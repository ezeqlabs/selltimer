package br.com.ezeqlabs.selltimer.model;

import java.io.Serializable;

public class Contato implements Serializable {
    private Long id;
    private String data;
    private String retorno;
    private String anotacoes;
    private String interesse;
    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public String getDataParaBanco(){
        String[] partes = data.split("/");
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }

    public void setData(String data){
        this.data = data;
    }

    public void setDataDoBanco(String data){
        String[] partes = data.split("-");
        this.data = partes[2] + "/" + partes[1] + "/" + partes[0];
    }

    public String getRetornoParaBanco(){
        String[] partes = retorno.split("/");
        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public void setRetornoDoBanco(String data){
        if(data != null && !data.equals("")){
            String[] partes = data.split("-");
            this.retorno = partes[2] + "/" + partes[1] + "/" + partes[0];
        }
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public String getInteresse() {
        return interesse;
    }

    public void setInteresse(String interesse) {
        this.interesse = interesse;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return data + " - " + interesse;
    }
}
