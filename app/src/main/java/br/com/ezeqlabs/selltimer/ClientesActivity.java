package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.dao.ClienteDAO;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class ClientesActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listview_clientes);
        ClienteDAO dao = new ClienteDAO(this);


        List<Cliente> clientes = dao.getClientes();

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_clientes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastro = new Intent(ClientesActivity.this, CadastroClientesActivity.class);
                startActivity(cadastro);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
