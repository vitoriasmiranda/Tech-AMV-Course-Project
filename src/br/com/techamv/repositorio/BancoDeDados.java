package br.com.techamv.repositorio;

import br.com.techamv.modelo.*;
import br.com.techamv.modelo.usuario.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {

    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Produto> produtos = new ArrayList<>();
    public static List<Categoria> categorias = new ArrayList<>();
    public static List<Fornecedor> fornecedores = new ArrayList<>();
    public static List<Venda> vendas = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<EntradaEstoque> entradasEstoque = new ArrayList<>();

    public static void inicializar() {
        Categoria catSmartphones = new Categoria("Smartphones");
        Categoria catNotebooks = new Categoria("Notebooks");
        Categoria catAcessorios = new Categoria("Acessórios");
        categorias.addAll(List.of(catSmartphones, catNotebooks, catAcessorios));

        produtos.add(new Produto("Galaxy S25", 7999.90, catSmartphones, 10, 20));
        produtos.add(new Produto("Phone 16", 8500.50, catSmartphones, 10, 15));
        produtos.add(new Produto("Dell G15", 5500.00, catNotebooks, 5, 8));
        produtos.add(new Produto("MacBook Air M4", 12000.00, catNotebooks, 3, 5));
        produtos.add(new Produto("Mouse sem fio", 150.00, catAcessorios, 20, 50));
        produtos.add(new Produto("Teclado Mecânico", 350.00, catAcessorios, 15, 30));

        usuarios.add(new Gerente("Vitória Miranda", "vitoria.gerente@techamv.com", "123"));
        usuarios.add(new Vendedor("Melissa Emely", "melissa.vendedora@techamv.com", "456"));
        usuarios.add(new Estoquista("Arthur Ramiro", "arthur.estoquista@techamv.com", "789"));
        
        fornecedores.add(new Fornecedor("Distribuidora de Eletrônicos Brasil", "11.222.333/0001-44"));
        fornecedores.add(new Fornecedor("Importados & Cia", "55.666.777/0001-88"));

        clientes.add(new Cliente("Carlos Silva", "123.456.789-00"));
    }
}