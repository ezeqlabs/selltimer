package br.com.ezeqlabs.selltimer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.helpers.PairHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;

public class DashboardAdapter extends BaseAdapter {
    private List<PairHelper> listaPares;
    private Context context;

    public DashboardAdapter(Context context, List<PairHelper> listaPares){
        this.context = context;
        this.listaPares = listaPares;
    }

    @Override
    public int getCount() {
        return listaPares.size();
    }

    @Override
    public Object getItem(int i) {
        return listaPares.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Cliente cliente = listaPares.get(i).getCliente();
        Contato contato = listaPares.get(i).getContato();

        String titulo = cliente.getNome() + " - " + contato.getData() + ": " + contato.getInteresse();

        View v = LayoutInflater.from(context).inflate(R.layout.item_dashboard, null);

        TextView tituloItem = (TextView) v.findViewById(R.id.titulo_item_dashboard);
        TextView textoItem = (TextView) v.findViewById(R.id.texto_item_dashboard);

        tituloItem.setText(titulo);

        if( !contato.getAnotacoes().equalsIgnoreCase("") ){
            textoItem.setText( contato.getAnotacoes() );
        }else{
            textoItem.setVisibility(View.GONE);
        }


        return v;
    }
}
