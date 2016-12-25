package br.com.ezeqlabs.selltimer.helpers;

import android.widget.EditText;

import br.com.ezeqlabs.selltimer.CadastroClientesActivity;
import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.model.Cliente;

public class ClienteHelper {
    private EditText nome;
    private Cliente cliente;

    public ClienteHelper(CadastroClientesActivity activity){
        nome = (EditText) activity.findViewById(R.id.nome_cliente);
        cliente = new Cliente();
    }

    public Cliente pegaClienteDoFormulario(){
        cliente.setNome(nome.getText().toString());

        return cliente;
    }
}
