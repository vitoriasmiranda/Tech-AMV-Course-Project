package br.com.techamv.visao;

import br.com.techamv.modelo.usuario.Gerente;
import br.com.techamv.servico.RelatorioServico;
import java.util.Scanner;

public class MenuGerente {
    private Gerente gerente;
    private Scanner scanner;
    private RelatorioServico relatorioServico;

    public MenuGerente(Gerente gerente, Scanner scanner) {
        this.gerente = gerente;
        this.scanner = scanner;
        this.relatorioServico = new RelatorioServico();
    }
    
    public void exibir() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n--- MENU GERENTE ---");
            System.out.println("1. Gerar Relatório de Lucro Total");
            System.out.println("2. Gerar Relatório de Produtos Mais Vendidos");
            System.out.println("3. Gestão de Usuários (Não implementado)");
            System.out.println("4. Análise de Vendas (Não implementado)");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    relatorioServico.gerarRelatorioLucro();
                    break;
                case 2:
                    relatorioServico.gerarRelatorioProdutosMaisVendidos();
                    break;
                case 3:
                    System.out.println("Funcionalidade de gestão de usuários ainda não implementada.");
                    break;
                case 4:
                    System.out.println("Funcionalidade de análise de vendas ainda não implementada.");
                    break;
                case 9:
                    System.out.println("Fazendo logout...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
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