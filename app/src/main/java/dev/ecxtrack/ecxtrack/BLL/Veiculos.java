package dev.ecxtrack.ecxtrack.BLL;

import org.joda.time.DateTime;

import java.util.List;

import dev.ecxtrack.ecxtrack.Entidades.Evento;
import dev.ecxtrack.ecxtrack.Entidades.Veiculo;

/**
 * Created by samuel.filizzola on 22/08/2014.
 */
public class Veiculos implements IDisposable {

    private dev.ecxtrack.ecxtrack.DAL.Veiculos oVeiculoDAL;

    public Veiculos()
    {
        oVeiculoDAL = new dev.ecxtrack.ecxtrack.DAL.Veiculos();
    }


    public List<Veiculo> VeiculosPorUsuario (int pCodusuario){
        return oVeiculoDAL.BuscaVeiculos(pCodusuario);
    }

   public Evento UltimoEventoVeiculo (Veiculo pVeiculo){
       return oVeiculoDAL.BuscaUltimaPosicao(pVeiculo.getCodVeiculo());
   }

   public List<Evento> Trajetos (DateTime pDataInicial, DateTime pDataFinal, int pCodVeiculo){
       return oVeiculoDAL.Trajetos(pDataInicial, pDataFinal, pCodVeiculo);
   }

    @Override
    public void Dispose() {
        if (oVeiculoDAL != null)
            oVeiculoDAL.Dispose();
    }

}
