package dev.sfilizzola.data.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sfilizzola.garantido.App;

public class Resources {

    /**
     * Obtem um comando SQL da biblioteca de Resources
     * @param pFolders
     * @param pFileName
     * @return
     */
    public static String GetSQL(String[] pFolders, String pFileName) 
    {

        // Stringbuilder que sera utilizado no processamento
        StringBuilder sb = new StringBuilder("SQL/");

        // Cria o nome do arquivo a ser carregado
        for (String s : pFolders) {
            sb.append(s);
            sb.append("/");
        }

        // Adiciona o nome do Arquivo
        sb.append(pFileName);

        try 
        {

            // Obtem o contexto definido globalmente e abre o arquivo do Assets
            InputStream is = App.getContext().getAssets().open(sb.toString());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = null;

            // Instancia do stringbuilder que sera utilizada para leitura do arquivo
            sb = new StringBuilder();

            while ((s = br.readLine()) != null)
                sb.append(s + "\r\n");

            br.close();
            is.close();
            return sb.toString();

        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

    }

}
