package dev.sfilizzola.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private String TAG = "EcxTrackDatabase";

  /**
   * Este é o endereço onde o android salva os bancos de dados criado pela aplicação,
   * /data/data/<namespace da aplicacao>/databases/
   */
  public static String DBPATH = "/data/data/dev.ecxtrack.mobiletrack/databases/";

  // Este é o nome do banco de dados que iremos utilizar
  public static String DBNAME = "EcxTrack.s3db";
  
  public Context context;
  
  /**
   * O construtor necessita do contexto da aplicação
   */
  public DatabaseHelper(Context context) {
    /* O primeiro argumento é o contexto da aplicacao
     * O segundo argumento é o nome do banco de dados
     * O terceiro é um ponteiro para manipulação de dados,
     *   não precisaremos dele.
     * O quarto é a versão do banco de dados
     */
    super(context,DBNAME , null, 1);
    this.context = context;
    
    Log.i(TAG,"DatabaseHelper Instanciado com sucesso");
  }
  
  /**
   * Os métodos onCreate e onUpgrade precisam ser sobreescrito
   */
  @Override
  public void onCreate(SQLiteDatabase db) {

	  
  }
  
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    /*
     * Estamos criando a primeira versão do nosso banco de dados,
     * então não precisamos fazer nenhuma alteração neste método.
     * 
     */
  }
  
  /**
   * Método auxiliar que verifica a existencia do banco
   * da aplicação.
   */
  private boolean checkDataBase() {
    
    SQLiteDatabase db = null;
    
    try 
    {
      Log.d(TAG,"Verificando se db existe");
      String path = DBPATH + DBNAME;
      db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    
    } 
    catch (SQLiteException e) 
    {
    	 Log.e(TAG,"O DB não existe!");
    }
    finally
    {
    	if(db != null)
    		  db.close();
    }
    
    // Retorna verdadeiro se o banco existir, pois o ponteiro irá existir,
    // se não houver referencia é porque o banco não existe
    return db != null;
  }
  
  private void createDataBase()
  
  throws Exception {
    
    // Primeiro temos que verificar se o banco da aplicação
    // já foi criado
    boolean exists = checkDataBase();
    
    if(!exists) {
      // Chamaremos esse método para que o android
      // crie um banco vazio e o diretório onde iremos copiar
      // no banco que está no assets.

      
      // Se o banco de dados não existir iremos copiar o nosso
      // arquivo em /assets para o local onde o android os salva
      try 
      {
    	  Log.i(TAG,"Copiando banco do asstes");
        
    	  copyDatabase();

      }
      catch (IOException e)
      {
       Log.i(TAG,"Não foi possível copiar o arquivo");
      }
      
    }

  }
  
  /**
   * Esse método é responsável por copiar o banco do diretório
   * assets para o diretório padrão do android.
   */
  private void copyDatabase()throws IOException {
    
	  Log.i(TAG,"Preparando para copiar o arquivo...");
    String dbPath = DBPATH + DBNAME;
    
    File dir = new File(DBPATH);
    boolean result = dir.mkdirs();
    
    if(result)
    	 Log.i(TAG,"Pastas criada com sucesso!");
    else
    	 Log.i(TAG,"Pastas não criada! Bost...");
    
    
    Log.i(TAG,"Abrindo arquivo para copiar");
    // Abre o arquivo o destino para copiar o banco de dados
    OutputStream dbStream = new FileOutputStream(dbPath);
    
    
    Log.i(TAG,"Obtendo banco do assets");
    // Abre Stream do nosso arquivo que esta no assets
    InputStream dbInputStream = context.getAssets().open(DBNAME);
    
    
    
    Log.i(TAG,"Obtendo buffer de dados.");
    byte[] buffer = new byte[1024];
    int length;
    while((length = dbInputStream.read(buffer)) > 0) {
      dbStream.write(buffer, 0, length);
    }
    
    dbInputStream.close();
    Log.i(TAG, "Finalalizando operação.");
    dbStream.flush();
    dbStream.close();
    
  }
  
  public SQLiteDatabase getDatabase() {
    
    try{
    	
    	 Log.i(TAG,"Obtendo banco de dados.");
      // Verificando se o banco já foi criado e se não foi o
      // mesmo é criado.
      createDataBase();
      
      // Abrindo database
      String path = DBPATH + DBNAME;
      
      return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }catch (Exception e) {
    	 Log.i(TAG,"Foi retornado um banco vazio.");
      return getWritableDatabase();
    }

  }
}