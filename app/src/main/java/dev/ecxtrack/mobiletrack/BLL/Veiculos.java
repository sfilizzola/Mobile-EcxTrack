package dev.ecxtrack.mobiletrack.BLL;

import java.util.List;

import dev.ecxtrack.mobiletrack.Entidades.Veiculo;

/**
 * Created by samuel.filizzola on 22/08/2014.
 */
public class Veiculos implements IDisposable {

    private dev.ecxtrack.mobiletrack.DAL.Veiculos oVeiculoDAL;

    public Veiculos()
    {
        oVeiculoDAL = new dev.ecxtrack.mobiletrack.DAL.Veiculos();
    }


    public List<Veiculo> VeiculosPorUsuario (int pCodusuario){
        return oVeiculoDAL.BuscaVeiculos(pCodusuario);
    }

    public String[] PlacasString (List<Veiculo> pLista){

        int vQuantidade = pLista.size();
        int cont = 0;

        String[] vret = new String[vQuantidade];

        for (Veiculo item : pLista){
            vret[cont] = item.getPlaca();
            cont++;
        }

        return vret;
    }

    @Override
    public void Dispose() {
        if (oVeiculoDAL != null)
            oVeiculoDAL.Dispose();
    }

}
