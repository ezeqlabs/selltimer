package br.com.ezeqlabs.selltimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class DetalheContatoActivity extends AppCompatActivity {
    private Cliente cliente;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cliente = (Cliente) getIntent().getSerializableExtra(Constantes.CLIENTE_INTENT);
        contato = (Contato) getIntent().getSerializableExtra(Constantes.CONTATO_INTENT);

        TextView tituloCliente = (TextView) findViewById(R.id.titulo_cliente_contato);
        TextView dataInteresse = (TextView) findViewById(R.id.data_interesse_contato);
        TextView anotacoesContato = (TextView) findViewById(R.id.anotacoes_contato);

        String textoDataInteresse = contato.getData() + " - " + contato.getInteresse();

        tituloCliente.setText(cliente.getNome());
        dataInteresse.setText(textoDataInteresse);
        anotacoesContato.setText(contato.getAnotacoes());

        AdView mAdView = (AdView) findViewById(R.id.adViewContato);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
