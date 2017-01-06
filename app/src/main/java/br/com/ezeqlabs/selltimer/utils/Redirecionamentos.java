package br.com.ezeqlabs.selltimer.utils;

import android.app.Activity;
import android.content.Intent;

import br.com.ezeqlabs.selltimer.DetalheClienteActivity;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;

public class Redirecionamentos {

    public static void redireciona_para_cliente(Activity activity, Cliente cliente, Contato contato, boolean finish){
        Intent detalhe = new Intent(activity, DetalheClienteActivity.class);
        detalhe.putExtra(Constantes.CLIENTE_INTENT, cliente);
        detalhe.putExtra(Constantes.CONTATO_INTENT, contato);
        activity.startActivity(detalhe);
        if(finish){
            activity.finish();
        }
    }
}
