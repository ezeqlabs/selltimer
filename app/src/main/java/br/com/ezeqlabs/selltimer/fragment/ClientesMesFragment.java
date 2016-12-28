package br.com.ezeqlabs.selltimer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.ezeqlabs.selltimer.DetalheClienteActivity;
import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.adapter.DashboardAdapter;
import br.com.ezeqlabs.selltimer.database.DatabaseHelper;
import br.com.ezeqlabs.selltimer.helpers.PairHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.utils.Constantes;

public class ClientesMesFragment extends Fragment {
    private TextView titulo;
    private ListView listView;
    private Context context;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_clientes, container, false);

        titulo = (TextView) v.findViewById(R.id.titulo_cliente_dashboard);
        titulo.setText(getString(R.string.contatos_mes));
        titulo.setBackgroundColor(getResources().getColor(R.color.azulSteelBlue));
        titulo.setTextColor(getResources().getColor(R.color.branco));

        listView = (ListView) v.findViewById(R.id.listview_dashboard);
        listView.setBackgroundColor(getResources().getColor(R.color.azulLightBlue));

        final List<PairHelper> listaPares = databaseHelper.getClientesMes();

        listView.setAdapter( new DashboardAdapter(context, listaPares) );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contato contato = listaPares.get(i).getContato();
                Cliente cliente = listaPares.get(i).getCliente();

                Intent detalhe = new Intent(context, DetalheClienteActivity.class);
                detalhe.putExtra(Constantes.CLIENTE_INTENT, cliente);
                detalhe.putExtra(Constantes.CONTATO_INTENT, contato);
                startActivity(detalhe);
            }
        });

        return v;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
