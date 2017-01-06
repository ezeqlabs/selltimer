package br.com.ezeqlabs.selltimer.fragment;

import android.app.Activity;
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

import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.adapter.DashboardAdapter;
import br.com.ezeqlabs.selltimer.helpers.PairHelper;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;
import br.com.ezeqlabs.selltimer.utils.Redirecionamentos;

public class DashboardFragment  extends Fragment {
    private TextView titulo, mensagem;
    private ListView listView;
    private Activity activity;
    private int corBgTitulo, corBgListView, txtTitulo, txtSemContatos;
    List<PairHelper> listaPares;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_clientes, container, false);

        preparaVariaveis(v);
        preparaTitulo();
        preparaExibicao();

        return v;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setListaPares(List<PairHelper> listaPares) {
        this.listaPares = listaPares;
    }

    public void setTxtSemContatos(int txtSemContatos) {
        this.txtSemContatos = txtSemContatos;
    }

    public void setTxtTitulo(int txtTitulo) {
        this.txtTitulo = txtTitulo;
    }

    public void setCorBgListView(int corBgListView) {
        this.corBgListView = corBgListView;
    }

    public void setCorBgTitulo(int corBgTitulo) {
        this.corBgTitulo = corBgTitulo;
    }

    private void preparaVariaveis(View v){
        titulo = (TextView) v.findViewById(R.id.titulo_cliente_dashboard);
        mensagem = (TextView) v.findViewById(R.id.mensagem_cliente_dashboard);
        listView = (ListView) v.findViewById(R.id.listview_dashboard);
    }

    private void preparaTitulo(){
        titulo.setText(getString(txtTitulo));
        titulo.setBackgroundColor(getResources().getColor(corBgTitulo));
        titulo.setTextColor(getResources().getColor(R.color.branco));
    }

    private void preparaExibicao(){
        if(listaPares != null) {
            if (listaPares.size() > 0) {
                preparaListView();
            } else {
                preparaMensagem();
            }
        }else {
            preparaMensagem();
        }
    }

    private void preparaMensagem(){
        mensagem.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        mensagem.setText(txtSemContatos);
    }

    private void preparaListView(){
        mensagem.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        listView.setBackgroundColor(getResources().getColor(corBgListView));

        listView.setAdapter( new DashboardAdapter(activity, listaPares) );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contato contato = listaPares.get(i).getContato();
                Cliente cliente = listaPares.get(i).getCliente();

                Redirecionamentos.redireciona_para_cliente(activity, cliente, contato, false);
            }
        });
    }
}
