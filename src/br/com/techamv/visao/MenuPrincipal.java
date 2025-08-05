package br.com.techamv.visao;

import br.com.techamv.modelo.usuario.*;
import br.com.techamv.servico.AutenticacaoServico;
import java.util.Optional;
import java.util.Scanner;

public class MenuPrincipal {

    private Scanner scanner;
    private AutenticacaoServico autenticacaoServico;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.autenticacaoServico = new AutenticacaoServico();
    }

    public void exibir() {
        while (true) {
            System.out.println("\n===== BEM-VINDO AO SISTEMA TECHAMV =====");
            System.out.println("Faça o login para continuar ou digite 'sair' no email para fechar o sistema.");
            System.out.print("Email: ");
            String email = scanner.nextLine();

            if (email.equalsIgnoreCase("sair")) {
                break;
            }

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            Optional<Usuario> usuarioAutenticado = autenticacaoServico.fazerLogin(email, senha);

            if (usuarioAutenticado.isPresent()) {
                Usuario usuario = usuarioAutenticado.get();
                System.out.println("\nLogin bem-sucedido! Bem-vindo(a), " + usuario.getNome() + " (" + usuario.getTipo() + ")");
                direcionarParaMenu(usuario);
            } else {
                System.out.println("Email, senha ou status do usuário inválidos. Tente novamente.");
            }
        }
        System.out.println("Sistema finalizado.");
        scanner.close();
    }

    private void direcionarParaMenu(Usuario usuario) {
        if (usuario instanceof Gerente) {
            new MenuGerente((Gerente) usuario, scanner).exibir();
        } else if (usuario instanceof Vendedor) {
            new MenuVendedor((Vendedor) usuario, scanner).exibir();
        } else if (usuario instanceof Estoquista) {
            new MenuEstoquista((Estoquista) usuario, scanner).exibir();
        }
    }
}