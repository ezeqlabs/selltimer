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
import android.widget.Toast;

import com.onurciner.toastox.ToastOX;

import br.com.ezeqlabs.selltimer.dao.ClienteDAO;
import br.com.ezeqlabs.selltimer.helpers.ClienteHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class CadastroClientesActivity extends AppCompatActivity {
    private LinearLayout llEnderecos, llTelefones, llEmails;
    private ClienteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_clientes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        llEnderecos = (LinearLayout) findViewById(R.id.container_enderecos);
        llTelefones = (LinearLayout) findViewById(R.id.container_telefones);
        llEmails = (LinearLayout) findViewById(R.id.container_emails);

        helper = new ClienteHelper(CadastroClientesActivity.this);
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
                Cliente cliente = helper.pegaClienteDoFormulario();
                ClienteDAO dao = new ClienteDAO(this);

                dao.insere(cliente);
                dao.close();

                ToastOX.ok(this, getString(R.string.cliente_salvo_sucesso), Toast.LENGTH_SHORT);

                Intent detalhe = new Intent(this, DetalheClienteActivity.class);
                detalhe.putExtra(Constantes.CLIENTE_INTENT, cliente);
                startActivity(detalhe);
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
