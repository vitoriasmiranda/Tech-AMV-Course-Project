package br.com.techamv;

import br.com.techamv.modelo.*;
import br.com.techamv.modelo.usuario.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaTechAMV {

    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Venda> vendas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        inicializarDados();
        
        while (true) {
            System.out.println("\n===== SISTEMA DE GERENCIAMENTO TECHAMV =====");
            System.out.println("Faça o login ou digite 'sair' no email para fechar.");
            System.out.print("Email: ");
            String email = scanner.nextLine();

            if (email.equalsIgnoreCase("sair")) {
                break;
            }
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            Optional<Usuario> usuarioOpt = autenticarUsuario(email, senha);

            if (usuarioOpt.isPresent()) {
                System.out.println("\nLogin bem-sucedido! Bem-vindo(a), " + usuarioOpt.get().getNome());
                direcionarParaMenu(usuarioOpt.get());
            } else {
                System.out.println("Credenciais ou status de usuário inválido.");
            }
        }
        System.out.println("Sistema finalizado.");
        scanner.close();
    }
    
    // MÉTODOS DE LÓGICA E CONTROLE

    private static Optional<Usuario> autenticarUsuario(String email, String senha) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha) && u.isAtivo())
                .findFirst();
    }

    private static void direcionarParaMenu(Usuario usuario) {
        if (usuario instanceof Gerente) {
            exibirMenuGerente((Gerente) usuario);
        } else if (usuario instanceof Vendedor) {
            exibirMenuVendedor((Vendedor) usuario);
        } else if (usuario instanceof Estoquista) {
            exibirMenuEstoquista((Estoquista) usuario);
        }
    }

    // MENUS
    
    private static void exibirMenuGerente(Gerente gerente) {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n--- MENU GERENTE: " + gerente.getNome() + " ---");
            System.out.println("1. Gestão de Usuários");
            System.out.println("2. Análise de Vendas");
            System.out.println("3. Relatório de Produtos Mais Vendidos");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: menuGestaoUsuarios(); break;
                case 2: gerarAnaliseDeVendas(); break;
                case 3: gerarRelatorioProdutosMaisVendidos(); break;
                case 9: System.out.println("Fazendo logout..."); break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    private static void exibirMenuVendedor(Vendedor vendedor) {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n--- MENU VENDEDOR: " + vendedor.getNome() + " ---");
            System.out.println("1. Registrar Nova Venda");
            System.out.println("2. Consultar Estoque");
            System.out.println("3. Relatório de Desempenho do Dia");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: registrarVenda(vendedor); break;
                case 2: consultarEstoque(); break;
                case 3: gerarRelatorioDesempenhoVendedor(vendedor); break;
                case 9: System.out.println("Fazendo logout..."); break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }
    
    private static void exibirMenuEstoquista(Estoquista estoquista) {
        int opcao = 0;
        while (opcao != 9) {
            verificarAlertasEstoqueBaixo();
            System.out.println("\n--- MENU ESTOQUISTA: " + estoquista.getNome() + " ---");
            System.out.println("1. Registrar Entrada de Mercadoria");
            System.out.println("2. Ajustar Inventário");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: registrarEntradaEstoque(); break;
                case 2: ajustarInventario(); break;
                case 9: System.out.println("Fazendo logout..."); break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }
    
    private static void menuGestaoUsuarios() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n-- Submenu: Gestão de Usuários --");
            System.out.println("1. Listar usuários");
            System.out.println("2. Criar novo usuário");
            System.out.println("3. Desativar um usuário");
            System.out.println("9. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: listarUsuarios(); break;
                case 2: criarUsuario(); break;
                case 3: desativarUsuario(); break;
                case 9: break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    // FUNCIONALIDADES
    
    private static void registrarVenda(Vendedor vendedor) {
        System.out.println("\n--- REGISTRO DE VENDA ---");
        List<ItemVenda> itens = new ArrayList<>();
        while (true) {
            consultarEstoque();
            System.out.print("Digite o ID do produto (ou 0 para finalizar): ");
            int id = lerOpcao();
            if (id == 0) break;

            Optional<Produto> produtoOpt = produtos.stream().filter(p -> p.getIdProduto() == id).findFirst();
            if (produtoOpt.isEmpty()) {
                System.out.println("Produto não encontrado.");
                continue;
            }
            Produto produto = produtoOpt.get();
            System.out.print("Quantidade: ");
            int qtd = lerOpcao();
            if (qtd <= 0) {
                System.out.println("Quantidade inválida.");
            } else if (produto.getEstoque().getQuantidade() < qtd) {
                System.out.println("Venda bloqueada: estoque insuficiente.");
            } else {
                itens.add(new ItemVenda(produto, qtd));
                System.out.println(">> " + produto.getNome() + " adicionado.");
            }
        }
        if (!itens.isEmpty()) {
            itens.forEach(item -> item.getProduto().getEstoque().remover(item.getQuantidade()));
            Venda v = new Venda(vendedor, null, itens);
            vendas.add(v);
            System.out.printf("Venda #%d registrada com sucesso! Total: R$ %.2f\n", v.getIdVenda(), v.getValorTotal());
        } else {
            System.out.println("Venda cancelada.");
        }
    }
    
    private static void consultarEstoque() {
        System.out.println("\n--- ESTOQUE ATUAL ---");
        produtos.forEach(p -> System.out.printf("ID %d - %s | Preço: R$ %.2f | Qtd: %d\n", p.getIdProduto(), p.getNome(), p.getPreco(), p.getEstoque().getQuantidade()));
    }

    private static void gerarRelatorioDesempenhoVendedor(Vendedor vendedor) {
        System.out.println("\n--- DESEMPENHO DE VENDAS (HOJE) ---");
        double total = vendas.stream()
                .filter(v -> v.getVendedor().equals(vendedor) && v.getData().toLocalDate().equals(LocalDate.now()))
                .mapToDouble(Venda::getValorTotal)
                .sum();
        System.out.printf("Total vendido por %s hoje: R$ %.2f\n", vendedor.getNome(), total);
    }
    
    private static void verificarAlertasEstoqueBaixo() {
        List<Produto> baixos = produtos.stream().filter(p -> p.getEstoque().getQuantidade() <= p.getEstoqueMinimo()).toList();
        if (!baixos.isEmpty()) {
            System.out.println("\nALERTA: PRODUTOS COM ESTOQUE BAIXO!");
            baixos.forEach(p -> System.out.printf("- %s | Atual: %d | Mínimo: %d\n", p.getNome(), p.getEstoque().getQuantidade(), p.getEstoqueMinimo()));
        }
    }

    private static void registrarEntradaEstoque() {
        System.out.println("\n--- REGISTRAR ENTRADA DE PRODUTO ---");
        consultarEstoque();
        System.out.print("Digite o ID do produto que receberá entrada: ");
        int id = lerOpcao();
        Optional<Produto> produtoOpt = produtos.stream().filter(p -> p.getIdProduto() == id).findFirst();
        if (produtoOpt.isEmpty()) {
            System.out.println("Produto não encontrado.");
            return;
        }
        System.out.print("Quantidade a ser adicionada: ");
        int qtd = lerOpcao();
        if (qtd > 0) {
            produtoOpt.get().getEstoque().adicionar(qtd);
            System.out.println("Estoque atualizado com sucesso!");
        } else {
            System.out.println("Quantidade inválida.");
        }
    }
    
    private static void ajustarInventario() {
        System.out.println("\n--- AJUSTAR INVENTÁRIO ---");
        consultarEstoque();
        System.out.print("Digite o ID do produto para ajustar: ");
        int id = lerOpcao();
        Optional<Produto> produtoOpt = produtos.stream().filter(p -> p.getIdProduto() == id).findFirst();
        if (produtoOpt.isEmpty()) {
            System.out.println("Produto não encontrado.");
            return;
        }
        Produto p = produtoOpt.get();
        System.out.printf("Estoque atual de %s: %d\n", p.getNome(), p.getEstoque().getQuantidade());
        System.out.print("Digite a nova quantidade correta: ");
        int qtd = lerOpcao();
        if (qtd >= 0) {
            p.getEstoque().setQuantidade(qtd);
            System.out.println("Inventário ajustado.");
        } else {
            System.out.println("Quantidade inválida.");
        }
    }
    
    private static void listarUsuarios() {
        System.out.println("\n--- USUÁRIOS DO SISTEMA ---");
        usuarios.forEach(u -> System.out.printf("ID %d | %s | %s | %s | Status: %s\n", u.getIdUsuario(), u.getTipo(), u.getNome(), u.getEmail(), u.isAtivo() ? "Ativo" : "Inativo"));
    }

    private static void criarUsuario() {
        System.out.println("\n--- CRIAR NOVO USUÁRIO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Tipo (Gerente, Vendedor, Estoquista): ");
        String tipo = scanner.nextLine();
        
        Usuario u;
        if (tipo.equalsIgnoreCase("gerente")) u = new Gerente(nome, email, senha);
        else if (tipo.equalsIgnoreCase("vendedor")) u = new Vendedor(nome, email, senha);
        else if (tipo.equalsIgnoreCase("estoquista")) u = new Estoquista(nome, email, senha);
        else {
            System.out.println("Tipo inválido.");
            return;
        }
        usuarios.add(u);
        System.out.println("Usuário criado com sucesso!");
    }
    
    private static void desativarUsuario() {
        System.out.println("\n--- DESATIVAR USUÁRIO ---");
        listarUsuarios();
        System.out.print("Digite o ID do usuário para desativar: ");
        int id = lerOpcao();
        Optional<Usuario> uOpt = usuarios.stream().filter(u -> u.getIdUsuario() == id).findFirst();
        if (uOpt.isPresent()) {
            uOpt.get().setAtivo(false);
            System.out.println("Usuário desativado.");
        } else {
            System.out.println("ID não encontrado.");
        }
    }
    
    private static void gerarAnaliseDeVendas() {
        System.out.println("\n--- HISTÓRICO DE VENDAS ---");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        vendas.forEach(v -> {
            System.out.println("---------------------------------");
            System.out.printf("Venda #%d | Data: %s | Vendedor: %s\n", v.getIdVenda(), v.getData().format(formatadorData), v.getVendedor().getNome());
            v.getItens().forEach(item -> System.out.printf("  -> %s (%d un): R$ %.2f\n", item.getProduto().getNome(), item.getQuantidade(), item.getSubtotal()));
            System.out.printf("Total da Venda: R$ %.2f\n", v.getValorTotal());
        });
    }

    private static void gerarRelatorioProdutosMaisVendidos() {
        System.out.println("\n--- PRODUTOS MAIS VENDIDOS ---");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        Map<String, Integer> contagem = vendas.stream()
            .flatMap(v -> v.getItens().stream())
            .collect(Collectors.groupingBy(item -> item.getProduto().getNome(), Collectors.summingInt(ItemVenda::getQuantidade)));

        contagem.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> System.out.printf("- %s | Unidades Vendidas: %d\n", entry.getKey(), entry.getValue()));
    }
    
    // MÉTODOS AUXILIARES
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void inicializarDados() {
        Categoria catSmart = new Categoria("Smartphones");
        Categoria catNote = new Categoria("Notebooks");
        Categoria catAcc = new Categoria("Acessórios");
        
        produtos.add(new Produto(1, "Galaxy S25", 7999.90, catSmart, 10, 20));
        produtos.add(new Produto(2, "Phone 16", 8500.50, catSmart, 10, 15));
        produtos.add(new Produto(3, "Dell G15", 5500.00, catNote, 5, 8));
        produtos.add(new Produto(4, "Mouse sem fio", 150.00, catAcc, 20, 50));

        usuarios.add(new Gerente("Arthur Ramiro", "g", "1"));
        usuarios.add(new Vendedor("Melissa Emely", "v", "1"));
        usuarios.add(new Estoquista("Vitória de Souza", "e", "1"));
    }
}
