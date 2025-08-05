package br.com.techamv.modelo;

public class Cliente {
    private static int proximoId = 1;
    private int idCliente;
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        this.idCliente = proximoId++;
        this.nome = nome;
        this.cpf = cpf;
    }

    public int getIdCliente() { return idCliente; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}