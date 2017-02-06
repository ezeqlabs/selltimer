package br.com.ezeqlabs.selltimer.model;

import java.io.Serializable;

public class Endereco implements Serializable {
    private Long id;
    private String endereco;
    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}