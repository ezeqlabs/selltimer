package br.com.ezeqlabs.selltimer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.onurciner.toastox.ToastOX;

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

        AdView mAdView = (AdView) findViewById(R.id.adViewCliente);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        limpaLayout();
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
                finish();
                return true;
            case R.id.action_deletar:
                exibeAlertDelete();
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
        if(enderecos != null){
            if(enderecos.size() > 0){
                for(Endereco endereco : enderecos){
                    geraEndereco(endereco);
                }
            }else{
                apagaView(R.id.titulo_endereco_detalhe, R.id.container_enderecos_detalhe);
            }
        } else{
            apagaView(R.id.titulo_endereco_detalhe, R.id.container_enderecos_detalhe);
        }
    }

    private void preparaTelefone(){
        if(telefones != null){
            if(telefones.size() > 0){
                for(Telefone telefone : telefones){
                    geraTelefone(telefone);
                }
            }else{
                apagaView(R.id.titulo_telefone_detalhe, R.id.container_telefones_detalhe);
            }
        }else{
            apagaView(R.id.titulo_telefone_detalhe, R.id.container_telefones_detalhe);
        }
    }

    private void preparaEmail(){
        if(emails != null){
            if(emails.size() > 0){
                for(Email email : emails){
                    geraEmail(email);
                }
            }else{
                apagaView(R.id.titulo_emails_detalhes, R.id.container_emails_detalhe);
            }
        }else{
            apagaView(R.id.titulo_emails_detalhes, R.id.container_emails_detalhe);
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

    private void limpaLayout(){
        llContato.removeAllViews();
        llEmail.removeAllViews();
        llEndereco.removeAllViews();
        llTelefone.removeAllViews();
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

    private void apagaView(int titulo, int container){
        findViewById(titulo).setVisibility(View.GONE);
        findViewById(container).setVisibility(View.GONE);
    }

    private void exibeAlertDelete(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning)
                .setTitle(getString(R.string.title_alert_deleta_cliente))
                .setMessage(getString(R.string.texto_alert_deleta_cliente))
                .setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletaCliente();
                    }
                })
                .setNegativeButton(getString(R.string.nao), null)
                .show();
    }

    private void deletaCliente(){
        databaseHelper.deletaTodosContatosCliente(cliente);
        databaseHelper.deletaTodosEmailsCliente(cliente);
        databaseHelper.deletaTodosEnderecosCliente(cliente);
        databaseHelper.deletaTodosTelefonesCliente(cliente);
        databaseHelper.deletaCliente(cliente);

        ToastOX.ok(this, getString(R.string.cliente_deletado_sucesso), Toast.LENGTH_LONG);
        finish();
    }
}
