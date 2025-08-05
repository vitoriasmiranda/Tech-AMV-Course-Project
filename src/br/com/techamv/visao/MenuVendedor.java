package br.com.techamv.visao;

import br.com.techamv.modelo.ItemVenda;
import br.com.techamv.modelo.Produto;
import br.com.techamv.modelo.usuario.Vendedor;
import br.com.techamv.repositorio.BancoDeDados;
import br.com.techamv.servico.RelatorioServico;
import br.com.techamv.servico.VendaServico;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuVendedor {
    private Vendedor vendedor;
    private Scanner scanner;
    private VendaServico vendaServico;
    private RelatorioServico relatorioServico;

    public MenuVendedor(Vendedor vendedor, Scanner scanner) {
        this.vendedor = vendedor;
        this.scanner = scanner;
        this.vendaServico = new VendaServico();
        this.relatorioServico = new RelatorioServico();
    }

    public void exibir() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n--- MENU VENDEDOR ---");
            System.out.println("1. Registrar Nova Venda");
            System.out.println("2. Consultar Estoque");
            System.out.println("3. Gerar Relatório de Desempenho Diário");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    registrarVenda();
                    break;
                case 2:
                    consultarEstoque();
                    break;
                case 3:
                    relatorioServico.gerarRelatorioDesempenhoVendedor(vendedor);
                    break;
                case 9:
                    System.out.println("Fazendo logout...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void consultarEstoque() {
        System.out.println("\n--- CONSULTA DE ESTOQUE ---");
        BancoDeDados.produtos.forEach(p -> 
            System.out.println(p.getIdProduto() + " - " + p.getNome() + " | Estoque: " + p.getEstoque().getQuantidade())
        );
    }
    
    private void registrarVenda() {
        System.out.println("\n--- REGISTRO DE NOVA VENDA ---");
        List<ItemVenda> itensVenda = new ArrayList<>();
        int idProduto = -1;
        while (idProduto != 0) {
            consultarEstoque();
            System.out.print("Digite o ID do produto para adicionar ao carrinho (ou 0 para finalizar): ");
            idProduto = lerOpcao();
            
            if(idProduto == 0) continue;

            int finalIdProduto = idProduto;
            Produto produtoSelecionado = BancoDeDados.produtos.stream()
                .filter(p -> p.getIdProduto() == finalIdProduto)
                .findFirst()
                .orElse(null);

            if (produtoSelecionado == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            System.out.print("Digite a quantidade: ");
            int quantidade = lerOpcao();

            if (quantidade <= 0) {
                System.out.println("Quantidade inválida.");
                continue;
            }
            if (produtoSelecionado.getEstoque().getQuantidade() < quantidade) {
                System.out.println("Bloqueio de venda: estoque insuficiente!");
                continue;
            }

            itensVenda.add(new ItemVenda(produtoSelecionado, quantidade));
            System.out.println(produtoSelecionado.getNome() + " adicionado ao carrinho.");
        }

        if (!itensVenda.isEmpty()) {
            vendaServico.registrarVenda(vendedor, null, itensVenda);
        } else {
            System.out.println("Nenhum item no carrinho. Venda cancelada.");
        }
    }
    
    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}