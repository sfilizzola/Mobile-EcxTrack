package dev.ecxtrack.mobiletrack.DAL;

import dev.ecxtrack.mobiletrack.Entidades.Cliente;
import dev.sfilizzola.data.DataCommand;
import dev.sfilizzola.data.DataParameter;
import dev.sfilizzola.data.DataReader;

/**
 * Created by Samuel on 13/08/2014.
 */
public class Clientes extends DataAccessLayerBase {

    private String TAG = "DAL CLIENTES";

    public Clientes(){}

    public Cliente VerificaCliente  (int pCodCliente){

        Cliente oCli = null;

        oCli = BuscaCLienteLocal(pCodCliente);

        if (oCli == null)
            oCli = BuscaClienteWebservice(pCodCliente);

        if (oCli != null && oCli.getStatus().equals("OK"))
            InsereClienteLocal(oCli);
       // if (oCli != null && oCli.getStatus().equals("NOT"))
        //    TrataRetornoUsuario(oUsu.getStatus());

        return oCli;
    }

    private Cliente BuscaCLienteLocal(int pCodCliente) {
        DataCommand DbCommand = DBManager().GetCommand();
        //String vSQL = dev.sfilizzola.data.Utilities.Resources.GetSQL(new String[] { "Itens" }, "CarregarItem.sql");

        DbCommand.setCommandText("SELECT * FROM clientes WHERE CodCliente = :CodCli");

        DbCommand.Parameters.add(":CodCli", DataParameter.DataType.STRING, pCodCliente);

        Cliente oCli = new Cliente();
        DataReader DbReader = DBManager().getDbReader(DbCommand);

        while (DbReader.Read()) {
           /* oUsu.setCodUsuario(DbReader.getInt("CodUsuario"));
            oUsu.setCPF(DbReader.getString("CPF"));
            oUsu.setSenha(DbReader.getString("Senha"));
            oUsu.setCodUsuario(DbReader.getInt("CodUsuario"));
            oUsu.setStatus("Banco");*/
        }
        DbReader.close();

        return oCli;
    }

    private Cliente BuscaClienteWebservice(int pCodCliente) {
        return null;
    }

    private void InsereClienteLocal(Cliente oCli) {
    }
}
