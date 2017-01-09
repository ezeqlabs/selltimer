package br.com.ezeqlabs.selltimer.helpers;

import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    private EditText primeiroEndereco, primeiroTelefone, primeiroEmail;
    private CadastroClientesActivity activity;

    public ClienteHelper(CadastroClientesActivity activity, List<EditText> listaEnderecos, List<EditText> listaTelefones, List<EditText> listaEmails){
        this.activity = activity;

        nome = (EditText) activity.findViewById(R.id.nome_cliente);

        enderecos = listaEnderecos;
        primeiroEndereco = (EditText) activity.findViewById(R.id.endereco_cliente);
        enderecos.add( primeiroEndereco );

        telefones = listaTelefones;
        primeiroTelefone = (EditText) activity.findViewById(R.id.telefone_cliente);
        telefones.add( primeiroTelefone );

        emails = listaEmails;
        primeiroEmail = (EditText) activity.findViewById(R.id.email_cliente);
        emails.add( primeiroEmail );

        cliente = new Cliente();
    }

    public Cliente pegaClienteDoFormulario(){
        cliente.setNome(nome.getText().toString());
        cliente.setEnderecos(listaEnderecos());
        cliente.setTelefones(listaTelefones());
        cliente.setEmails(listaEmails());

        return cliente;
    }

    public EditText getNome() {
        return nome;
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

    public void colocaClienteNoFormulario(Cliente cliente, LinearLayout llEmail, LinearLayout llEndereco, LinearLayout llTelefone){
        nome.setText(cliente.getNome());
        colocaEmailNoFormulario(cliente, llEmail);
        colocaEnderecoNoFormulario(cliente, llEndereco);
        colocaTelefoneNoFormulario(cliente, llTelefone);
    }

    private void colocaEmailNoFormulario(Cliente cliente, LinearLayout llEmail){
        List<Email> emailList = cliente.getEmails();

        if( emailList != null ){
            if( !emailList.isEmpty() ){
                for(Email tmp : emailList){
                    if( primeiroEmail.getText().toString().equalsIgnoreCase("") ){
                        primeiroEmail.setText( tmp.getEmail() );
                    }else{
                        EditText editText = geraEditText( tmp.getEmail() , R.layout.edittext_email);
                        llEmail.addView(editText);
                        emails.add(editText);
                    }
                }
            }
        }
    }

    private void colocaEnderecoNoFormulario(Cliente cliente, LinearLayout llEndereco){
        List<Endereco> enderecoList = cliente.getEnderecos();
        if( enderecoList != null ){
            if( !enderecoList.isEmpty() ){
                for(Endereco tmp : enderecoList){
                    if(primeiroEndereco.getText().toString().equalsIgnoreCase("")){
                        primeiroEndereco.setText( tmp.getEndereco() );
                    }else{
                        EditText editText = geraEditText( tmp.getEndereco() , R.layout.edittext_endereco);
                        llEndereco.addView(editText);
                        enderecos.add(editText);
                    }
                }
            }
        }
    }

    private void colocaTelefoneNoFormulario(Cliente cliente, LinearLayout llTelefone){
        List<Telefone> telefoneList = cliente.getTelefones();
        if( telefoneList != null ){
            if( !telefoneList.isEmpty() ){
                for(Telefone tmp : telefoneList){
                    if(primeiroTelefone.getText().toString().equalsIgnoreCase("")){
                        primeiroTelefone.setText( tmp.getTelefone() );
                    }else{
                        EditText editText = geraEditText( tmp.getTelefone() , R.layout.edittext_telefone);
                        llTelefone.addView(editText);
                        telefones.add(editText);
                    }
                }
            }
        }
    }

    private EditText geraEditText(String text, int layout){
        EditText editText = (EditText) LayoutInflater.from(activity).inflate(layout, null);
        editText.setText(text);
        return editText;
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

    public boolean nomeValido(){
        if (cliente.getNome().equals("")) {
            nome.setError(activity.getString(R.string.erro_nome));
            return false;
        }

        if( cliente.getNome().length() < 3 ){
            nome.setError(activity.getString(R.string.erro_nome_invalido));
            return false;
        }

        return true;
    }

    public boolean emailValido(){
        for( EditText tmp : emails ){
            tmp.setError(null);

            String valor = tmp.getText().toString();
            if( valor.length() > 0 ){
                if( !(valor.matches(".+@.+\\.[a-z]+")) ){
                    tmp.setError(activity.getString(R.string.erro_email_invalido));
                    return false;
                }
            }
        }
        return true;
    }

    public boolean telefoneValido(){
        for( EditText tmp : telefones ){
            tmp.setError(null);

            String valor = tmp.getText().toString();
            if( valor.length() > 0 ){
                if( !(valor.length() >= 14 && valor.length() < 16) ){
                    tmp.setError(activity.getString(R.string.erro_telefone_invalido));
                    return false;
                }
            }
        }
        return true;
    }

    public boolean enderecoValido(){
        for( EditText tmp : enderecos ){
            tmp.setError(null);

            String valor = tmp.getText().toString();
            if( valor.length() > 0 ){
                if( valor.length() < 5){
                    tmp.setError(activity.getString(R.string.erro_endereco_invalido));
                    return false;
                }
            }
        }
        return true;
    }
}
