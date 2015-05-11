package dev.ecxtrack.ecxtrack;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.helpshift.Helpshift;


public class HelpFragment extends DialogFragment implements DialogInterface.OnClickListener {


    Button btnChat, btnEmail, btnTelefone, btnGuia;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dlg = new Dialog(getActivity());
        dlg.setContentView(R.layout.fragment_help);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setTitle("Precisa de Ajuda ?");

        btnChat = (Button)dlg.findViewById(R.id.btnChat);
        btnEmail = (Button)dlg.findViewById(R.id.btnEmail);
        btnTelefone = (Button)dlg.findViewById(R.id.btnTelefone);
        btnGuia = (Button)dlg.findViewById(R.id.btnGuia);

        btnTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "(31)3279-0062"));
                startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "coordenadores@ecx.com.br" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(intent, "Mandar E-mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Não existem clientes de E-mail instalados.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helpshift.showConversation(getActivity());
            }
        });

        btnGuia.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Intent intent = new Intent(context, ActScreenSlide.class);
                context.startActivity(intent);
            }
        });


        return dlg;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }

}
