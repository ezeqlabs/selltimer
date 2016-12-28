package br.com.ezeqlabs.selltimer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import br.com.ezeqlabs.selltimer.R;

public class ClientesSemanaFragment extends Fragment {
    private TextView titulo;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_clientes, container, false);

        titulo = (TextView) v.findViewById(R.id.titulo_cliente_dashboard);
        titulo.setText(getString(R.string.contatos_semana));
        titulo.setBackgroundColor(getResources().getColor(R.color.amareloGoldenrod));
        titulo.setTextColor(getResources().getColor(R.color.branco));

        listView = (ListView) v.findViewById(R.id.listview_dashboard);
        listView.setBackgroundColor(getResources().getColor(R.color.amareloLightYellow));

        return v;
    }
}
