package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class ClientesActivity extends AppCompatActivity {
    private ListView listView;
    private TextView textoMensagem;
    private DatabaseHelper databaseHelper;
    private List<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preparaVariaveis();
        trataFab();
    }

    @Override
    protected void onResume(){
        super.onResume();
        preparaListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Cliente> populaClientesCompleto(){
        List<Cliente> retorno = new ArrayList<>();

        for(Cliente cliente : databaseHelper.getClientes()){
            Long clienteId = cliente.getId();

            cliente.setEnderecos( databaseHelper.getEnderecosCliente(clienteId) );
            cliente.setEmails( databaseHelper.getEmailsCliente(clienteId) );
            cliente.setTelefones( databaseHelper.getTelefonesCliente(clienteId) );

            retorno.add(cliente);
        }

        return retorno;
    }

    private void preparaVariaveis(){
        listView = (ListView) findViewById(R.id.listview_clientes);
        textoMensagem = (TextView) findViewById(R.id.texto_clientes_vazio);
        databaseHelper = new DatabaseHelper(this);
    }

    private void preparaListView(){
        clientes = populaClientesCompleto();

        if(clientes.size() > 0){
            exibeLista();
        }else{
            exibeMensagem();
        }
    }

    private void exibeLista(){
        textoMensagem.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientes);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detalhe = new Intent(ClientesActivity.this, DetalheClienteActivity.class);
                detalhe.putExtra(Constantes.CLIENTE_INTENT, (Cliente) listView.getItemAtPosition(i));
                startActivity(detalhe);
            }
        });
    }

    private void exibeMensagem(){
        listView.setVisibility(View.GONE);
        textoMensagem.setVisibility(View.VISIBLE);
    }

    private void trataFab(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_clientes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastro = new Intent(ClientesActivity.this, CadastroClientesActivity.class);
                startActivity(cadastro);
            }
        });
    }

}
