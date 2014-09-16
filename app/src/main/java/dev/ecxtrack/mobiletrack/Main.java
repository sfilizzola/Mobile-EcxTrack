package dev.ecxtrack.mobiletrack;

import android.app.ActionBar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

import dev.ecxtrack.mobiletrack.BLL.Veiculos;
import dev.ecxtrack.mobiletrack.Entidades.Evento;
import dev.ecxtrack.mobiletrack.Entidades.Veiculo;


public class Main extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private GoogleMap mMap;

    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    private View mProgressView;

    private VeiculosPositionTask mPositionTask;

    private ProgressDialog progress;


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

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        progress = ProgressDialog.show(this, "Carregando posição", "Aguarde...", true);
        Veiculo oVeiculoSelecionado = App.getoVeiculosAtuais().get(position);
        App.setoVeiculoSelecionado(oVeiculoSelecionado);
        mPositionTask = new VeiculosPositionTask(oVeiculoSelecionado);
        mPositionTask.execute();
        mTitle = oVeiculoSelecionado.getPlaca();
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

        if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Configurações.", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (item.getItemId() == R.id.action_logout) {
            App.setoUsuarioLogado(null);
            App.setoVeiculosAtuais(null);
            App.setoVeiculoSelecionado(null);
            this.finish();
        }

        if (item.getItemId() == R.id.action_refresh) {
            progress = ProgressDialog.show(this, "Carregando posição", "Aguarde...", true);
            mPositionTask = new VeiculosPositionTask(App.getoVeiculoSelecionado());
            mPositionTask.execute();
        }

        if (item.getItemId() == R.id.action_anchor) {
            Toast.makeText(this, "Anchor.", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (item.getItemId() == R.id.action_route) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(this.getFragmentManager(), "dataInicial");
        }


        return super.onOptionsItemSelected(item);
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
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //TODO - Inicia com posição atual do cliente.

        LatLng teste = new LatLng(-19.921161, -43.914792);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(teste, 16.0f));
        mMap.addMarker(new MarkerOptions().position(teste).title("Marcador"));
    }

    private void setUpMap(Evento pEvento){



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

        oMarcador.snippet("Data: " + pEvento.getDataEvento().toString("dd/MM/YYYY H:m:s"));
        //Adiciona Marcador
        mMap.addMarker(oMarcador);

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
            if (result.getCodCliente().equals(0))
                setUpMap();
            else
                setUpMap(result);
        }

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private DatePickerDialog dlg;

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
                Toast.makeText(getActivity().getApplicationContext(), "dataFinal: "+ day +"/" +month +"/"+year, Toast.LENGTH_SHORT).show();

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(this.getFragmentManager(), "dataFinal");}
            else if (this.getTag().equals("dataFinal")){
                Toast.makeText(getActivity().getApplicationContext(), "dataFinal.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
