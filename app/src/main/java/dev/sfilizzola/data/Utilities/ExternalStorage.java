
package dev.sfilizzola.data.Utilities;


import android.os.Environment;

public class ExternalStorage {

    /**
     * Verifica se o armazenamento extrono esta disponivel
     * 
     * @return
     */
    public static Boolean isExternalStorageAvailable() {

        // Tenta salvar as configuracoes em um arquivo de texto no cartao de memoria...
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        return mExternalStorageAvailable && mExternalStorageWriteable;

    }

}
