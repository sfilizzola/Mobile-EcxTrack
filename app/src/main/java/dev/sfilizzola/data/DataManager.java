
package dev.sfilizzola.data;


import java.util.Date;

import dev.sfilizzola.data.DataParameter.DataType;
import dev.sfilizzola.utils.Log;
import com.sfilizzola.garantido.App;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {

    private String ConnectionString;
    private SQLiteDatabase DbConnection;
    private static DataCommand DbCommandCache;
    private static DatabaseHelper DATABASE;

    public DataManager() {

        DATABASE = new DatabaseHelper(App.getAppContext());
        DbConnection = DATABASE.getDatabase();
        this.setConnectionString(DbConnection.getPath());

    }

    /**
     * Construtor que seta o tipo de conex�o que ser� criada
     * 
     * @param PatchMode Informa se deve ser aberta conex�o para aplicar path ou n�o.
     */
    public DataManager(boolean PatchMode) {

        if (PatchMode)
            this.setConnectionString(DbConnection.getPath());
        else
            this.setConnectionString(DbConnection.getPath());
    }

    /**
     * Retorna true caso a conex�o com o banco de dados existir e estiver aberta
     */
    public boolean CheckConnection() {

        return !(DbConnection == null || !DbConnection.isOpen());
    }

    /**
     * Verifica se uma transa��o esta sendo feita no momento
     */
    public boolean CheckTransaction() {

        if (CheckConnection())
            return DbConnection.inTransaction();
        return false;
    }

    /**
     * Fecha o banco de dados e alguma transa��o caso alguma estiver ocorrendo
     */
    public void CloseDbConnection() {

        if (CheckConnection() && CheckTransaction())
            DbConnection.endTransaction();

        if (CheckConnection()) {
            DataManagerConnectionCache.CloseConnection(ConnectionString);
            DbConnection = null;
            Log.v("DATABASE", "Database closed");

        }
    }

    public void Dispose() {

        CloseDbConnection();
    }

    /**
     * Obtem um novo comando
     */
    public DataCommand GetCommand() {

        DataCommand oCommand = new DataCommand();
        oCommand.setConnection(GetConnection());
        return oCommand;
    }

    /**
     * Obtem um novo comando e inicia uma transa��o caso n�o estiver ocorrendo uma.
     */
    public DataCommand GetCommand(boolean InTransaction) {

        DataCommand oCommand = this.GetCommand();
        if (InTransaction && !CheckTransaction())
            DbConnection.beginTransaction();
        return oCommand;
    }

    /**
     * Obtem um DataCommand do Cache
     * 
     * @return
     */
    public DataCommand GetCommandFromCache() {

        if (DbCommandCache == null)
            DbCommandCache = GetCommand();
        return DbCommandCache;

    }

    /**
     * Inicializa e abre uma nova conexao com o banco de dados
     */
    public SQLiteDatabase GetConnection() {

        // Cria uma conexao com o banco de dados
        if (!CheckConnection()) {
            DbConnection = DataManagerConnectionCache.OpenConnection(ConnectionString);
            Log.v("DATABASE", "Database opened");
        }
        return DbConnection;
    }

    /**
     * Obtem a string de conexao atual, atribuida ao sistema
     * 
     * @return
     */
    public String getConnectionString() {

        return ConnectionString;
    }

    /**
     * Obtem um cursor para leitura de dados
     * 
     * @param pDBCommand
     * @return
     */
    public android.database.Cursor getCursor(DataCommand pDBCommand) {

        return pDBCommand.ExecuteQuery();
    }

    /**
     * Obtem um cursor para leitura de dados
     * 
     * @param pSQL
     * @return
     */
    public android.database.Cursor getCursor(String pSQL) {

        DataCommand oCommand = GetCommand();
        oCommand.setCommandText(pSQL);
        return oCommand.ExecuteQuery();
    }

    /**
     * Retorna o tempo atual do sistema
     */
    public Date GetDateTime() {

        return new Date();
    }

    /**
     * Obtem um DataReader para leitura sequencial dos dados
     * 
     * @param pDBCommand
     * @return
     */
    public DataReader getDbReader(DataCommand pDBCommand) {

        return new DataReader(getCursor(pDBCommand));
    }

    /**
     * Obtem um DataReader para leitura sequencial dos dados
     * 
     * @param pSQL
     * @return
     */
    public DataReader getDbReader(String pSQL) {

        return new DataReader(getCursor(pSQL));
    }

    /**
     * Cria um novo parametro sem valor preenchido
     * 
     * @param pDataType Tipo do parametro
     * @param pParamName Nome do parametro
     */
    public DataParameter GetParameter(DataType pDataType, String pParamName) {

        return GetParameter(pDataType, pParamName, null);
    }

    /**
     * Cria um novo parametro
     * 
     * @param pDataType Tipo do parametro
     * @param pParamName Nome do parametro
     * @param pValue Valor do parametro
     */
    public DataParameter GetParameter(DataType pDataType, String pParamName, Object pValue) {

        DataParameter oParam = new DataParameter();
        oParam.setDbType(pDataType);
        oParam.setParameterName(pParamName);
        oParam.setValue(pValue);
        return oParam;
    }

    /**
     * Obtem um escalar do tipo Integer
     * 
     * @param pDbCommand
     * @return
     */
    public Integer GetScalarInteger(DataCommand pDbCommand) {

        return pDbCommand.ExecuteScalarInteger();
    }

    /**
     * Obtem um escalar do tipo Integer
     * 
     * @param pSQL
     * @return
     */
    public Integer GetScalarInteger(String pSQL) {

        DataCommand oCommand = this.GetCommand();
        oCommand.setCommandText(pSQL);
        return oCommand.ExecuteScalarInteger();
    }

    /**
     * Obtem um escalar do tipo Long
     * 
     * @param pDbCommand
     * @return
     */
    public Long GetScalarLong(DataCommand pDbCommand) {

        return pDbCommand.ExecuteScalarLong();
    }

    /**
     * Obtem um escalar do tipo Long
     * 
     * @param pSQL
     * @return
     */
    public Long GetScalarLong(String pSQL) {

        DataCommand oCommand = this.GetCommand();
        oCommand.setCommandText(pSQL);
        return oCommand.ExecuteScalarLong();
    }

    /**
     * Obtem um escalar do tipo String
     * 
     * @param pDbCommand
     * @return
     */
    public String GetScalarString(DataCommand pDbCommand) {

        return pDbCommand.ExecuteScalarString();
    }

    /**
     * Obtem um escalar do tipo String
     * 
     * @param pSQL
     * @return
     */
    public String GetScalarString(String pSQL) {

        DataCommand oCommand = this.GetCommand();
        oCommand.setCommandText(pSQL);
        return oCommand.ExecuteScalarString();
    }

    /**
     * Define a String de Conexao que sera usada pelo sistema
     * 
     * @param connectionString
     */
    public void setConnectionString(String connectionString) {

        // Quando houver alteracao de string de conexao, fecha a mesma, para que uma nova conexao seja criada
        if (connectionString != this.ConnectionString) {
            CloseDbConnection();
        }

        ConnectionString = connectionString;

    }

    /**
     * Inicia uma transa��o
     */
    public void TransactionBegin() {

        if (CheckTransaction())
            DbConnection.beginTransaction();
    }

    /**
     * Commita uma transa��o
     */
    public void TransactionCommit() {

        if (CheckTransaction()) {
            DbConnection.setTransactionSuccessful();
            DbConnection.endTransaction();
        }
    }

    /**
     * Finaliza a transa��o atual sem salvar as modifica��es
     */
    public void TransactionRollback() {

        if (CheckTransaction()) {
            DbConnection.endTransaction();
        }

    }

}
