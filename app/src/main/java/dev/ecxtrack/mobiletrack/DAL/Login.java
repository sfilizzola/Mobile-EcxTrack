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

        if (oUsu != null && oUsu.getStatus().equals("OK"))
            InsereUsuarioLocal(oUsu);
        if (oUsu != null && oUsu.getStatus().equals("NOT"))
            TrataRetornoUsuario(oUsu.getStatus());

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

        Usuario oUsu = new Usuario();
        DataReader DbReader = DBManager().getDbReader(DbCommand);

        while (DbReader.Read()) {
           oUsu.setCodUsuario(DbReader.getInt("CodUsuario"));
           oUsu.setCPF(DbReader.getString("CPF"));
            oUsu.setSenha(DbReader.getString("Senha"));
            oUsu.setCodUsuario(DbReader.getInt("CodUsuario"));

            //TODO - Faz função para recupperação de cliente
            //oUsu.setCliente(DbReader.getInt("CPF"));

            //TODO - Faz função para recupperação de perfil
            //oUsu.setPerfil(DbReader.getIntOrNull("CPF"));

            oUsu.setStatus("Banco");
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
