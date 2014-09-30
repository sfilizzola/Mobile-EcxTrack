package dev.ecxtrack.mobiletrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.DateTime;

import dev.sfilizzola.utils.Log;

public class DatePickerFragment extends DialogFragment implements DialogInterface.OnClickListener {

    DatePicker dtInical;
    DatePicker dtFinal;
    TimePicker hrInicial;
    TimePicker hrFinal;
    Button btnOk;
    Button btnCancelar;

    @Override
    public Dialog onCreateDialog(Bundle savedinstance){
       Dialog dlg = new Dialog(getActivity());
        dlg.setContentView(R.layout.dialog_trajetos);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setTitle("Listar Trajeto");

        dtInical = (DatePicker) dlg.findViewById(R.id.dtInicial);
        dtInical.setCalendarViewShown(false);

        dtFinal = (DatePicker) dlg.findViewById(R.id.dtFinal);
        dtFinal.setCalendarViewShown(false);

        hrInicial = (TimePicker) dlg.findViewById(R.id.timeInicio);
        hrInicial.setIs24HourView(true);
        hrInicial.setCurrentHour(6);
        hrInicial.setCurrentMinute(0);
        hrFinal = (TimePicker) dlg.findViewById(R.id.timeFim);
        hrFinal.setIs24HourView(true);
        hrFinal.setCurrentHour(23);
        hrFinal.setCurrentMinute(59);

        btnOk = (Button)dlg.findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main main = (Main) getActivity();

                DateTime dataInicioSetada = new DateTime(dtInical.getYear(), dtInical.getMonth()+1, dtInical.getDayOfMonth(), hrInicial.getCurrentHour(), hrInicial.getCurrentMinute());
                DateTime dataFinalSetada = new DateTime(dtFinal.getYear(), dtFinal.getMonth()+1, dtFinal.getDayOfMonth(), hrFinal.getCurrentHour(), hrFinal.getCurrentMinute());

                if (dataInicioSetada.getMillis() > dataFinalSetada.getMillis()) {
                    Toast.makeText(getActivity(), "Data Inicial maior que final, favor trocar", Toast.LENGTH_LONG).show();

                } else {
                    main.MontaTrajeto(dataInicioSetada, dataFinalSetada);
                    getDialog().dismiss();
                }
            }
        });

        btnCancelar = (Button)dlg.findViewById(R.id.btnCancel);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return dlg;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }
}
