package dev.ecxtrack.mobiletrack.DAL;

import dev.ecxtrack.mobiletrack.Entidades.Usuario;
import dev.sfilizzola.data.*;
/**
 * Created by Samuel on 12/08/2014.
 */
public class Login extends DataAccessLayerBase {

    public Login(){}

    public Usuario VerificaLogin (String pNomeUsuario, String pSenha){

        Usuario oUsu = null;

        oUsu = BuscaUsuarioLocal(pNomeUsuario, pSenha);
        if (oUsu == null)
            oUsu = BuscaUsuarioWebservice(pNomeUsuario, pSenha);
        if (oUsu != null)
            InsereUsuarioLocal(oUsu);
        return oUsu;
    }

    private Usuario BuscaUsuarioLocal(String pNomeUsuario, String pSenha) {

        DataCommand DbCommand = DBManager().GetCommand();
        //String vSQL = dev.sfilizzola.data.Utilities.Resources.GetSQL(new String[] { "Itens" }, "CarregarItem.sql");

        DbCommand.setCommandText("SELECT * FROM usuarios WHERE nome = :NomeUsuario AND senha = :SenhaUsuario");

        DbCommand.Parameters.add(":NomeUsuario", DataParameter.DataType.STRING, pNomeUsuario);
        DbCommand.Parameters.add(":SenhaUsuario", DataParameter.DataType.STRING, pSenha);

        Usuario oUsu = new Usuario();
        DataReader DbReader = DBManager().getDbReader(DbCommand);

        while (DbReader.Read()) {
           oUsu.setCodUsuario(DbReader.getInt("CodUsuario"));
           oUsu.setCPF(DbReader.getString("cpf"));

            /*oDoc.setIdDocumento(DbReader.getInt("id_docs"));
            oDoc.setNome(DbReader.getString("nome"));
            oDoc.setFoto(DbReader.getString("foto"));
            oDoc.setIdentificacao(DbReader.getStringOrNull("identificacao"));*/
        }
        DbReader.close();

        return oUsu;
    }

    private Usuario BuscaUsuarioWebservice(String pNomeUsuario, String pSenha) {
        return null;
    }

    private void InsereUsuarioLocal(Usuario oUsu) {
    }
}
