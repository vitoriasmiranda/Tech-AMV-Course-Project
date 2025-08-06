package br.com.techamv.modelo;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cpf;
    private static int proximoId = 1;

    public Cliente(String nome, String cpf) {
        this.idCliente = proximoId++;
        this.nome = nome;
        this.cpf = cpf;
    }

    public int getIdCliente() { return idCliente; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}