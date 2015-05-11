package dev.ecxtrack.ecxtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import dev.ecxtrack.ecxtrack.BLL.Veiculos;
import dev.ecxtrack.ecxtrack.Entidades.Veiculo;


public class LoadingAct extends Activity {

    private VeiculosLoginTask mVeicInitTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mVeicInitTask = new VeiculosLoginTask(App.getoUsuarioLogado().getCodUsuario());
        mVeicInitTask.execute();
    }


    public class VeiculosLoginTask extends AsyncTask<Void, Void, List<Veiculo>> {

        private final int mCodUsu;

        VeiculosLoginTask(int pCodusu) {
            mCodUsu = pCodusu;
        }

        @Override
        protected List<Veiculo> doInBackground(Void... params) {
            List<Veiculo> vretVal;
            Veiculos BLLVeiculos = new Veiculos();
            vretVal = BLLVeiculos.VeiculosPorUsuario(mCodUsu);
            BLLVeiculos.Dispose();
            return vretVal;
        }

        @Override
        protected void onPostExecute(List<Veiculo> result) {
            App.setoVeiculosAtuais(result);

            Intent newIntent = new Intent(LoadingAct.this, Main.class);
            startActivity(newIntent);
        }

    }

}
