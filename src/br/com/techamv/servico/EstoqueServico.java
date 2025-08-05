package br.com.techamv.servico;

import br.com.techamv.modelo.EntradaEstoque;
import br.com.techamv.modelo.Fornecedor;
import br.com.techamv.modelo.Produto;
import br.com.techamv.modelo.usuario.Estoquista;
import br.com.techamv.repositorio.BancoDeDados;
import java.util.List;

public class EstoqueServico {

    public void registrarEntrada(Produto produto, int quantidade, Fornecedor fornecedor, Estoquista estoquista) {
        produto.getEstoque().adicionar(quantidade);
        EntradaEstoque entrada = new EntradaEstoque(produto, fornecedor, estoquista, quantidade);
        BancoDeDados.entradasEstoque.add(entrada);
        System.out.println("Entrada registrada com sucesso! Novo estoque de " + produto.getNome() + ": " + produto.getEstoque().getQuantidade());
    }
    
    public void ajustarEstoque(Produto produto, int novaQuantidade, String justificativa) {
        int quantidadeAntiga = produto.getEstoque().getQuantidade();
        int diferenca = novaQuantidade - quantidadeAntiga;
        produto.getEstoque().adicionar(diferenca);
        System.out.println("Estoque de " + produto.getNome() + " ajustado para " + novaQuantidade + ".");
        System.out.println("Justificativa: " + justificativa);
    }
    
    public List<Produto> listarProdutosComEstoqueBaixo() {
        return BancoDeDados.produtos.stream()
                .filter(p -> p.getEstoque().getQuantidade() <= p.getEstoqueMinimo())
                .toList();
    }
}