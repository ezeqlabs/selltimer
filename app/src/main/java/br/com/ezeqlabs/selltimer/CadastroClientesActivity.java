package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.onurciner.toastox.ToastOX;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.helpers.ClienteHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.model.Telefone;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class CadastroClientesActivity extends AppCompatActivity {
    private LinearLayout llEnderecos, llTelefones, llEmails;
    private List<EditText> listaEnderecos = new ArrayList<>();
    private List<EditText> listaTelefones = new ArrayList<>();
    private List<EditText> listaEmails = new ArrayList<>();
    private ClienteHelper helper;
    private DatabaseHelper databaseHelper;
    private Cliente cliente;
    private Long clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_clientes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preparaVariaveis();
        preparaFormulario();
    }

    private void preparaVariaveis(){
        llEnderecos = (LinearLayout) findViewById(R.id.container_enderecos);
        llTelefones = (LinearLayout) findViewById(R.id.container_telefones);
        llEmails = (LinearLayout) findViewById(R.id.container_emails);
        cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);

        helper = new ClienteHelper(CadastroClientesActivity.this, listaEnderecos, listaTelefones, listaEmails);
        databaseHelper = new DatabaseHelper(this);

        if( cliente != null ){
            clienteId = cliente.getId();
        }
    }

    private void preparaFormulario(){
        if( cliente != null ){
            helper.colocaClienteNoFormulario(cliente, llEmails, llEnderecos, llTelefones);
        }
    }

    public void novoEndereco(View v){
        EditText editText = geraEditText(R.string.label_endereco, R.layout.edittext_endereco);
        llEnderecos.addView(editText);
        listaEnderecos.add(editText);
    }

    public void novoTelefone(View v){
        EditText editText = geraEditText(R.string.label_telefone, R.layout.edittext_telefone);
        llTelefones.addView(editText);
        listaTelefones.add(editText);
    }

    public void novoEmail(View v){
        EditText editText = geraEditText(R.string.label_email, R.layout.edittext_email);
        llEmails.addView(editText);
        listaEmails.add(editText);
    }

    private EditText geraEditText(int hint, int layout){
        EditText editText = (EditText) LayoutInflater.from(this).inflate(layout, null);
        editText.setHint(hint);
        return editText;
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
                salvaOuEditaCliente();
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void salvaOuEditaCliente(){
        if( cliente == null ){
            salvarClienteCompleto();
        }else{
            atualizaClienteCompleto();
        }
    }

    private void salvarClienteCompleto(){
        cliente = helper.pegaClienteDoFormulario();

        if(clienteValido()){
            clienteId = databaseHelper.insereCliente(cliente);

            salvaBlocosCliente();
            redirecionaClienteSalvo(R.string.cliente_salvo_sucesso);
        }else{
            cliente = null;
        }
    }

    private void atualizaClienteCompleto(){
        Cliente tmp = helper.pegaClienteDoFormulario();

        cliente.setNome( tmp.getNome() );
        cliente.setEmails( tmp.getEmails() );
        cliente.setEnderecos( tmp.getEnderecos() );
        cliente.setTelefones( tmp.getTelefones() );

        if(clienteValido()){
            databaseHelper.atualizaCliente(cliente);

            atualizaTelefones(cliente);
            atualizaEnderecos(cliente);
            atualizaEmails(cliente);

            databaseHelper.close();

            redirecionaClienteSalvo(R.string.cliente_atualizado_sucesso);
        }
    }

    private void atualizaTelefones(Cliente cliente){
        databaseHelper.deletaTodosTelefonesCliente(cliente);
        salvarTelefones(cliente.getTelefones(), clienteId);
    }

    private void atualizaEnderecos(Cliente cliente){
        databaseHelper.deletaTodosEnderecosCliente(cliente);
        salvarEndereco(cliente.getEnderecos(), clienteId);
    }

    private void atualizaEmails(Cliente cliente){
        databaseHelper.deletaTodosEmailsCliente(cliente);
        salvarEmails(cliente.getEmails(), clienteId);
    }

    private boolean clienteValido(){
        boolean valido = true;

        if(!helper.nomeValido()){
            valido = false;
        }

        if( !helper.enderecoValido() ){
            valido = false;
        }

        if( !helper.telefoneValido() ){
            valido = false;
        }

        if( !helper.emailValido() ){
            valido = false;
        }

        return valido;
    }

    private void salvaBlocosCliente(){
        salvarEndereco(cliente.getEnderecos(), clienteId);
        salvarTelefones(cliente.getTelefones(), clienteId);
        salvarEmails(cliente.getEmails(), clienteId);

        databaseHelper.close();
    }

    private void redirecionaClienteSalvo(int mensagem){
        cliente.setId(clienteId);

        ToastOX.ok(this, getString(mensagem), Toast.LENGTH_LONG);

        Intent detalhe = new Intent(this, DetalheClienteActivity.class);
        detalhe.putExtra(Constantes.CLIENTE_INTENT, cliente);
        startActivity(detalhe);
    }

    private void salvarEndereco(List<Endereco> enderecos, Long clienteId){
        for(Endereco endereco : enderecos){
            databaseHelper.insereEndereco(endereco, clienteId);
        }
    }

    private void salvarTelefones(List<Telefone> telefones, Long clienteId){
        for(Telefone telefone : telefones){
            databaseHelper.insereTelefone(telefone, clienteId);
        }
    }

    private void salvarEmails(List<Email> emails, Long clienteId){
        for(Email email : emails){
            databaseHelper.insereEmail(email, clienteId);
        }
    }
}