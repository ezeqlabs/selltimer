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
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class DetalheClienteActivity extends AppCompatActivity {
    private ListView listView;
    private LinearLayout llEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_cliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listview_contatos);
        llEndereco = (LinearLayout) findViewById(R.id.container_enderecos_detalhe);

        Cliente cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);
        List<Endereco> enderecos = cliente.getEnderecos();

        TextView nomeCliente = (TextView) findViewById(R.id.nome_cliente_detalhe);
        nomeCliente.setText(cliente.getNome());

        for(Endereco endereco : enderecos){
            geraEndereco(endereco);
        }



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

    public void adicionaContato(View v){
        Intent cadastroContato = new Intent(this, CadastroContatoActivity.class);
        startActivity(cadastroContato);
    }

    private void geraEndereco(Endereco endereco){
        llEndereco.addView(geraTextView(endereco.getEndereco()));
    }

    private TextView geraTextView(String texto){
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(texto);
        return textView;
    }
}
