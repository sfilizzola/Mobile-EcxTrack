package dev.sfilizzola.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import dev.ecxtrack.mobiletrack.App;

public class DialogUtil 
{
   //talvez pudessemos colocar um atributo do tipo Dialog
	//e retirar o parâmetro do método, mas preferi fazer assim, pois era a forma original.
	
	 /**
     * Verifica se o campo de texto precisa ser automaticamente selecionado. Caso precise, seleciona e apresenta o teclado
     * 
     * @param d Dialogo onde o campo de texto esta sendo apresentado
     * @param v Referencia para o campo de texto
     */
    public static void HandleAutoShowingKeyboard(final Dialog d, final EditText v) {

        v.postDelayed(new Runnable() {

            @Override
            public void run() {

                v.requestFocus();
                InputMethodManager imm = (InputMethodManager) App.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);

            }
        }, 250);

    }
}
