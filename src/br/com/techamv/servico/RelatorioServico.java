package br.com.techamv.servico;

import br.com.techamv.modelo.Produto;
import br.com.techamv.modelo.Venda;
import br.com.techamv.modelo.usuario.Vendedor;
import br.com.techamv.repositorio.BancoDeDados;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioServico {

    private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void gerarRelatorioLucro() {
        double lucroTotal = BancoDeDados.vendas.stream().mapToDouble(Venda::getValorTotal).sum();
        System.out.println("\n--- Relatório de Lucro Total ---");
        System.out.printf("Lucro total de todas as vendas: R$ %.2f\n", lucroTotal);
    }

    public void gerarRelatorioProdutosMaisVendidos() {
        System.out.println("\n--- Relatório de Produtos Mais Vendidos ---");
        Map<String, Integer> produtosVendidos = BancoDeDados.vendas.stream()
                .flatMap(venda -> venda.getItens().stream())
                .collect(Collectors.groupingBy(
                        item -> item.getProduto().getNome(),
                        Collectors.summingInt(item -> item.getQuantidade())
                ));
        
        if (produtosVendidos.isEmpty()) {
            System.out.println("Nenhum produto vendido ainda.");
            return;
        }

        produtosVendidos.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println("Produto: " + entry.getKey() + " - Quantidade Vendida: " + entry.getValue()));
    }

    public void gerarRelatorioDesempenhoVendedor(Vendedor vendedor) {
        System.out.println("\n--- Relatório de Desempenho Diário para " + vendedor.getNome() + " ---");
        double totalVendidoHoje = BancoDeDados.vendas.stream()
                .filter(v -> v.getVendedor().equals(vendedor) && v.getData().toLocalDate().equals(LocalDate.now()))
                .mapToDouble(Venda::getValorTotal)
                .sum();
        
        System.out.printf("Total vendido hoje: R$ %.2f\n", totalVendidoHoje);
    }

    public void gerarAnaliseDeVendas() {
        System.out.println("\n--- Análise de Histórico de Vendas ---");
        if (BancoDeDados.vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada no sistema.");
            return;
        }

        BancoDeDados.vendas.forEach(venda -> {
            System.out.println("-----------------------------------------");
            System.out.println("Venda ID: " + venda.getIdVenda());
            System.out.println("Data: " + venda.getData().format(formatador));
            System.out.println("Vendedor: " + venda.getVendedor().getNome());
            System.out.println("Cliente: " + (venda.getCliente() != null ? venda.getCliente().getNome() : "Não Identificado"));
            System.out.println("Itens vendidos:");
            venda.getItens().forEach(item -> {
                System.out.printf("  - %s (%d un) - Subtotal: R$ %.2f\n",
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getSubtotal()
                );
            });
            System.out.printf("VALOR TOTAL DA VENDA: R$ %.2f\n", venda.getValorTotal());
        });
        System.out.println("-----------------------------------------");
    }
}