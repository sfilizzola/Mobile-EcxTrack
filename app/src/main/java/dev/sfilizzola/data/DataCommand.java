
package dev.sfilizzola.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import dev.sfilizzola.data.DataParameter.DataType;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Classe que representa um comando que sera executado contra uma base de dados do MySQL
 * 
 * @author Samuel Filizzola
 */
public class DataCommand {

    private class ParseQueryResult {

        String[] params;
        String sql;

        public ParseQueryResult(String sql, String[] params) {

            this.params = params;
            this.sql = sql;
        }

        public String[] getParams() {

            return params;
        }

        public String getSql() {

            return sql;
        }

    }

    /* Comando SQL a ser executado */
    private String CommandText;

    /* Objeto utilizado na manipulacao do banco de dados MySQL */
    private SQLiteDatabase Connection;

    /* Cole��o de parametros que serao substituidos na query */
    public DataParameterCollection Parameters;

    /* Formato da data 2000-12-31 24:59:59 */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /* Objeto para formatar numeros */
    private static DecimalFormat NumberFormater;

    /* Objeto para formatar datas */
    private static SimpleDateFormat DateFormater;

    /**
     * Cria um objeto DbCommand conectado a banco de dados informado
     */
    public DataCommand() {

        // Cria a colecao de parametros
        Parameters = new DataParameterCollection();

        // Instancia objetos que formatam datas e n�meros
        if (NumberFormater == null || DateFormater == null) {
            NumberFormater = (DecimalFormat)NumberFormat.getInstance(new Locale("en_US"));
            NumberFormater.setGroupingUsed(false);
            NumberFormater.setMaximumFractionDigits(12);
            DateFormater = new SimpleDateFormat(DATE_FORMAT);
        }

    }

    /**
     * Cria um objeto DbCommand conectado ao banco de dados informado
     * 
     * @param pDatabase Banco de dados ao qual o comando sera submetido
     */
    public DataCommand(SQLiteDatabase pDatabase) {

        this();
        this.Connection = pDatabase;
    }

    /** Retorna um objeto DataParameter em branco */
    public DataParameter CreateParameter() {

        return new DataParameter();
    }

    /**
     * Executa um comando que n�o � uma query
     */
    public void ExecuteNonQuery() {

        ParseQueryResult oResult = ParseQuery();
        Connection.execSQL(oResult.getSql(), oResult.getParams());
    }

    /**
     * Retorna um cursor com o resultado da query
     * 
     * @return Cursor com o resultado da query
     */
    public android.database.Cursor ExecuteQuery() {

        ParseQueryResult oResult = ParseQuery();
        return Connection.rawQuery(oResult.getSql(), oResult.getParams());
    }

    /**
     * Realiza a validacao do cursor para retornar o valor de um escalar
     * 
     * @param c
     * @return
     */
    private boolean ValidateExecuteScalar(Cursor c) {

        if (c.getCount() == 0)
            return false;
        else {
            c.moveToFirst();
            return !c.isNull(0);
        }

    }

    public Integer ExecuteScalarInteger() {

        Integer vRetval;
        Cursor c = ExecuteQuery();
        if (!ValidateExecuteScalar(c))
            vRetval = null;
        else
            vRetval = c.getInt(0);
        c.close();
        return vRetval;
    }

    public Long ExecuteScalarLong() {

        Long vRetval;
        Cursor c = ExecuteQuery();
        if (!ValidateExecuteScalar(c))
            vRetval = null;
        else
            vRetval = c.getLong(0);
        c.close();
        return vRetval;
    }

    public String ExecuteScalarString() {

        String vRetval;
        Cursor c = ExecuteQuery();
        if (!ValidateExecuteScalar(c))
            vRetval = null;
        else
            vRetval = c.getString(0);
        c.close();
        return vRetval;
    }

    public String getCommandText() {

        return CommandText;
    }

    public SQLiteDatabase getConnection() {

        return Connection;
    }

    /**
     * Faz a preparacao da query para substituir os parametros na mesma
     * 
     * @return String formatada com os parametros atuais
     */
    private ParseQueryResult ParseQuery() {

        ParseQueryResult retObj = null;

        // Verifica se essa comando possui parametros. Se nao tiver, nao precisa do parse
        if (this.Parameters.isEmpty()) {
            retObj = new ParseQueryResult(this.CommandText, new String[0]);
        } else {
            ArrayList<String> paramArray = new ArrayList<String>();
            StringBuilder result = new StringBuilder(this.CommandText.length());
            String delimiters = "= \r\n(),";
            StringTokenizer st = new StringTokenizer(this.CommandText, delimiters, true);

            while (st.hasMoreTokens()) {

                String w = st.nextToken();

                DataParameter p = null;
                if (w.startsWith(":"))
                    p = Parameters.get(w);

                if (p != null) {

                    // Define o valor do parametro
                    String pParamValue;

                    if (p.getValue() == null)
                        pParamValue = "";
                    else if (p.getDbType() == DataType.STRING) {
                        pParamValue = String.format("%s", p.getValue());
                    } else if (p.getDbType() == DataType.DATETIME) {
                        // Formata no caso de Datas
                        pParamValue = String.format("%s", DateFormater.format(p.getValue()));
                    } else // Formata no caso de numeros
                    {
                        // Cria instance no formato americano
                        pParamValue = String.format("%s", NumberFormater.format(p.getValue()));
                    }

                    result.append("?");
                    paramArray.add(pParamValue);

                } else
                    result.append(w);

            }
            retObj = new ParseQueryResult(result.toString(), paramArray.toArray(new String[paramArray.size()]));
        }

        return retObj;

    }

    /** Seta o comando a ser executado */
    public void setCommandText(String commandText) {

        CommandText = commandText;
    }

    /** Seta a conex�o a ser utilizada pelo objeto */
    public void setConnection(SQLiteDatabase connection) {

        Connection = connection;
    }

    public Double ExecuteScalarDouble() {

        Double vRetval;
        Cursor c = ExecuteQuery();
        if (!ValidateExecuteScalar(c))
            vRetval = null;
        else
            vRetval = c.getDouble(0);
        c.close();
        return vRetval;
    }

    public byte[] ExecuteScalarByteArray() {

        byte[] vRetval;
        Cursor c = ExecuteQuery();
        if (!ValidateExecuteScalar(c))
            vRetval = null;
        else
            vRetval = c.getBlob(0);
        c.close();
        return vRetval;
    }

    /*
     * public DataParameterCollection getParameters() { return Parameters; } public void setParameters(DataParameterCollection parameters) { Parameters = parameters; }
     */
}
