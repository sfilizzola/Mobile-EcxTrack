
package dev.sfilizzola.data;

import java.util.List;

import dev.sfilizzola.utils.ArrayList;
import dev.sfilizzola.utils.Log;
import android.database.sqlite.SQLiteDatabase;

public class DataManagerConnectionCache {

    // Cria a lista com o cache de conexoes
    static List<DataManagerConnectionCacheItem> connCache = new ArrayList<DataManagerConnectionCacheItem>();

    /**
     * Finaliza uma conexao do cache
     * 
     * @param connectionString
     */
    public static void CloseConnection(String connectionString) {

        synchronized (connCache) {

            DataManagerConnectionCacheItem connItem = findItem(connectionString);

            if (connItem != null) {

                // Decrementa o contador
                // Se o contador for zero, finaliza a conexao
                if (--connItem.dbConnectionCount <= 0 && connItem.dbConnection != null) {

                    if (connItem.dbConnection.isOpen())
                        connItem.dbConnection.close();

                    connItem.dbConnection = null;

                }

                Log.v("DATABASE", String.format("Database Open Count -: %d", connItem.dbConnectionCount));

            }

        }

    }

    public static SQLiteDatabase OpenConnection(String connectionString) {

        synchronized (connCache) {

            DataManagerConnectionCacheItem connItem = findItem(connectionString);

            if (connItem == null) {
                connItem = new DataManagerConnectionCacheItem();
                connCache.add(connItem);
            }

            if (connItem.dbConnection == null || connItem.dbConnectionCount == 0) {
                connItem.dbConnection = SQLiteDatabase.openDatabase(connectionString, null, SQLiteDatabase.OPEN_READWRITE);
                connItem.dbConnectionString = connectionString;
            }

            // Incrementa o contador
            connItem.dbConnectionCount++;

            // Informa o contador atual
            Log.v("DATABASE", String.format("Database Open Count +: %d", connItem.dbConnectionCount));

            return connItem.dbConnection;
        
        }

    }

    private static DataManagerConnectionCacheItem findItem(String connStr) {

        DataManagerConnectionCacheItem retObj = null;

        for (int i = 0; i < connCache.size(); i++) {
            if (connCache.get(i).dbConnectionString.equalsIgnoreCase(connStr)) {
                retObj = connCache.get(i);
                break;
            }
        }

        return retObj;

    }

}
