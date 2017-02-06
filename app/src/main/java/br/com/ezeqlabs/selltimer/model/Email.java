package br.com.ezeqlabs.selltimer.model;

import java.io.Serializable;

public class Email implements Serializable{
    private Long id;
    private String email;
    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
