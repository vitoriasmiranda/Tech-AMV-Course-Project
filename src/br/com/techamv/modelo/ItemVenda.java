package br.com.techamv.modelo;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private double precoUnitario;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoUnitario() { return precoUnitario; }
    
    public double getSubtotal() {
        return quantidade * precoUnitario;
    }
}