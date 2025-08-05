package br.com.techamv.servico;

import br.com.techamv.modelo.usuario.*;
import br.com.techamv.repositorio.BancoDeDados;
import java.util.Optional;

public class UsuarioServico {

    public void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        BancoDeDados.usuarios.forEach(u -> 
            System.out.println(
                "ID: " + u.getIdUsuario() + 
                " | Nome: " + u.getNome() + 
                " | Email: " + u.getEmail() +
                " | Tipo: " + u.getTipo() +
                " | Status: " + (u.isAtivo() ? "Ativo" : "Inativo")
            )
        );
    }

    public void criarUsuario(String nome, String email, String senha, String tipo) {
        Usuario novoUsuario;
        switch (tipo.toLowerCase()) {
            case "gerente":
                novoUsuario = new Gerente(nome, email, senha);
                break;
            case "vendedor":
                novoUsuario = new Vendedor(nome, email, senha);
                break;
            case "estoquista":
                novoUsuario = new Estoquista(nome, email, senha);
                break;
            default:
                System.out.println("Tipo de usuário inválido.");
                return;
        }
        BancoDeDados.usuarios.add(novoUsuario);
        System.out.println("Usuário " + nome + " criado com sucesso!");
    }

    public Optional<Usuario> encontrarPorId(int id) {
        return BancoDeDados.usuarios.stream().filter(u -> u.getIdUsuario() == id).findFirst();
    }
    
    public void desativarUsuario(Usuario usuario) {
        usuario.setAtivo(false);
        System.out.println("Usuário " + usuario.getNome() + " desativado com sucesso.");
    }
}