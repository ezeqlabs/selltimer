package br.com.ezeqlabs.selltimer.helpers;

import br.com.ezeqlabs.selltimer.model.Cliente;
import br.com.ezeqlabs.selltimer.model.Contato;

public class PairHelper {
    private Cliente cliente;
    private Contato contato;

    public PairHelper(Cliente cliente, Contato contato){
        this.cliente = cliente;
        this.contato = contato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Contato getContato() {
        return contato;
    }
}
