package br.com.techamv.modelo.usuario;

public class Vendedor extends Usuario {
    public Vendedor(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public String getTipo() {
        return "Vendedor";
    }
}