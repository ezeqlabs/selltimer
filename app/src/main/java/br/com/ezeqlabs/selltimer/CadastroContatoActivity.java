package br.com.ezeqlabs.selltimer;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onurciner.toastox.ToastOX;

import java.text.ParseException;
import java.util.Calendar;

import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.helpers.ContatoHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.utils.Constantes;
import br.com.ezeqlabs.selltimer.utils.Datas;

public class CadastroContatoActivity extends AppCompatActivity {
    private EditText dataContato;
    private Cliente cliente;
    private ContatoHelper helper;
    private DatabaseHelper databaseHelper;
    private Contato contato;
    private Long clienteId;
    private Long contatoId;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preparaVariaveis();
        preparaSpinner();
        preparaFormulario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_salvar:
                salvaOuEditaContato();
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void preparaVariaveis(){
        spinner = (Spinner) findViewById(R.id.interesse_contato);
        dataContato = (EditText) findViewById(R.id.data_contato);
        helper = new ContatoHelper(this);
        cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);
        contato = (Contato) getIntent().getSerializableExtra(Constantes.CONTATO_INTENT);
        clienteId = cliente.getId();
        databaseHelper = new DatabaseHelper(this);
    }

    private void preparaSpinner(){
        adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_de_interesse,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void preparaFormulario(){
        if( contato != null ){
            helper.colocaContatoNoFormulario(contato, adapter);
        }
    }

    private void salvaOuEditaContato(){
        if( contato == null ){
            salvarContato();
        }else{
            atualizaContato();
        }
    }

    private void salvarContato(){
        contato = helper.pegaContatoDoFormulario();

        if(contatoValido()){
            contatoId = databaseHelper.insereContato(contato, clienteId);
            databaseHelper.close();
            redirecionaContato(R.string.contato_salvo_sucesso);
        }else{
            contato = null;
        }
    }

    private void atualizaContato(){
        Contato tmp = helper.pegaContatoDoFormulario();

        contato.setData( tmp.getData() );
        contato.setInteresse( tmp.getInteresse() );
        contato.setAnotacoes( tmp.getAnotacoes() );

        if(contatoValido()){
            if( contato.getId() != null ) {
                contatoId = contato.getId();
                databaseHelper.atualizaContato(contato, clienteId);
                databaseHelper.close();
                redirecionaContato(R.string.contato_atualizado_sucesso);
            }else{
                ToastOX.error(this, getString(R.string.erro_atualizar_contato), Toast.LENGTH_SHORT);
                voltaParaDetalhe();
            }
        }
    }

    private boolean contatoValido(){
        boolean valido = true;

        if(!dataValida()){
            valido = false;
        }

        if( contato.getInteresse().equalsIgnoreCase(getString(R.string.label_interesse)) ){
            TextView errorText = (TextView) helper.getInteresse().getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getString(R.string.erro_interesse));

            valido = false;
        }

        return valido;
    }

    private boolean dataValida(){
        helper.getData().setError(null);
        String data = contato.getData();

        if( data.equalsIgnoreCase("") ){
            helper.getData().setError(getString(R.string.erro_data));
            return false;
        }

        if( data.length() != 10 ){
            helper.getData().setError(getString(R.string.erro_data_invalida));
            return false;
        }


        if( !data.matches("\\d{1,2}/\\d{1,2}/\\d{4}") ){
            helper.getData().setError(getString(R.string.erro_data_invalida));
            return false;
        }

        if( !((Character) data.charAt(2)).toString().equals("/") &&
                !((Character) data.charAt(5)).toString().equals("/") ){
            helper.getData().setError(getString(R.string.erro_data_invalida));
            return false;
        }

        if( getIntDataInput() > getIntDataAtual() ){
            helper.getData().setError(getString(R.string.erro_data_invalida));
            return false;
        }

        return true;
    }

    private int getIntDataInput(){
        String[] blocosData = contato.getData().split("/");
        String input = blocosData[2] + blocosData[1] + blocosData[0];

        return Integer.parseInt(input);
    }

    private int getIntDataAtual(){
        String atual = Datas.dataAtual().replaceAll("-", "");
        return Integer.parseInt(atual);
    }

    private void redirecionaContato(int mensagem){
        contato.setId(contatoId);
        ToastOX.ok(this, getString(mensagem), Toast.LENGTH_SHORT);
        voltaParaDetalhe();
    }

    private void voltaParaDetalhe(){
        Intent detalhe = new Intent(this, DetalheContatoActivity.class);
        detalhe.putExtra(Constantes.CLIENTE_INTENT, cliente);
        detalhe.putExtra(Constantes.CONTATO_INTENT, contato);
        startActivity(detalhe);
        finish();
    }

    public void abreDatepicker(View v){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dataContato.setText(preparaTextoData(dayOfMonth, (monthOfYear + 1), year));

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private String preparaTextoData(int dia, int mes, int ano){
        String texto = "";

        texto += dia > 9 ? dia : "0"+dia;
        texto += "/";
        texto += mes > 9 ? mes : "0"+mes;
        texto += "/";
        texto += ano;

        return texto;
    }

    public void abreAjudaInteresse(View v){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_info)
                .setTitle(getString(R.string.ajuda))
                .setMessage(getString(R.string.mensagem_ajuda_interesse))
                .setPositiveButton(getString(R.string.entendi), null)
                .show();
    }
}
