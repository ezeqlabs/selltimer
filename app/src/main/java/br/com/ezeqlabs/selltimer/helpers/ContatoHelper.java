package br.com.ezeqlabs.selltimer.helpers;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.ezeqlabs.selltimer.CadastroContatoActivity;
import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.model.Contato;

public class ContatoHelper {
    private EditText data, anotacoes;
    private Spinner interesse;
    private Contato contato;
    private ArrayAdapter<CharSequence> adapter;

    public ContatoHelper(CadastroContatoActivity activity){
        data = (EditText) activity.findViewById(R.id.data_contato);
        interesse = (Spinner) activity.findViewById(R.id.interesse_contato);
        anotacoes = (EditText) activity.findViewById(R.id.descricao_contato);

        contato = new Contato();
    }

    public EditText getData() {
        return data;
    }

    public EditText getAnotacoes() {
        return anotacoes;
    }

    public Spinner getInteresse() {
        return interesse;
    }

    public Contato pegaContatoDoFormulario(){
        contato.setData(data.getText().toString());
        contato.setRetorno(preparaRetorno());
        contato.setInteresse(interesse.getSelectedItem().toString());
        contato.setAnotacoes(anotacoes.getText().toString());

        return contato;
    }

    public void colocaContatoNoFormulario(Contato contato, ArrayAdapter<CharSequence> adapter){
        data.setText(contato.getData());
        anotacoes.setText(contato.getAnotacoes());
        setSpinner(contato, adapter);
    }

    private void setSpinner(Contato contato, ArrayAdapter<CharSequence> adapter){
        int posicao = adapter.getPosition(contato.getInteresse());
        interesse.setSelection(posicao);
    }

    private String preparaRetorno(){
        String dataRetorno = "";
        String dataContato = data.getText().toString();
        Long interesseReal = interesse.getSelectedItemId();
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();

        if(!dataContato.equals("")){
            try {
                Date date = sourceFormat.parse(dataContato);
                c.setTime(date);

                if(interesseReal == 1){ // MUITO = +3
                    c.add(Calendar.DATE, 3);
                } else if(interesseReal == 2){ // MEDIO = +10
                    c.add(Calendar.DATE, 10);
                } else if(interesseReal == 3){ // POUCO = +25
                    c.add(Calendar.DATE, 25);
                }

                date = c.getTime();
                dataRetorno = sourceFormat.format(date);

            } catch (ParseException e) {
                dataRetorno = "";
            }
        }

        return dataRetorno;
    }
}
