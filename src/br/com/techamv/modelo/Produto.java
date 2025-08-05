package br.com.techamv.modelo;

public class Produto {
    private static int proximoId = 1;
    private int idProduto;
    private String nome;
    private double preco;
    private int estoqueMinimo;
    private Categoria categoria;
    private Estoque estoque;

    public Produto(String nome, double preco, Categoria categoria, int estoqueMinimo, int estoqueInicial) {
        this.idProduto = proximoId++;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.estoqueMinimo = estoqueMinimo;
        this.estoque = new Estoque(estoqueInicial);
    }

    public int getIdProduto() { return idProduto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(int estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Estoque getEstoque() { return estoque; }
}