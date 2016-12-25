package br.com.ezeqlabs.selltimer.helpers;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.ezeqlabs.selltimer.CadastroClientesActivity;
import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Email;
import br.com.ezeqlabs.selltimer.model.Endereco;
import br.com.ezeqlabs.selltimer.model.Telefone;

public class ClienteHelper {
    private EditText nome;
    private List<EditText> enderecos, telefones, emails;
    private Cliente cliente;

    public ClienteHelper(CadastroClientesActivity activity, List<EditText> listaEnderecos, List<EditText> listaTelefones, List<EditText> listaEmails){
        nome = (EditText) activity.findViewById(R.id.nome_cliente);

        enderecos = listaEnderecos;
        enderecos.add( (EditText) activity.findViewById(R.id.endereco_cliente) );

        telefones = listaTelefones;
        telefones.add( (EditText) activity.findViewById(R.id.telefone_cliente) );

        emails = listaEmails;
        emails.add( (EditText) activity.findViewById(R.id.email_cliente) );

        cliente = new Cliente();
    }

    public Cliente pegaClienteDoFormulario(){
        cliente.setNome(nome.getText().toString());
        cliente.setEnderecos(listaEnderecos());
        cliente.setTelefones(listaTelefones());
        cliente.setEmails(listaEmails());

        return cliente;
    }

    private List<Endereco> listaEnderecos(){
        List<Endereco> listEnderecos = new ArrayList<>();

        for(EditText editTextEndereco : enderecos){
            String texto = editTextEndereco.getText().toString();

            if(!texto.equalsIgnoreCase("")){
                Endereco endereco = new Endereco();
                endereco.setEndereco(texto);
                listEnderecos.add(endereco);
            }
        }

        return listEnderecos;
    }

    private List<Telefone> listaTelefones(){
        List<Telefone> listTelefones = new ArrayList<>();

        for(EditText editTextTelefone : telefones){
            String texto = editTextTelefone.getText().toString();

            if(!texto.equalsIgnoreCase("")){
                Telefone telefone = new Telefone();
                telefone.setTelefone(texto);
                listTelefones.add(telefone);
            }
        }

        return listTelefones;
    }

    private List<Email> listaEmails(){
        List<Email> listEmail = new ArrayList<>();

        for(EditText editTextEmail : emails){
            String texto = editTextEmail.getText().toString();

            if(!texto.equalsIgnoreCase("")){
                Email email = new Email();
                email.setEmail(texto);
                listEmail.add(email);
            }
        }

        return listEmail;
    }
}
