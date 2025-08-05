package br.com.techamv.servico;

import br.com.techamv.modelo.usuario.Usuario;
import br.com.techamv.repositorio.BancoDeDados;
import java.util.Optional;

public class AutenticacaoServico {

    public Optional<Usuario> fazerLogin(String email, String senha) {
        return BancoDeDados.usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha) && u.isAtivo())
                .findFirst();
    }
}