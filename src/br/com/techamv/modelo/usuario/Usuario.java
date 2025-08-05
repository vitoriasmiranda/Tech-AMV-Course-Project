package br.com.techamv.modelo.usuario;

public abstract class Usuario {
    private static int proximoId = 1;
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;

    public Usuario(String nome, String email, String senha) {
        this.idUsuario = proximoId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = true;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    
    public abstract String getTipo();
}