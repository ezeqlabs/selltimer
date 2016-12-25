package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.model.Telefone;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class DetalheClienteActivity extends AppCompatActivity {
    private ListView listView;
    private LinearLayout llEndereco, llTelefone, llEmail;
    private List<Endereco> enderecos;
    private List<Telefone> telefones;
    private List<Email> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listview_contatos);
        llEndereco = (LinearLayout) findViewById(R.id.container_enderecos_detalhe);
        llTelefone = (LinearLayout) findViewById(R.id.container_telefones_detalhe);
        llEmail = (LinearLayout) findViewById(R.id.container_emails_detalhe);

        Cliente cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);
        enderecos = cliente.getEnderecos();
        telefones = cliente.getTelefones();
        emails = cliente.getEmails();

        TextView nomeCliente = (TextView) findViewById(R.id.nome_cliente_detalhe);
        nomeCliente.setText(cliente.getNome());

        preparaEnderecos();
        preparaTelefone();
        preparaEmail();





        List<String> contatos = new ArrayList<>();
        contatos.add("19/12/2016 - Muito interessado");
        contatos.add("17/12/2016 - Muito interessado");
        contatos.add("14/12/2016 - Muito interessado");
        contatos.add("11/12/2016 - Interessado");
        contatos.add("06/12/2016 - Interessado");
        contatos.add("28/11/2016 - Interessado");
        contatos.add("22/11/2016 - Pouco interessado");
        contatos.add("16/11/2016 - Pouco interessado");
        contatos.add("01/11/2016 - Pouco interessado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contatos);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detalheContato = new Intent(DetalheClienteActivity.this, DetalheContatoActivity.class);
                startActivity(detalheContato);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent listagem = new Intent(this, ClientesActivity.class);
                startActivity(listagem);
                
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void adicionaContato(View v){
        Intent cadastroContato = new Intent(this, CadastroContatoActivity.class);
        startActivity(cadastroContato);
    }

    private void geraEndereco(Endereco endereco){
        llEndereco.addView(geraTextView(endereco.getEndereco()));
    }

    private void geraTelefone(Telefone telefone){
        llTelefone.addView(geraTextView(telefone.getTelefone()));
    }

    private void geraEmail(Email email){
        llEmail.addView(geraTextView(email.getEmail()));
    }

    private TextView geraTextView(String texto){
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(texto);
        return textView;
    }

    private void preparaEnderecos(){
        if(enderecos.size() > 0){
            for(Endereco endereco : enderecos){
                geraEndereco(endereco);
            }
        }else{
            findViewById(R.id.titulo_endereco_detalhe).setVisibility(View.GONE);
            findViewById(R.id.container_enderecos_detalhe).setVisibility(View.GONE);
        }
    }

    private void preparaTelefone(){
        if(telefones.size() > 0){
            for(Telefone telefone : telefones){
                geraTelefone(telefone);
            }
        }else{
            findViewById(R.id.titulo_telefone_detalhe).setVisibility(View.GONE);
            findViewById(R.id.container_telefones_detalhe).setVisibility(View.GONE);
        }
    }

    private void preparaEmail(){
        if(emails.size() > 0){
            for(Email email : emails){
                geraEmail(email);
            }
        }else{
            findViewById(R.id.titulo_emails_detalhes).setVisibility(View.GONE);
            findViewById(R.id.container_emails_detalhe).setVisibility(View.GONE);
        }
    }
}
