package br.com.ezeqlabs.selltimer.helpers;

import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;

import br.com.ezeqlabs.selltimer.CadastroContatoActivity;
import br.com.ezeqlabs.selltimer.R;
import br.com.ezeqlabs.selltimer.model.Contato;

public class ContatoHelper {
    private EditText data, anotacoes;
    private Spinner interesse;
    private Contato contato;

    public ContatoHelper(CadastroContatoActivity activity){
        data = (EditText) activity.findViewById(R.id.data_contato);
        interesse = (Spinner) activity.findViewById(R.id.interesse_contato);
        anotacoes = (EditText) activity.findViewById(R.id.descricao_contato);

        contato = new Contato();
    }

    public Contato pegaContatoDoFormulario() throws ParseException {
        contato.setData(data.getText().toString());
        contato.setInteresse(interesse.getSelectedItem().toString());
        contato.setAnotacoes(anotacoes.getText().toString());

        return contato;
    }
}
