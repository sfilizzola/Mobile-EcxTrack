
package dev.ecxtrack.ecxtrack;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import dev.ecxtrack.ecxtrack.Entidades.Evento;
import dev.ecxtrack.ecxtrack.Entidades.Usuario;
import dev.ecxtrack.ecxtrack.Entidades.Veiculo;
import dev.sfilizzola.utils.ArrayList;


/**
 * @author samuel.filizzola
 */
public class App extends Application {



    // Constantes de notificacao

    // Objeto utilizado na sincronizacao da gravacao de dados da Instancia
    private static Object oLockObject = new Object();

    // Data em que o sistema foi inicializado
    public static final DateTime oDtInicializacao = new DateTime();

    // Usado para formatador dinheiro
    public static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    // Usado para formatador numeros
    public static final DecimalFormat numFormat = (DecimalFormat)NumberFormat.getNumberInstance(new Locale("pt", "BR"));

    // Usado para formatador numeros com casas decimais
    public static final DecimalFormat numFormatUS = (DecimalFormat)NumberFormat.getNumberInstance(Locale.ENGLISH);

    // Usado para formatar numeros com casas decimais
    public static final DecimalFormat numFormatDecimals = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    // Usado para formatar numeros com casas decimais
    public static final DecimalFormat numFormatDecimalsUS = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH));

    // Usado para formatar datas com Horas
    public static final DateFormat dtFormatLongMedium = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);

    // Usado para formatar datas com Horas
    public static final DateFormat dtFormatMediumMedium = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    // Usado para formatar datas Sem Horas
    public static final DateFormat dtFormatShortNone = DateFormat.getDateInstance(DateFormat.SHORT);

    // Usado para formatar datas Sem Horas
    public static final DateFormat dtFormatMediumNone = DateFormat.getDateInstance(DateFormat.MEDIUM);

    // Usado para formatar datas Sem Horas
    public static final DateFormat dtFormatLongNone = DateFormat.getDateInstance(DateFormat.LONG);

    // Contexto global utilizado pela aplicacao
    private static Context appContext;

    // Progress Dialog que � utilizado
    private static ProgressDialog oProgressDialog;

    // Imput manager utilizado no processo do teclado
    private static InputMethodManager imm;

    // Informa se a instancia foi carregada
    private static boolean instanceLoaded;

    // Verifica se pedido ja teve propriedades setada
    private static Boolean PedidoConfigurado = false;

    private static Usuario oUsuarioLogado;

    private static List<Veiculo> oVeiculosAtuais;

    private static Evento oEventoAtual;

    private static Veiculo oVeiculoSelecionado;


    /**
     * Obtem o contexto no qual a aplicacao esta sendo executada
     * 
     * @return
     */
    public static Context getContext() {

        return appContext;
    }



    @Override
    public void onCreate() {

        super.onCreate();
        appContext = this;

        //Instalacao Parse
        Parse.initialize(this, "hrXOihZn1s2Ve8OIeHVcmKDk86jIMBz9AKYlxFXA", "SCRAP68KGLAzdkwqQaUGhRUWmKXt8Td05ujS63GD");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

    }


    public static Context getAppContext() {

        return appContext;
    }

    public static void setAppContext(Context appContext) {

        App.appContext = appContext;
    }


    public static double parseDouble(String pString) throws ParseException {

        return numFormat.parse(pString).doubleValue();
    }

   /**
     * Apresenta um ProgressDialog
     * 
     * @param act
     * @param pMessage
     */
    public static void ProgressDialogShow(final Activity act, final String pMessage) {

        try {
            if (oProgressDialog != null)
                oProgressDialog.dismiss();
        } finally {
            oProgressDialog = null;
        }

        oProgressDialog = ProgressDialog.show(act, "", pMessage, true);

    }

    /**
     * Fecha o ProgressDialog que esteja aberto
     * 
     * @param act
     */
    public static void ProgressDialogDimiss(Activity act) {

        try {
            if (oProgressDialog != null)
                oProgressDialog.dismiss();
        } finally {
            oProgressDialog = null;
        }

    }

    public static void HideSoftKeyboard(final View v) 
    {     	
       imm.hideSoftInputFromWindow(v.getWindowToken(), 0);    	
    }

    public static void HideSoftKeyboard()
    {
    	imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
    }
    
    public static void ShowSoftKeyBoard(final View v)
    {
    	 imm.showSoftInputFromInputMethod(v.getWindowToken(), 0); 
    }
    
    public static void ShowSoftKeyBoard()
    {
    	imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
    }


    private boolean CheckAppInstanceData() {

        File oInstanceData = new File(appContext.getCacheDir(), "AppInstanceData.dat");
        return oInstanceData.exists() && oInstanceData.canRead();

    }

    /**
     * Limpa os dados da instancia do aplicativo
     */
    public static void CleanAppInstanceData() {

        synchronized (App.oLockObject) {

            File oInstanceData = new File(appContext.getCacheDir(), "AppInstanceData.dat");
            boolean vRetVal = oInstanceData.delete();
            if (vRetVal)
                android.util.Log.v("ECXSALDO", "Arquivo de Dados da Instancia excluido com sucesso.");
            else
                android.util.Log.v("ECXSALDO", "Arquivo de Dados da Instancia n�o excluido.");

        }
    }

    /**
     * Carrega os dados da instancia do aplicativo, se eles existirem
     */
    public static void OpenAppInstanceData() {

        synchronized (App.oLockObject) {

            File oFileInstanceData = new File(appContext.getCacheDir(), "AppInstanceData.dat");
            if (oFileInstanceData.exists() && oFileInstanceData.canRead()) {

                FileInputStream fis = null;
                ObjectInputStream is = null;

                try {

                    android.util.Log.v("ECXSALDO", "Desserializacao Iniciada");
                    fis = new FileInputStream(oFileInstanceData);
                    is = new ObjectInputStream(fis);

                    android.util.Log.v("ECXSALDO", "Desserializacao concluida");
                    setAppInstanceData((Object[])is.readObject());
                    android.util.Log.v("ECXSALDO", "Atribuicao concluida!");

                } catch (Exception e) {

                } finally {

                    try {

                        if (is != null) {
                            is.close();
                            is = null;
                        }
                        if (fis != null) {
                            fis.close();
                            fis = null;
                        }

                        android.util.Log.v("ECXSALDO", "Recursos liberados com sucesso!");

                    } catch (Exception e2) {
                        android.util.Log.e("ECXSALDO", "Problemas ao liberar recursos: " + e2.getMessage());
                    }

                }

            }

        }

    }

    public static Object[] getAppInstanceData() {

        return new Object[] { null };
    }

    public static void setAppInstanceData(Object[] oAppInstanceData) {

    }

    /**
     * Salva os dados presentes na class APP, para que os mesmos possam ser restaurados posteriormente, caso haja necessidade
     */
    public static void SaveAppInstanceData() {

        Thread tr = new Thread(new Runnable() {

            @Override
            public void run() {

                synchronized (App.oLockObject) {

                    // Cria o objeto a ser serializado
                    Object[] oAppInstanceData = getAppInstanceData();

                    // Cria os objetos utilizados na manipulacao dos arquivos
                    FileOutputStream fos = null;
                    ObjectOutputStream os = null;

                    try {
                        android.util.Log.v("ECXSALDO", "Serializacao Iniciada");
                        fos = new FileOutputStream(new File(appContext.getCacheDir(), "AppInstanceData.dat"));
                        os = new ObjectOutputStream(fos);
                        os.writeObject(oAppInstanceData);
                        android.util.Log.v("ECXSALDO", "Serializacao Concluida");
                    } catch (Exception e) {
                        // BugSenseHandler.sendExceptionMessage("CACHE", "ECXSALDO_SERIALIZE_INSTANCE", e);
                    } finally {

                        try {

                            if (os != null) {
                                os.close();
                                os = null;
                            }
                            if (fos != null) {
                                fos.close();
                                fos = null;
                            }

                            android.util.Log.v("ECXSALDO", "Recursos liberados com sucesso!");

                        } catch (Exception e2) {
                            android.util.Log.e("ECXSALDO", "Problemas ao liberar recursos: " + e2.getMessage());
                        }

                    }

                }

            }
        });
        tr.start();

    }

    public static boolean isInstanceLoaded() {

        return instanceLoaded;
    }

    public static Usuario getoUsuarioLogado() {
        return oUsuarioLogado;
    }

    public static void setoUsuarioLogado(Usuario oUsuarioLogado) {
        App.oUsuarioLogado = oUsuarioLogado;
    }

    public static List<Veiculo> getoVeiculosAtuais() {
        if (oVeiculosAtuais == null)
            oVeiculosAtuais = new ArrayList<Veiculo>();
        return oVeiculosAtuais;
    }

    public static void setoVeiculosAtuais(List<Veiculo> oVeiculosAtuais) {
        App.oVeiculosAtuais = oVeiculosAtuais;
    }

    public static Evento getoEventoAtual() {
        return oEventoAtual;
    }

    public static void setoEventoAtual(Evento oEventoAtual) {
        App.oEventoAtual = oEventoAtual;
    }

    public static Veiculo getoVeiculoSelecionado() {
        return oVeiculoSelecionado;
    }

    public static void setoVeiculoSelecionado(Veiculo oVeiculoSelecionado) {
        App.oVeiculoSelecionado = oVeiculoSelecionado;
    }
}
