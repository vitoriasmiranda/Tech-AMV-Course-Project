package br.com.techamv.visao;

import br.com.techamv.modelo.usuario.Gerente;
import br.com.techamv.modelo.usuario.Usuario;
import br.com.techamv.servico.RelatorioServico;
import br.com.techamv.servico.UsuarioServico;
import java.util.Optional;
import java.util.Scanner;

public class MenuGerente {
    private Gerente gerente;
    private Scanner scanner;
    private RelatorioServico relatorioServico;
    private UsuarioServico usuarioServico;

    public MenuGerente(Gerente gerente, Scanner scanner) {
        this.gerente = gerente;
        this.scanner = scanner;
        this.relatorioServico = new RelatorioServico();
        this.usuarioServico = new UsuarioServico();
    }
    
    public void exibir() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n--- MENU GERENTE ---");
            System.out.println("1. Gestão de Usuários");
            System.out.println("2. Análise de Vendas");
            System.out.println("3. Gerar Relatório de Lucro Total");
            System.out.println("4. Gerar Relatório de Produtos Mais Vendidos");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuGestaoUsuarios();
                    break;
                case 2:
                    relatorioServico.gerarAnaliseDeVendas();
                    break;
                case 3:
                    relatorioServico.gerarRelatorioLucro();
                    break;
                case 4:
                    relatorioServico.gerarRelatorioProdutosMaisVendidos();
                    break;
                case 9:
                    System.out.println("Fazendo logout...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void menuGestaoUsuarios() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n-- Gestão de Usuários --");
            System.out.println("1. Listar todos os usuários");
            System.out.println("2. Criar novo usuário");
            System.out.println("3. Desativar um usuário");
            System.out.println("9. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    usuarioServico.listarUsuarios();
                    break;
                case 2:
                    criarUsuario();
                    break;
                case 3:
                    desativarUsuario();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    
    private void criarUsuario() {
        System.out.println("\n-- Criar Novo Usuário --");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Tipo (Gerente, Vendedor, Estoquista): ");
        String tipo = scanner.nextLine();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || tipo.isEmpty()) {
            System.out.println("Todos os campos são obrigatórios.");
            return;
        }
        usuarioServico.criarUsuario(nome, email, senha, tipo);
    }
    
    private void desativarUsuario() {
        System.out.println("\n-- Desativar Usuário --");
        System.out.print("Digite o ID do usuário que deseja desativar: ");
        int id = lerOpcao();

        Optional<Usuario> usuarioOpt = usuarioServico.encontrarPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.equals(this.gerente)) {
                System.out.println("Não é possível desativar a si mesmo.");
                return;
            }
            usuarioServico.desativarUsuario(usuario);
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}