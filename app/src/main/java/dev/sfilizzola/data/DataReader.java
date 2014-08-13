
package dev.sfilizzola.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import dev.sfilizzola.utils.Log;

public class DataReader implements Cursor {

    private boolean vInitialized = false;

    private Cursor pCursor;

    private static DateFormat iso8601Format;

    /**
     * Ctor
     * 
     * @param c
     */
    public DataReader(Cursor c) {

        pCursor = c;

        if (iso8601Format == null)
            iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

    @Override
    public void close() {

        pCursor.close();
        pCursor = null;
        Log.v("DATAREADER", "Data Reader Closed");
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {

        pCursor.copyStringToBuffer(columnIndex, buffer);
    }

    @Override
    public void deactivate() {

        pCursor.deactivate();
    }

    @Override
    public byte[] getBlob(int columnIndex) {

        return pCursor.getBlob(columnIndex);
    }

    @Override
    public int getColumnCount() {

        return pCursor.getColumnCount();
    }

    @Override
    public int getColumnIndex(String columnName) {

        return pCursor.getColumnIndex(columnName.toLowerCase());
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {

        return pCursor.getColumnIndexOrThrow(columnName.toLowerCase());
    }

    @Override
    public String getColumnName(int columnIndex) {

        return pCursor.getColumnName(columnIndex);
    }

    @Override
    public String[] getColumnNames() {

        return pCursor.getColumnNames();
    }

    @Override
    public int getCount() {

        return pCursor.getCount();
    }

    public Date getDate(int columnIndex) {

        try {
            return iso8601Format.parse(pCursor.getString(columnIndex));
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    public Date getDate(String columnName) {

        return this.getDate(pCursor.getColumnIndex(columnName.toLowerCase()));
    }

    public Date getDateOrNull(int columnIndex) {

        try {
            if (pCursor.isNull(columnIndex))
                return null;
            else
                return iso8601Format.parse(pCursor.getString(columnIndex));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public Date getDateOrNull(String columnName) {

        return this.getDateOrNull(pCursor.getColumnIndex(columnName.toLowerCase()));
    }

    @Override
    public double getDouble(int columnIndex) {

        return pCursor.getDouble(columnIndex);
    }

    @Override
    public int getType(int columnIndex) {
        return  pCursor.getType(columnIndex);
    }

    public double getDouble(String columnName) {

        return pCursor.getDouble(this.getColumnIndex(columnName.toLowerCase()));
    }

    public Double getDoubleOrNull(int columnIndex) {

        return pCursor.isNull(columnIndex) ? null : pCursor.getDouble(columnIndex);
    }

    public Double getDoubleOrNull(String columnName) {

        int i = this.getColumnIndex(columnName.toLowerCase());
        return getDoubleOrNull(i);
    }

    @Override
    public Bundle getExtras() {

        return pCursor.getExtras();
    }

    @Override
    public float getFloat(int columnIndex) {

        return pCursor.getFloat(columnIndex);
    }

    public float getFloat(String columnName) {

        return pCursor.getFloat(this.getColumnIndex(columnName.toLowerCase()));
    }

    public Float getFloatOrNull(int columnIndex) {

        return pCursor.isNull(columnIndex) ? null : pCursor.getFloat(columnIndex);
    }

    public Float getFloatOrNull(String columnName) {

        int i = this.getColumnIndex(columnName.toLowerCase());
        return getFloatOrNull(i);
    }

    @Override
    public int getInt(int columnIndex) {

        return pCursor.getInt(columnIndex);
    }

    public int getInt(String columnName) {

        return pCursor.getInt(this.getColumnIndex(columnName.toLowerCase()));
    }

    public Integer getIntOrNull(int columnIndex) {

        return pCursor.isNull(columnIndex) ? null : pCursor.getInt(columnIndex);
    }

    public Integer getIntOrNull(String columnName) {

        int i = this.getColumnIndex(columnName.toLowerCase());
        return getIntOrNull(i);
    }

    @Override
    public long getLong(int columnIndex) {

        return pCursor.getLong(columnIndex);
    }

    public long getLong(String columnName) {

        return pCursor.getLong(this.getColumnIndex(columnName.toLowerCase()));
    }

    public Long getLongOrNull(int columnIndex) {

        return pCursor.isNull(columnIndex) ? null : pCursor.getLong(columnIndex);
    }

    public Long getLongOrNull(String columnName) {

        int i = this.getColumnIndex(columnName.toLowerCase());
        return getLongOrNull(i);
    }

    @Override
    public int getPosition() {

        return pCursor.getPosition();
    }

    @Override
    public short getShort(int columnIndex) {

        return pCursor.getShort(columnIndex);
    }

    public short getShort(String columnName) {

        return pCursor.getShort(this.getColumnIndex(columnName.toLowerCase()));
    }

    public Short getShortOrNull(int columnIndex) {

        return pCursor.isNull(columnIndex) ? null : pCursor.getShort(columnIndex);
    }

    public Short getShortOrNull(String columnName) {

        int i = this.getColumnIndex(columnName.toLowerCase());
        return getShortOrNull(i);
    }

    @Override
    public String getString(int columnIndex) {

        String vRetval = pCursor.getString(columnIndex);
        return vRetval != null ? vRetval : "";
    }

    public String getString(String columnName) {

        return this.getString(this.getColumnIndex(columnName.toLowerCase()));
    }

    public String getStringOrNull(int columnIndex) {

        return pCursor.isNull(columnIndex) ? null : pCursor.getString(columnIndex);
    }

    public String getStringOrNull(String columnName) {

        int i = this.getColumnIndex(columnName.toLowerCase());
        return getStringOrNull(i);
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {

        return pCursor.getWantsAllOnMoveCalls();
    }

    @Override
    public boolean isAfterLast() {

        return pCursor.isAfterLast();
    }

    @Override
    public boolean isBeforeFirst() {

        return pCursor.isBeforeFirst();
    }

    @Override
    public boolean isClosed() {

        return pCursor.isClosed();
    }

    @Override
    public boolean isFirst() {

        return pCursor.isFirst();
    }

    @Override
    public boolean isLast() {

        return pCursor.isLast();
    }

    @Override
    public boolean isNull(int columnIndex) {

        return pCursor.isNull(columnIndex);
    }

    public boolean isNull(String columnName) {

        return pCursor.isNull(this.getColumnIndex(columnName.toLowerCase()));

    }

    @Override
    public boolean move(int offset) {

        return pCursor.move(offset);
    }

    @Override
    public boolean moveToFirst() {

        return pCursor.moveToFirst();
    }

    @Override
    public boolean moveToLast() {

        return pCursor.moveToLast();
    }

    @Override
    public boolean moveToNext() {

        return pCursor.moveToNext();
    }

    @Override
    public boolean moveToPosition(int position) {

        return pCursor.moveToPosition(position);
    }

    @Override
    public boolean moveToPrevious() {

        return pCursor.moveToPrevious();
    }

    /**
     * Faz a leitura do cursor em modo sequencial
     * 
     * @return
     */
    public boolean Read() {

        if (vInitialized == false) {
            vInitialized = true;
            Log.v("DATAREADER", "Data Reader Opened");
            return this.moveToFirst();
        } else {
            return this.moveToNext();
        }

    }

    @Override
    public void registerContentObserver(ContentObserver observer) {

        pCursor.registerContentObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

        pCursor.registerDataSetObserver(observer);

    }

    @Override
    public boolean requery() {

        return pCursor.requery();
    }

    @Override
    public Bundle respond(Bundle extras) {

        return pCursor.respond(extras);
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {

        pCursor.setNotificationUri(cr, uri);
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {

        pCursor.unregisterContentObserver(observer);

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

        pCursor.unregisterDataSetObserver(observer);
    }

}
