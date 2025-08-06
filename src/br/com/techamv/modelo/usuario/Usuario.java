package br.com.techamv.modelo.usuario;

public abstract class Usuario {
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;
    private static int proximoId = 1;

    public Usuario(String nome, String email, String senha) {
        this.idUsuario = proximoId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = true;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public boolean isAtivo() { return ativo; }
    
    public void setNome(String nome) { this.nome = nome; }
    
    public void setEmail(String email) { this.email = email; }
    
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public abstract String getTipo();
}