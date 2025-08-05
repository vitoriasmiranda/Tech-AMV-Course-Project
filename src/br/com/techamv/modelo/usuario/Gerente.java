package br.com.techamv.modelo.usuario;

public class Gerente extends Usuario {
    public Gerente(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public String getTipo() {
        return "Gerente";
    }
}