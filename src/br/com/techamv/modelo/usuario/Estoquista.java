package br.com.techamv.modelo.usuario;

public class Estoquista extends Usuario {
    public Estoquista(String nome, String email, String senha) {
        super(nome, email,);
    }

    @Override
    public String getTipo() {
        return "Estoquista";
    }
}