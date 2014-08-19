
package dev.sfilizzola.utils;

import java.text.NumberFormat;

import android.widget.TextView;

/**
 * Classe auxiliar utilizada no processo de definir valores para um TextView, quando utilizamos um ViewHolder
 * 
 * @author Samuel Filizzola
 */
public abstract class ViewHolder {

    private static String EMPTY = "";

    /**
     * Valida os parametros para ver se o campo podera ser definido
     * 
     * @param pTextView
     * @param pValue
     * @return
     */
    private Boolean ValidateParams(TextView pTextView, Object pValue) {

        if (pTextView == null)
            return false;
        else if (pValue == null) {
            pTextView.setText(EMPTY);
            return false;
        } else
            return true;

    }

    public void setString(TextView pTextView, String pString) {

        if (ValidateParams(pTextView, pString))
            pTextView.setText(pString);

    }

    public void setDouble(TextView pTextView, Double pDouble) {

        if (ValidateParams(pTextView, pDouble))
            pTextView.setText(pDouble.toString());

    }

    public void setDouble(TextView pTextView, Double pDouble, NumberFormat pNumberFormat) {

        if (ValidateParams(pTextView, pDouble)) {
            if (pNumberFormat != null)
                pTextView.setText(pNumberFormat.format(pDouble));
            else
                pTextView.setText(pDouble.toString());
        }

    }

    public void setDouble(TextView pTextView, Double pDouble, String pFormat) {

        if (ValidateParams(pTextView, pDouble)) {
            if (pFormat != null)
                pTextView.setText(String.format(pFormat, pDouble));
            else
                pTextView.setText(pDouble.toString());
        }

    }

    public void setInteger(TextView pTextView, Integer pInteger) {

        if (ValidateParams(pTextView, pInteger))
            pTextView.setText(pInteger.toString());

    }

    public void setInteger(TextView pTextView, Integer pInteger, NumberFormat pNumberFormat) {

        if (ValidateParams(pTextView, pInteger)) {
            if (pNumberFormat != null)
                pTextView.setText(pNumberFormat.format(pInteger));
            else
                pTextView.setText(pInteger.toString());
        }

    }

    public void setInteger(TextView pTextView, Integer pInteger, String pFormat) {

        if (ValidateParams(pTextView, pInteger)) {
            if (pFormat != null)
                pTextView.setText(String.format(pFormat, pInteger));
            else
                pTextView.setText(pInteger.toString());
        }

    }

}
