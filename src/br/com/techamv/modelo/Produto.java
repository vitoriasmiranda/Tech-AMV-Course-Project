package br.com.techamv.modelo;

public class Produto {
    private int idProduto;
    private String nome;
    private double preco;
    private Categoria categoria;
    private int estoqueMinimo;
    private Estoque estoque;

    public Produto(int id, String nome, double preco, Categoria categoria, int estoqueMinimo, int estoqueInicial) {
        this.idProduto = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.estoqueMinimo = estoqueMinimo;
        this.estoque = new Estoque(estoqueInicial);
    }

    public int getIdProduto() { return idProduto; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public Categoria getCategoria() { return categoria; }
    public int getEstoqueMinimo() { return estoqueMinimo; }
    public Estoque getEstoque() { return estoque; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPreco(double preco) { this.preco = preco; }
}