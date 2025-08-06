package br.com.techamv.modelo;

public class Estoque {
    private int quantidade;

    public Estoque(int quantidadeInicial) {
        this.quantidade = quantidadeInicial;
    }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void adicionar(int valor) { this.quantidade += valor; }
    public void remover(int valor) { this.quantidade -= valor; }
}