package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CadastroClientesActivity extends AppCompatActivity {
    private LinearLayout llEnderecos, llTelefones, llEmails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_clientes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        llEnderecos = (LinearLayout) findViewById(R.id.container_enderecos);
        llTelefones = (LinearLayout) findViewById(R.id.container_telefones);
        llEmails = (LinearLayout) findViewById(R.id.container_emails);
    }

    public void novoEndereco(View v){
        llEnderecos.addView(geraEditText(R.string.label_endereco));
    }

    public void novoTelefone(View v){
        llTelefones.addView(geraEditText(R.string.label_telefone));
    }

    public void novoEmail(View v){
        llEmails.addView(geraEditText(R.string.label_email));
    }

    private EditText geraEditText(int hint){
        EditText editText = new EditText(this);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setHint(hint);
        return editText;
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
                Intent detalhe = new Intent(this, DetalheClienteActivity.class);
                startActivity(detalhe);
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
