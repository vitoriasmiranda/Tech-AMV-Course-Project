package br.com.techamv.modelo;

public class Fornecedor {
    private int idFornecedor;
    private String nome;
    private String cnpj;
    private static int proximoId = 1;

    public Fornecedor(String nome, String cnpj) {
        this.idFornecedor = proximoId++;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}