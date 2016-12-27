package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.model.Telefone;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class DetalheClienteActivity extends AppCompatActivity {
    private LinearLayout llEndereco, llTelefone, llEmail, llContato;
    private List<Endereco> enderecos;
    private List<Telefone> telefones;
    private List<Email> emails;
    private List<Contato> contatos;
    private Cliente cliente;
    private Contato contato;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private TextView nomeCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        atribuiVariaveis();

    }

    @Override
    protected void onResume(){
        super.onResume();
        preparaNomeCliente();
        preparaEnderecos();
        preparaTelefone();
        preparaEmail();
        preparaContatos();
    }

    @Override
    protected void onStop(){
        super.onStop();
        llContato.removeAllViews();
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
        cadastroContato.putExtra(Constantes.CLIENTE_INTENT, cliente);
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

    private void atribuiVariaveis(){
        llEndereco = (LinearLayout) findViewById(R.id.container_enderecos_detalhe);
        llTelefone = (LinearLayout) findViewById(R.id.container_telefones_detalhe);
        llEmail = (LinearLayout) findViewById(R.id.container_emails_detalhe);
        llContato = (LinearLayout) findViewById(R.id.container_contatos_cliente);
        nomeCliente = (TextView) findViewById(R.id.nome_cliente_detalhe);

        cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);
        enderecos = cliente.getEnderecos();
        telefones = cliente.getTelefones();
        emails = cliente.getEmails();
    }

    private void preparaNomeCliente(){
        nomeCliente.setText(cliente.getNome());
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

    private void preparaContatos(){
        contatos = databaseHelper.getContatosCliente( cliente.getId() );
        if(!contatos.isEmpty()){

            for(Contato con : contatos){
                Button botao = (Button) LayoutInflater.from(this).inflate(R.layout.button_contato, null);
                botao.setId(Integer.parseInt(String.valueOf(con.getId())));
                botao.setText( con.toString() );

                llContato.addView(botao);
            }

        }
    }

    public void abreContato(View v){
        for( int i = 0; i < contatos.size(); i++ ){
            if( v.getId() == contatos.get(i).getId() ){
                contato = contatos.get(i);
            }
        }

        Intent detalhe = new Intent(this, DetalheContatoActivity.class);
        detalhe.putExtra(Constantes.CLIENTE_INTENT, cliente);
        detalhe.putExtra(Constantes.CONTATO_INTENT, contato);
        startActivity(detalhe);
    }
}
