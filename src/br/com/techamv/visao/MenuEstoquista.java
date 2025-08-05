package br.com.techamv.visao;

import br.com.techamv.modelo.Fornecedor;
import br.com.techamv.modelo.Produto;
import br.com.techamv.modelo.usuario.Estoquista;
import br.com.techamv.repositorio.BancoDeDados;
import br.com.techamv.servico.EstoqueServico;
import java.util.List;
import java.util.Scanner;

public class MenuEstoquista {
    private Estoquista estoquista;
    private Scanner scanner;
    private EstoqueServico estoqueServico;

    public MenuEstoquista(Estoquista estoquista, Scanner scanner) {
        this.estoquista = estoquista;
        this.scanner = scanner;
        this.estoqueServico = new EstoqueServico();
    }

    public void exibir() {
        int opcao = 0;
        while (opcao != 9) {
            verificarAlertas();
            System.out.println("\n--- MENU ESTOQUISTA ---");
            System.out.println("1. Registrar Entrada de Mercadoria");
            System.out.println("2. Ajustar Inventário");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    registrarEntrada();
                    break;
                case 2:
                    ajustarInventario();
                    break;
                case 9:
                    System.out.println("Fazendo logout...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void verificarAlertas() {
        List<Produto> produtosBaixoEstoque = estoqueServico.listarProdutosComEstoqueBaixo();
        if (!produtosBaixoEstoque.isEmpty()) {
            System.out.println("\n!!! ALERTA DE ESTOQUE BAIXO !!!");
            for (Produto p : produtosBaixoEstoque) {
                System.out.println("- " + p.getNome() + " | Estoque atual: " + p.getEstoque().getQuantidade() + " | Mínimo: " + p.getEstoqueMinimo());
            }
        }
    }

    private void registrarEntrada() {
        System.out.println("\n--- REGISTRO DE ENTRADA ---");
        Produto produto = selecionarProduto();
        if (produto == null) return;

        Fornecedor fornecedor = selecionarFornecedor();
        if (fornecedor == null) return;

        System.out.print("Digite a quantidade de entrada: ");
        int quantidade = lerOpcao();
        
        if (quantidade > 0) {
            estoqueServico.registrarEntrada(produto, quantidade, fornecedor, estoquista);
        } else {
            System.out.println("Quantidade inválida.");
        }
    }
    
    private void ajustarInventario() {
        System.out.println("\n--- AJUSTE DE INVENTÁRIO ---");
        Produto produto = selecionarProduto();
        if (produto == null) return;
        
        System.out.println("Estoque atual de " + produto.getNome() + ": " + produto.getEstoque().getQuantidade());
        System.out.print("Digite a nova quantidade correta: ");
        int novaQuantidade = lerOpcao();
        
        if (novaQuantidade >= 0) {
            System.out.print("Digite a justificativa para o ajuste: ");
            String justificativa = scanner.nextLine();
            estoqueServico.ajustarEstoque(produto, novaQuantidade, justificativa);
        } else {
            System.out.println("Quantidade inválida.");
        }
    }

    private Produto selecionarProduto() {
        System.out.println("Produtos disponíveis:");
        BancoDeDados.produtos.forEach(p -> System.out.println(p.getIdProduto() + " - " + p.getNome()));
        System.out.print("Selecione o ID do produto: ");
        int idProduto = lerOpcao();
        return BancoDeDados.produtos.stream().filter(p -> p.getIdProduto() == idProduto).findFirst().orElse(null);
    }
    
    private Fornecedor selecionarFornecedor() {
        System.out.println("Fornecedores disponíveis:");
        BancoDeDados.fornecedores.forEach(f -> System.out.println(f.getIdFornecedor() + " - " + f.getNome()));
        System.out.print("Selecione o ID do fornecedor: ");
        int idFornecedor = lerOpcao();
        return BancoDeDados.fornecedores.stream().filter(f -> f.getIdFornecedor() == idFornecedor).findFirst().orElse(null);
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}