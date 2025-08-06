package br.com.techamv.modelo;

import br.com.techamv.modelo.usuario.Estoquista;

import java.time.LocalDateTime;

public class EntradaEstoque {
    private int idEntrada;
    private Produto produto;
    private Fornecedor fornecedor;
    private Estoquista estoquista;
    private int quantidade;
    private LocalDateTime data;
    private static int proximoId = 1;

    public EntradaEstoque(Produto produto, Fornecedor fornecedor, Estoquista estoquista, int quantidade) {
        this.idEntrada = proximoId++;
        this.produto = produto;
        this.fornecedor = fornecedor;
        this.estoquista = estoquista;
        this.quantidade = quantidade;
        this.data = LocalDateTime.now();
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public Produto getProduto() {
        return produto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Estoquista getEstoquista() {
        return estoquista;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDateTime getData() {
        return data;
    }
}
