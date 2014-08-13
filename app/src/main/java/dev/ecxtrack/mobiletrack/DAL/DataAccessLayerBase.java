package dev.ecxtrack.mobiletrack.DAL;

import dev.sfilizzola.data.DataManager;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Samuel
 * Date: 01/08/13
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public class DataAccessLayerBase {

    // Objeto estatico que representa a conexao com o banco de dados
    private DataManager oDBManager;
    private DataManager oDBManagerPatch;
    private int oConnCount;

    /**
     * Objeto responsavel por tratar a comunicacao com o servidor
     */
    public DataAccessLayerBase() {

        if (oDBManager == null)
            oDBManager = new DataManager();
        oConnCount++;

    }

    public DataManager DBManager() {

        return oDBManager;
    }

    public DataManager DBManagerPatch() {

        if (oDBManagerPatch == null) {
            oDBManagerPatch = new DataManager(true);
        }
        return oDBManagerPatch;
    }

    public void Dispose() {

        if (--oConnCount == 0) {
            oDBManager.Dispose();
            oDBManager = null;
            if (oDBManagerPatch != null) {
                oDBManagerPatch.Dispose();
                oDBManagerPatch = null;
            }
        }
    }

    /**
     * Obtem a data e hora atual do servidor
     */
    public Date GetActualServerTime() {

        return oDBManager.GetDateTime();
    }
}
