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

        List<String> clientes = new ArrayList<>();

        clientes.add("Cliente 01");
        clientes.add("Cliente 02");
        clientes.add("Cliente 03");
        clientes.add("Cliente 04");
        clientes.add("Cliente 05");
        clientes.add("Cliente 06");
        clientes.add("Cliente 07");
        clientes.add("Cliente 08");
        clientes.add("Cliente 09");
        clientes.add("Cliente 10");
        clientes.add("Cliente 11");
        clientes.add("Cliente 12");
        clientes.add("Cliente 13");
        clientes.add("Cliente 14");
        clientes.add("Cliente 15");
        clientes.add("Cliente 16");
        clientes.add("Cliente 17");
        clientes.add("Cliente 18");
        clientes.add("Cliente 19");
        clientes.add("Cliente 20");
        clientes.add("Cliente 21");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clientes);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detalhe = new Intent(ClientesActivity.this, DetalheClienteActivity.class);
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
