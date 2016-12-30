package br.com.ezeqlabs.selltimer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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

public class CadastroContatoActivity extends AppCompatActivity {
    private EditText dataContato;
    private Cliente cliente;
    private ContatoHelper helper;
    private DatabaseHelper databaseHelper;
    private Contato contato;
    private Long clienteId;
    private Long contatoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.interesse_contato);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_de_interesse, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dataContato = (EditText) findViewById(R.id.data_contato);

        helper = new ContatoHelper(this);
        cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);
    }

    @Override
    protected void onStop(){
        super.onStop();
        finish();
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
                salvarContato();
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salvarContato(){
        contato = helper.pegaContatoDoFormulario();
        databaseHelper = new DatabaseHelper(this);
        clienteId = cliente.getId();

        if(contatoValido()){
            contatoId = databaseHelper.insereContato(contato, clienteId);
            databaseHelper.close();
            redirecionaContato();
        }
    }

    private boolean contatoValido(){
        boolean valido = true;

        if( contato.getData().equalsIgnoreCase("") ){
            helper.getData().setError(getString(R.string.erro_data));
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

    private void redirecionaContato(){
        contato.setId(contatoId);

        ToastOX.ok(this, getString(R.string.contato_salvo_sucesso), Toast.LENGTH_LONG);

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

        texto += dia + "/";
        texto += mes > 9 ? mes : "0"+mes;
        texto += "/";
        texto += ano;

        return texto;
    }
}
