package br.com.techamv.servico;

import br.com.techamv.modelo.Cliente;
import br.com.techamv.modelo.ItemVenda;
import br.com.techamv.modelo.Venda;
import br.com.techamv.modelo.usuario.Vendedor;
import br.com.techamv.repositorio.BancoDeDados;
import java.util.List;

public class VendaServico {

    public void registrarVenda(Vendedor vendedor, Cliente cliente, List<ItemVenda> itens) {
        for (ItemVenda item : itens) {
            item.getProduto().getEstoque().remover(item.getQuantidade());
        }
        Venda novaVenda = new Venda(vendedor, cliente, itens);
        BancoDeDados.vendas.add(novaVenda);
        
        System.out.println("\n--- Venda #" + novaVenda.getIdVenda() + " registrada com sucesso! ---");
        System.out.printf("Valor Total: R$ %.2f\n", novaVenda.getValorTotal());
        System.out.println("Vendedor: " + vendedor.getNome());
        System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "Não identificado"));
    }
}