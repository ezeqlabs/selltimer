package br.com.ezeqlabs.selltimer.helpers;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.CadastroClientesActivity;
import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Endereco;

public class ClienteHelper {
    private EditText nome;
    private List<EditText> enderecos;
    private Cliente cliente;

    public ClienteHelper(CadastroClientesActivity activity, List<EditText> listaEnderecos){
        nome = (EditText) activity.findViewById(R.id.nome_cliente);

        enderecos = listaEnderecos;
        enderecos.add( (EditText) activity.findViewById(R.id.endereco_cliente) );

        cliente = new Cliente();
    }

    public Cliente pegaClienteDoFormulario(){
        cliente.setNome(nome.getText().toString());
        cliente.setEnderecos(listaEnderecos());

        return cliente;
    }

    private List<Endereco> listaEnderecos(){
        List<Endereco> listEnderecos = new ArrayList<>();

        for(EditText editTextEndereco : enderecos){
            Endereco endereco = new Endereco();
            endereco.setEndereco(editTextEndereco.getText().toString());
            listEnderecos.add(endereco);
        }

        return listEnderecos;
    }
}
