package dev.ecxtrack.ecxtrack.DAL;

import org.joda.time.DateTime;
import org.ksoap2.SoapFault;
import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import java.util.List;

import dev.ecxtrack.ecxtrack.Entidades.Evento;
import dev.ecxtrack.ecxtrack.Entidades.Veiculo;
import dev.sfilizzola.data.DataCommand;
import dev.sfilizzola.data.DataParameter;
import dev.sfilizzola.data.DataReader;
import dev.sfilizzola.utils.ArrayList;
import dev.sfilizzola.utils.Log;

/**
 * Created by samuel.filizzola on 19/08/2014.
 */
public class Veiculos extends DataAccessLayerBase{

    private String TAG = "DAL VEICULOS";

    public Veiculos() {
    }


    public List<Veiculo> BuscaVeiculos (Integer pCodUsuario){

        List<Veiculo> vRetVal = new ArrayList<Veiculo>();

        DataCommand DbCommand = DBManager().GetCommand();

        String vSQL = "";
        if (pCodUsuario == null)
            vSQL = ("SELECT * FROM veiculos ");
        else {
            vSQL = ("SELECT * FROM veiculos Where CodCliente = :CodCliente ");
            DbCommand.Parameters.add(":nome", DataParameter.DataType.NUMBER, pCodUsuario);
        }

        DbCommand.setCommandText(vSQL);

        DataReader DbReader = DBManager().getDbReader(DbCommand);
        while (DbReader.Read()) {
            Veiculo oVeiculo = new Veiculo();
            oVeiculo.setPlaca(DbReader.getString("Placa"));
            oVeiculo.setCodTipo(DbReader.getInt("CodTipo"));
            oVeiculo.setCodVeiculo(DbReader.getInt("CodVeiculo"));
            oVeiculo.setContatoNome(DbReader.getStringOrNull("ContatoNome"));
            oVeiculo.setContatoTelefone(DbReader.getStringOrNull("ContatoTelefone"));
            oVeiculo.setCodCliente(DbReader.getInt("CodCliente"));
            oVeiculo.setTipoVeiculoNome(DbReader.getString("TipoVeiculoNome"));

            vRetVal.add(oVeiculo);

        }DbReader.close();

        if (vRetVal.size() == 0)
            vRetVal = BuscaVeiculosWebservice(pCodUsuario);

        return vRetVal;
    }


    public Evento BuscaUltimaPosicao(int pCodVeiculo) {
        WebService ws = new WebService();
        try {
            return ws.UltimaLocalizacaoVeiculo(pCodVeiculo);
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage());
        } catch (HttpResponseException e) {
            Log.e(TAG, e.getMessage());
        } catch (SoapFault soapFault) {
            Log.e(TAG, soapFault.getMessage());
        }
        return null;
    }

    public void InsereVeiculosBanco (List<Veiculo> pLista){
        for (Veiculo carro: pLista){
            InsereVeiculoBanco(carro);
        }
    }

    private void InsereVeiculoBanco (Veiculo pVeiculo){

        DataCommand DbCommand = DBManager().GetCommand();

        DbCommand.setCommandText(dev.sfilizzola.data.Utilities.Resources.GetSQL(new String[] {"Veiculos" }, "VeiculoInsert.sql"));

        DbCommand.Parameters.add(":Placa", DataParameter.DataType.STRING, pVeiculo.getPlaca());
        DbCommand.Parameters.add(":TipoVeiculoNome", DataParameter.DataType.STRING, pVeiculo.getTipoVeiculoNome());
        DbCommand.Parameters.add(":CodVeiculo", DataParameter.DataType.NUMBER, pVeiculo.getCodVeiculo());
        DbCommand.Parameters.add(":ContatoNome", DataParameter.DataType.STRING, pVeiculo.getContatoNome());
        DbCommand.Parameters.add(":ContatoTelefone", DataParameter.DataType.STRING, pVeiculo.getContatoTelefone());
        DbCommand.Parameters.add(":CodCliente", DataParameter.DataType.NUMBER, pVeiculo.getCodCliente());

        DbCommand.ExecuteNonQuery();
    }


    public List<Veiculo> BuscaVeiculosWebservice(Integer pCodUsuario){
        WebService ws = new WebService();
        try {
            return ws.VeiculosPorCliente(pCodUsuario);
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage());
        } catch (HttpResponseException e) {
            Log.e(TAG, e.getMessage());
        } catch (SoapFault soapFault) {
            Log.e(TAG, soapFault.getMessage());
        }
        return null;
    }


    public List<Evento> Trajetos(DateTime pDataInicial, DateTime pDataFinal, int pCodVeiculo) {
        WebService ws = new WebService();
        try {
            return ws.Trajetos(pDataInicial, pDataFinal, pCodVeiculo);
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage());
        } catch (HttpResponseException e) {
            Log.e(TAG, e.getMessage());
        } catch (SoapFault soapFault) {
            Log.e(TAG, soapFault.getMessage());
        }
        return null;

    }


    public int NovaAncora(int CodEvento, int CodUsuario, int Raio){

        WebService ws = new WebService();
        try {
            return ws.NovaAncora(CodEvento, CodUsuario, Raio);
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage());
        } catch (HttpResponseException e) {
            Log.e(TAG, e.getMessage());
        } catch (SoapFault soapFault) {
            Log.e(TAG, soapFault.getMessage());
        }
        return 0;
    }

    public String CancelarAncora(int IdAncora){

        WebService ws = new WebService();
        try {
            return ws.CancelarAncora(IdAncora);
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage());
        } catch (HttpResponseException e) {
            Log.e(TAG, e.getMessage());
        } catch (SoapFault soapFault) {
            Log.e(TAG, soapFault.getMessage());
        }
        return null;
    }
}
