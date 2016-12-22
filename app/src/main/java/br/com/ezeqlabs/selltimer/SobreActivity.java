package br.com.ezeqlabs.selltimer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ezeqlabs.selltimer.utils.Constantes;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtVersao = (TextView) findViewById(R.id.txtVersao);
        try {
            txtVersao.setText(Constantes.getVersao(this));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void enviarFeedback(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_feedback, null))
                .setPositiveButton(getString(R.string.enviar_dialog_feedback),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String assunto, tipo, descricao;
                                Dialog view = (Dialog) dialog;

                                EditText tituloFeedback = (EditText) view.findViewById(R.id.titulo_feedback);
                                Spinner tipoFeedback = (Spinner) view.findViewById(R.id.tipo_feedback);
                                EditText descricaoFeedback = (EditText) view.findViewById(R.id.descricao_feedback);

                                assunto = tituloFeedback.getText().toString();
                                tipo = tipoFeedback.getSelectedItem().toString();
                                descricao = descricaoFeedback.getText().toString();

                                enviarEmail(assunto, tipo, descricao);
                            }
                        })
                .setNegativeButton(getString(R.string.cancelar_dialog_feedback),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

        builder.create();
        builder.show();
    }

    public void enviarEmail(String assunto, String tipo, String descricao){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ezeqlabs@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.assunto_email_feedback, tipo, assunto));
        i.putExtra(Intent.EXTRA_TEXT   , descricao);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(Intent.createChooser(i, getString(R.string.titulo_chooser_feedback, tipo.toLowerCase())));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, getString(R.string.erro_chooser_feedback), Toast.LENGTH_SHORT).show();
        }
    }
}
