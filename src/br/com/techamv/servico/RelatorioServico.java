package br.com.techamv.servico;

import br.com.techamv.modelo.Produto;
import br.com.techamv.modelo.Venda;
import br.com.techamv.modelo.usuario.Vendedor;
import br.com.techamv.repositorio.BancoDeDados;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioServico {

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
}