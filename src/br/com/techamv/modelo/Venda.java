package br.com.techamv.modelo;

import br.com.techamv.modelo.usuario.Vendedor;
import java.time.LocalDateTime;
import java.util.List;

public class Venda {
    private static int proximoId = 1;
    private int idVenda;
    private Vendedor vendedor;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private double valorTotal;
    private LocalDateTime data;

    public Venda(Vendedor vendedor, Cliente cliente, List<ItemVenda> itens) {
        this.idVenda = proximoId++;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.itens = itens;
        this.data = LocalDateTime.now();
        this.valorTotal = calcularValorTotal();
    }

    private double calcularValorTotal() {
        return this.itens.stream().mapToDouble(ItemVenda::getSubtotal).sum();
    }
    
    public int getIdVenda() { return idVenda; }
    public Vendedor getVendedor() { return vendedor; }
    public Cliente getCliente() { return cliente; }
    public List<ItemVenda> getItens() { return itens; }
    public double getValorTotal() { return valorTotal; }
    public LocalDateTime getData() { return data; }
}