package br.com.ezeqlabs.selltimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DetalheClienteActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listview_contatos);

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
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
