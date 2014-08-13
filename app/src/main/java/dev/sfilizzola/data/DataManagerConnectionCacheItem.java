
package dev.sfilizzola.data;

import android.database.sqlite.SQLiteDatabase;

class DataManagerConnectionCacheItem {

    int dbConnectionCount;
    String dbConnectionString;
    SQLiteDatabase dbConnection;

}
