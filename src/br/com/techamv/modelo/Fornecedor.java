package br.com.techamv.modelo;

public class Fornecedor {
    private static int proximoId = 1;
    private int idFornecedor;
    private String nome;
    private String cnpj;

    public Fornecedor(String nome, String cnpj) {
        this.idFornecedor = proximoId++;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public int getIdFornecedor() { return idFornecedor; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}