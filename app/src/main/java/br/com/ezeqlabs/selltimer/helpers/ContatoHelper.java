package br.com.ezeqlabs.selltimer.helpers;

import android.app.Activity;
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
import br.com.ezeqlabs.selltimer.utils.Datas;

public class ContatoHelper {
    private EditText data, anotacoes;
    private Spinner interesse;
    private Contato contato;
    private ArrayAdapter<CharSequence> adapter;
    private Activity activity;

    public ContatoHelper(CadastroContatoActivity activity){
        this.activity = activity;
        data = (EditText) activity.findViewById(R.id.data_contato);
        interesse = (Spinner) activity.findViewById(R.id.interesse_contato);
        anotacoes = (EditText) activity.findViewById(R.id.descricao_contato);

        contato = new Contato();
    }

    public EditText getData() {
        return data;
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

    public boolean dataValida(String data){
        this.data.setError(null);

        if( data.equalsIgnoreCase("") ){
            this.data.setError(activity.getString(R.string.erro_data));
            return false;
        }

        if( data.length() != 10 ){
            this.data.setError(activity.getString(R.string.erro_data_invalida));
            return false;
        }


        if( !data.matches("\\d{1,2}/\\d{1,2}/\\d{4}") ){
            this.data.setError(activity.getString(R.string.erro_data_invalida));
            return false;
        }

        if( !((Character) data.charAt(2)).toString().equals("/") &&
                !((Character) data.charAt(5)).toString().equals("/") ){
            this.data.setError(activity.getString(R.string.erro_data_invalida));
            return false;
        }

        if( getIntDataInput(data) > getIntDataAtual() ){
            this.data.setError(activity.getString(R.string.erro_data_invalida));
            return false;
        }

        return true;
    }

    private int getIntDataInput(String data){
        String[] blocosData = data.split("/");
        String input = blocosData[2] + blocosData[1] + blocosData[0];

        return Integer.parseInt(input);
    }

    private int getIntDataAtual(){
        String atual = Datas.dataAtual().replaceAll("-", "");
        return Integer.parseInt(atual);
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
