package dev.ecxtrack.ecxtrack.DAL;

import org.ksoap2.SoapFault;
import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import dev.ecxtrack.ecxtrack.Entidades.Usuario;
import dev.sfilizzola.data.*;
import dev.sfilizzola.utils.Log;

/**
 * Created by Samuel on 12/08/2014.
 */
public class Login extends DataAccessLayerBase {

    private String TAG = "DAL LOGIN";

    public Login(){}

    public Usuario VerificaLogin (String pNomeUsuario, String pSenha) throws HttpResponseException{

        Usuario oUsu = BuscaUsuarioWebservice(pNomeUsuario, pSenha);
       // oUsu = BuscaUsuarioLocal(pNomeUsuario, pSenha);
        //if (oUsu == null)
        //oUsu = BuscaUsuarioWebservice(pNomeUsuario, pSenha);

            //if (oUsu != null && oUsu.getStatus().equals("OK"))
              //  InsereUsuarioLocal(oUsu);
            //if (oUsu != null && oUsu.getStatus().equals("NOT"))
            //     TrataRetornoUsuario(oUsu.getStatus());

        return oUsu;
    }

    private void TrataRetornoUsuario(String status) {
    }

    private Usuario BuscaUsuarioLocal(String pNomeUsuario, String pSenha) {

        DataCommand DbCommand = DBManager().GetCommand();
        //String vSQL = dev.sfilizzola.data.Utilities.Resources.GetSQL(new String[] { "Itens" }, "CarregarItem.sql");

        DbCommand.setCommandText("SELECT * FROM usuarios WHERE nome = :NomeUsuario AND senha = :SenhaUsuario");

        DbCommand.Parameters.add(":NomeUsuario", DataParameter.DataType.STRING, pNomeUsuario);
        DbCommand.Parameters.add(":SenhaUsuario", DataParameter.DataType.STRING, pSenha);

        Usuario oUsu = null;
        DataReader DbReader = DBManager().getDbReader(DbCommand);

        while (DbReader.Read()) {

            oUsu = new Usuario();
            oUsu.setCodUsuario(DbReader.getInt("CodUsuario"));
            oUsu.setCPF(DbReader.getString("CPF"));
            oUsu.setSenha(DbReader.getString("Senha"));
            oUsu.setCodUsuario(DbReader.getInt("CodUsuario"));
            oUsu.setStatus("Banco");
        }
        DbReader.close();

        return oUsu;
    }

    private Usuario BuscaUsuarioWebservice(String pNomeUsuario, String pSenha) throws HttpResponseException{
        WebService ws = new WebService();
        try {
            return ws.Login(pNomeUsuario, pSenha);
        } catch (XmlPullParserException e) {
            Log.e(TAG, e.getMessage());
        }  catch (SoapFault soapFault) {
            Log.e(TAG, soapFault.getMessage());
        }
        return null;
    }

    private void InsereUsuarioLocal(Usuario oUsu) {

       /* DataCommand DbCommand = DBManager().GetCommand();

        DbCommand.setCommandText(dev.sfilizzola.data.Utilities.Resources.GetSQL(new String[] {"Login" }, "LoginInsert.sql"));

        DbCommand.Parameters.add(":Nome", DataParameter.DataType.STRING, oUsu.getNome());
        DbCommand.Parameters.add(":CPF", DataParameter.DataType.STRING, oUsu.getCPF());
        DbCommand.Parameters.add(":Email", DataParameter.DataType.STRING, oUsu.getEmail());
        DbCommand.Parameters.add(":Senha", DataParameter.DataType.STRING, oUsu.getSenha());
        DbCommand.Parameters.add(":CodUsuario", DataParameter.DataType.NUMBER, oUsu.getCodUsuario());
        DbCommand.Parameters.add(":CodPerfil", DataParameter.DataType.NUMBER, oUsu.getPerfil().getCodPerfil());
        DbCommand.Parameters.add(":CodCliente", DataParameter.DataType.NUMBER, oUsu.getCliente().getCodCliente());
        DbCommand.Parameters.add(":dtValidade", DataParameter.DataType.DATETIME, oUsu.getDtValidade());

        DbCommand.ExecuteNonQuery();*/

    }
}
