package dev.ecxtrack.ecxtrack;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.helpshift.Helpshift;

import org.joda.time.DateTime;

import java.util.List;

import dev.ecxtrack.ecxtrack.BLL.Veiculos;
import dev.ecxtrack.ecxtrack.Entidades.Evento;
import dev.ecxtrack.ecxtrack.Entidades.Veiculo;


public class Main extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    //Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private GoogleMap mMap;

    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    private VeiculosPositionTask mPositionTask;

    private TrajetosTask mTrajTask;

    private ProgressDialog progress;

    private LocationClient mLocationClient;

    private Location mCurrentLocation;

    private static final String PREFS_NAME = "TrackPrefs";

    private Menu mMenu;

    private Circle circuloAncora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mLocationClient = new LocationClient(this, this, this);

        //instalaçao HelpShift
        Helpshift.install(getApplication(), "a21a303c81649ab4857be952523837ff", "ecxtrack.helpshift.com", "ecxtrack_platform_20150507194408005-f07857714d1ba78");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        progress = ProgressDialog.show(this, "Carregando posição", "Aguarde...", true);
        if (App.getoVeiculosAtuais() != null) {
            try {
                Veiculo oVeiculoSelecionado = App.getoVeiculosAtuais().get(position);
                App.setoVeiculoSelecionado(oVeiculoSelecionado);
                mPositionTask = new VeiculosPositionTask(oVeiculoSelecionado);
                mPositionTask.execute();
                mTitle = oVeiculoSelecionado.getPlaca();
            } catch (IndexOutOfBoundsException Exception){
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setIcon(android.R.drawable.ic_dialog_info);
                dialog.setTitle("Atenção");
                dialog.setMessage(getString(R.string.expired_session));
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.create().show();
            }
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(android.R.drawable.ic_dialog_info);
            dialog.setTitle("Atenção");
            dialog.setMessage(getString(R.string.expired_session));
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment != null) {
            if (!mNavigationDrawerFragment.isDrawerOpen()) {
                // Only show items in the action bar relevant to this screen
                // if the drawer is not showing. Otherwise, let the drawer
                // decide what to show in the action bar.
                getMenuInflater().inflate(R.menu.main, menu);
                getMenuInflater().inflate(R.menu.global, menu);

                mMenu = menu;
                restoreActionBar();
                return true;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        /*if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Configurações.", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        if (item.getItemId() == R.id.action_logout) {
            App.setoUsuarioLogado(null);
            App.setoVeiculosAtuais(null);
            App.setoVeiculoSelecionado(null);
            this.finish();
        }

        if (item.getItemId() == R.id.action_refresh) {
            AtualizaPosicao();
        }

        if (item.getItemId() == R.id.action_anchor) {
            EscolheDesenhaAncora();
        }

        if (item.getItemId() == R.id.action_help) {
            DialogFragment newFragment = new HelpFragment();
            newFragment.show(this.getFragmentManager(), "HelpFragment");
        }

        if (item.getItemId() == R.id.action_find) {
            TracaCaminhoUsuarioLocalAteCarroSelecionado(App.getoVeiculoSelecionado());
        }

        if (item.getItemId() == R.id.action_route) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(this.getFragmentManager(), "dataInicial");
        }

        if (item.getItemId() == R.id.action_maptype) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.action_maptype)
                    .setItems(R.array.tipos_de_mapa, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int MAP_TYPE;
                            switch (which) {
                                //Normal
                                case 0:
                                    MAP_TYPE = GoogleMap.MAP_TYPE_NORMAL;
                                    break;
                                //Satelite
                                case 1:
                                    MAP_TYPE = GoogleMap.MAP_TYPE_SATELLITE;
                                    break;
                                //Terreno
                                case 2:
                                    MAP_TYPE = GoogleMap.MAP_TYPE_TERRAIN;
                                    break;
                                //Hibrido
                                case 3:
                                    MAP_TYPE = GoogleMap.MAP_TYPE_HYBRID;
                                    break;
                                //Normal
                                default:
                                    MAP_TYPE = GoogleMap.MAP_TYPE_NORMAL;
                                    break;
                            }
                            mMap.setMapType(MAP_TYPE);

                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putInt("MapType", MAP_TYPE);
                            // Commit the edits!
                            editor.commit();


                        }
                    });
            builder.show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void EscolheDesenhaAncora() {

        if (circuloAncora == null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.action_anchor)
                    .setItems(R.array.raios_ancora, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            int RADIUS;

                            switch (which) {
                                //100
                                case 0:
                                    RADIUS = 100;
                                    break;
                                //200
                                case 1:
                                    RADIUS = 200;
                                    break;
                                //300
                                case 2:
                                    RADIUS = 300;
                                    break;
                                //500
                                case 3:
                                    RADIUS = 500;
                                    break;
                                //1km
                                case 4:
                                    RADIUS = 1000;
                                    break;
                                default:
                                    RADIUS = 100;
                                    break;
                            }

                            Evento oEventoAtual = App.getoEventoAtual();
                            if (oEventoAtual != null) {
                                if (circuloAncora != null)
                                    circuloAncora.remove();

                                LatLng centroAtual = new LatLng(oEventoAtual.getLatitude(), oEventoAtual.getLongitude());

                                CircleOptions circleOptions = new CircleOptions()
                                        .center(centroAtual)
                                        .radius(RADIUS) // In meters
                                        .strokeColor(Color.BLUE)
                                        .fillColor(Color.argb(80, 0, 0, 100));
                                circuloAncora = mMap.addCircle(circleOptions);

                                //TODO - Insere chamado webservice da âncora para criação

                            } else {
                                Toast.makeText(getApplicationContext(), "Nenhum carro selecionado para setar uma âncora.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            builder.show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Deseja cancelar a âncora marcada ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (circuloAncora != null)
                            {
                                circuloAncora.remove();
                                circuloAncora = null;
                            }

                            //TODO - Insere chamado webservice da âncora para cancelamento
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }

    private void AtualizaPosicao() {
        progress = ProgressDialog.show(this, "Carregando posição", "Aguarde...", true);
        mPositionTask = new VeiculosPositionTask(App.getoVeiculoSelecionado());
        mPositionTask.execute();
    }

    private void TracaCaminhoUsuarioLocalAteCarroSelecionado(Veiculo veiculo) {
        mCurrentLocation = mLocationClient.getLastLocation();

        new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja traçar uma rota de sua posição atual até o Veículo " + veiculo.getPlaca() + " ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + App.getoEventoAtual().getLatitude() + "," + App.getoEventoAtual().getLongitude()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        try {
            if (mMap == null) {
                // Try to obtain the map from the SupportMapFragment.
                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();
                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    int mapType = settings.getInt("MapType", GoogleMap.MAP_TYPE_NORMAL);
                    mMap.setMapType(mapType);
                    setUpMap();
                }
            }
        } catch (NullPointerException Exception){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(android.R.drawable.ic_dialog_info);
            dialog.setTitle("Atenção");
            dialog.setMessage(getString(R.string.expired_session));
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialog.create().show();
        }
    }

    private void setUpMap() {
        if (mLocationClient.isConnected()) {
            mCurrentLocation = mLocationClient.getLastLocation();
            if (mCurrentLocation != null && mMap != null)
            {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 16.0f));
            }
        } else {
            //Local da  sede da ECX card
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-19.936162, -43.944933), 16.0f));
        }
    }

    private void setUpMap(Evento pEvento){

        mMap.setMyLocationEnabled(true);

        LatLng teste = new LatLng(pEvento.getLatitude(), pEvento.getLongitude());
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(teste, 16.0f));
        //dados do Marcador
        MarkerOptions oMarcador = new MarkerOptions().position(teste).title(App.getoVeiculoSelecionado().getPlaca());
        if (pEvento.isStatusIgnicao())
            oMarcador.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_list_gcar));
        else
            oMarcador.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_list_rcar));

        oMarcador.snippet("Data: " + pEvento.getDataEvento().toString("dd/MM/YYYY HH:mm:ss"));
        //Adiciona Marcador
        mMap.addMarker(oMarcador);

    }

    private void setUpLines(List<Evento> result, boolean SetMarkers, boolean ClearMap) {

        if (ClearMap)
            mMap.clear();

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(Converte(result))
                .color(Color.BLUE)
                .geodesic(true));

        if (SetMarkers)
            AdicionaMarcadoresDeVertices(result);
    }

    private void AdicionaMarcadoresDeVertices(List<Evento> result) {

        int cont = 1;

        //Construtor de Limites
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(Evento ev : result){

            LatLng positionPlace = new LatLng(ev.getLatitude(), ev.getLongitude());

            MarkerOptions oMarcador = new MarkerOptions().position(positionPlace).title("Transmissão: " + cont);

            oMarcador.snippet("Data: " + ev.getDataEvento().toString("dd/MM/YYYY HH:mm:ss"));

            //inclui posição nos builders
            builder.include(oMarcador.getPosition());

            //Adiciona Marcador
            mMap.addMarker(oMarcador);
            cont++;
        }

        //Limites
        LatLngBounds bounds = builder.build();

        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate CamUp = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(CamUp);
    }

    private LatLng[] Converte (List<Evento> result){

        LatLng[] eventos = new LatLng[result.size()];

        int cont = 0;

        for(Evento ev : result){
            eventos[cont] = new LatLng(ev.getLatitude(), ev.getLongitude());
            cont++;
        }

        return eventos;
    }

    public void MontaTrajeto(DateTime dtInicial, DateTime dtFinal) {
        progress = ProgressDialog.show(this, "Carregando Trajetos", "Aguarde...", true);
        mTrajTask = new TrajetosTask(dtInicial, dtFinal, App.getoVeiculoSelecionado().getCodVeiculo());
        mTrajTask.execute();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //mMenu.getItem(3).setEnabled(true);
    }

    @Override
    public void onDisconnected() {
        //mMenu.getItem(3).setEnabled(false);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 9000);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //mMenu.getItem(3).setEnabled(false);
            Toast.makeText(this, "Não é possivel achar localização do usuário.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

    public class VeiculosPositionTask extends AsyncTask<Void, Void, Evento> {

        private final Veiculo mVeiculoSelecionado;

        VeiculosPositionTask(Veiculo pVeiculo) {
            mVeiculoSelecionado = pVeiculo;
        }

        @Override
        protected Evento doInBackground(Void... params) {

            Evento vretVal;
            Veiculos BLLVeiculos = new Veiculos();
            vretVal = BLLVeiculos.UltimoEventoVeiculo(mVeiculoSelecionado);
            BLLVeiculos.Dispose();
            return vretVal;
        }

        @Override
        protected void onPostExecute(Evento result) {
            progress.dismiss();
            App.setoEventoAtual(result);
            if (result == null || result.getCodCliente().equals(0))
                setUpMap();
            else
                setUpMap(result);
        }

    }

    public class TrajetosTask extends AsyncTask<Void, Void, List<Evento>> {

        private DateTime mDtInicial;
        private DateTime mDtFinal;
        private int mCodVeiculo;

        TrajetosTask(DateTime pDtInicial, DateTime pDatafinal, int pCodVeiculo) {
            mDtInicial = pDtInicial;
            mDtFinal = pDatafinal;
            mCodVeiculo = pCodVeiculo;
        }

        @Override
        protected List<Evento> doInBackground(Void... params) {

            List<Evento> vretVal;
            Veiculos BLLVeiculos = new Veiculos();
            vretVal = BLLVeiculos.Trajetos(mDtInicial, mDtFinal, mCodVeiculo);
            BLLVeiculos.Dispose();
            return vretVal;
        }

        @Override
        protected void onPostExecute(List<Evento> result) {
            progress.dismiss();
            setUpLines(result, true, true);
        }

    }

    @Override
    public void onBackPressed() {
        CallCloseDialog();
    }

    private void CallCloseDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.setTitle("Atenção");
        dialog.setMessage(R.string.alert_exit_list);
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.setNegativeButton("Não", null);
        dialog.create().show();
    }

   /* public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private DatePickerDialog dlg;
        private DateTime dtInicial;
        private DateTime dtFinal;
        private Main.TrajetosTask mTrajTask;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            dlg = new DatePickerDialog(getActivity(), this, year, month, day);
            if (this.getTag().equals("dataInicial"))
                dlg.setTitle("Data Inicial");
            else
                dlg.setTitle("Data Final");

            // Create a new instance of DatePickerDialog and return it
            return dlg;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if (this.getTag().equals("dataInicial")){

                //(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour)
                dtInicial = new DateTime(year, month, day, 0, 1);
                //Caham outra janela de Data
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(this.getFragmentManager(), "dataFinal");}
            else if (this.getTag().equals("dataFinal")){

                dtFinal = new DateTime(year, month, day, 23, 59);
                //chama task

                //MontaTrajeto(dtInicial, dtFinal);
            }
        }
    }*/
}