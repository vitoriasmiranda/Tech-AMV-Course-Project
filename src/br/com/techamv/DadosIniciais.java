package br.com.techamv;

import br.com.techamv.modelo.Categoria;
import br.com.techamv.modelo.Cliente;
import br.com.techamv.modelo.Fornecedor;
import br.com.techamv.modelo.Produto;
import br.com.techamv.modelo.usuario.Estoquista;
import br.com.techamv.modelo.usuario.Gerente;
import br.com.techamv.modelo.usuario.Usuario;
import br.com.techamv.modelo.usuario.Vendedor;

import java.util.ArrayList;
import java.util.List;

public class DadosIniciais {

    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Gerente("Arthur Ramiro", "g", "1"));
        usuarios.add(new Vendedor("Melissa Emely", "v", "1"));
        usuarios.add(new Estoquista("Vitória de Souza", "e", "1"));
        return usuarios;
    }

    public static List<Produto> carregarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        
        Categoria catSmart = new Categoria("Smartphones");
        Categoria catNote = new Categoria("Notebooks");
        Categoria catAcc = new Categoria("Acessórios");

        produtos.add(new Produto(1, "Galaxy S25", 7999.90, catSmart, 10, 20));
        produtos.add(new Produto(2, "Phone 16", 8500.50, catSmart, 10, 15));
        produtos.add(new Produto(3, "Dell G15", 5500.00, catNote, 5, 8));
        produtos.add(new Produto(4, "Mouse sem fio", 150.00, catAcc, 20, 50));
        
        return produtos;
    }

    public static List<Cliente> carregarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("João da Silva", "111.222.333-44"));
        clientes.add(new Cliente("Maria Oliveira", "555.666.777-88"));
        return clientes;
    }

    public static List<Fornecedor> carregarFornecedores() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(new Fornecedor("Eletrônicos Brasil S/A", "12.345.678/0001-99"));
        fornecedores.add(new Fornecedor("Importados Tech Ltda", "98.765.432/0001-11"));
        return fornecedores;
    }
}