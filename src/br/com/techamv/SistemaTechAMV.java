package br.com.techamv;

import br.com.techamv.modelo.*;
import br.com.techamv.modelo.usuario.*;
import br.com.techamv.repositorio.DadosIniciais;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SistemaTechAMV {

    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Venda> vendas = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Fornecedor> fornecedores = new ArrayList<>();
    private static List<EntradaEstoque> entradasEstoque = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        DadosIniciais.carregar(usuarios, produtos, clientes, fornecedores);

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

    // O restante do código permanece exatamente o mesmo
    
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

    private static void exibirMenuGerente(Gerente gerente) {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n--- MENU GERENTE: " + gerente.getNome() + " ---");
            System.out.println("1. Gestão de Usuários");
            System.out.println("2. Análise de Vendas");
            System.out.println("3. Relatório de Produtos Mais Vendidos");
            System.out.println("4. Histórico de Entradas no Estoque");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: menuGestaoUsuarios(); break;
                case 2: gerarAnaliseDeVendas(); break;
                case 3: gerarRelatorioProdutosMaisVendidos(); break;
                case 4: gerarRelatorioEntradasEstoque(); break;
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
            System.out.println("3. Gestão de Clientes");
            System.out.println("4. Relatório de Desempenho do Dia");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: registrarVenda(vendedor); break;
                case 2: consultarEstoque(); break;
                case 3: menuGestaoClientes(); break;
                case 4: gerarRelatorioDesempenhoVendedor(vendedor); break;
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
            System.out.println("3. Gestão de Fornecedores");
            System.out.println("9. Logout");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: registrarEntradaEstoque(estoquista); break;
                case 2: ajustarInventario(); break;
                case 3: menuGestaoFornecedores(); break;
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
            System.out.println("3. Editar um usuário");
            System.out.println("4. Desativar um usuário");
            System.out.println("9. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: listarUsuarios(); break;
                case 2: criarUsuario(); break;
                case 3: editarUsuario(); break;
                case 4: desativarUsuario(); break;
                case 9: break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    private static void menuGestaoClientes() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n-- Submenu: Gestão de Clientes --");
            System.out.println("1. Listar clientes");
            System.out.println("2. Cadastrar novo cliente");
            System.out.println("9. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: listarClientes(); break;
                case 2: cadastrarCliente(); break;
                case 9: break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    private static void menuGestaoFornecedores() {
        int opcao = 0;
        while (opcao != 9) {
            System.out.println("\n-- Submenu: Gestão de Fornecedores --");
            System.out.println("1. Listar fornecedores");
            System.out.println("2. Cadastrar novo fornecedor");
            System.out.println("9. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = lerOpcao();
            switch (opcao) {
                case 1: listarFornecedores(); break;
                case 2: cadastrarFornecedor(); break;
                case 9: break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

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

        if (itens.isEmpty()) {
            System.out.println("Venda cancelada.");
            return;
        }

        System.out.print("Deseja associar um cliente a esta venda? (s/n): ");
        Cliente clienteAssociado = null;
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            listarClientes();
            System.out.print("Digite o ID do cliente: ");
            int idCliente = lerOpcao();
            clienteAssociado = clientes.stream()
                    .filter(c -> c.getIdCliente() == idCliente)
                    .findFirst()
                    .orElse(null);
            if (clienteAssociado == null) {
                System.out.println("Cliente não encontrado. Venda seguirá sem cliente associado.");
            }
        }

        itens.forEach(item -> item.getProduto().getEstoque().remover(item.getQuantidade()));
        Venda v = new Venda(vendedor, clienteAssociado, itens);
        vendas.add(v);

        System.out.printf("Venda #%d registrada com sucesso! Total: R$ %.2f\n", v.getIdVenda(), v.getValorTotal());
        if(v.getCliente() != null){
            System.out.println("Cliente: " + v.getCliente().getNome());
        }
    }

    private static void consultarEstoque() {
        System.out.println("\n--- ESTOQUE ATUAL ---");
        produtos.forEach(p -> System.out.printf("ID %d - %s | Preço: R$ %.2f | Qtd: %d\n", p.getIdProduto(), p.getNome(), p.getPreco(), p.getEstoque().getQuantidade()));
    }

    private static void registrarEntradaEstoque(Estoquista estoquista) {
        System.out.println("\n--- REGISTRAR ENTRADA DE MERCADORIA ---");
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
        if (qtd <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        listarFornecedores();
        System.out.print("Digite o ID do fornecedor: ");
        int idFornecedor = lerOpcao();
        Optional<Fornecedor> fornecedorOpt = fornecedores.stream().filter(f -> f.getIdFornecedor() == idFornecedor).findFirst();
        if(fornecedorOpt.isEmpty()){
            System.out.println("Fornecedor não encontrado. Operação cancelada.");
            return;
        }

        produtoOpt.get().getEstoque().adicionar(qtd);
        EntradaEstoque novaEntrada = new EntradaEstoque(produtoOpt.get(), fornecedorOpt.get(), estoquista, qtd);
        entradasEstoque.add(novaEntrada);

        System.out.println("Entrada de estoque registrada e quantidade atualizada com sucesso!");
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

    private static void editarUsuario() {
        System.out.println("\n--- EDITAR USUÁRIO ---");
        listarUsuarios();
        System.out.print("Digite o ID do usuário para editar: ");
        int id = lerOpcao();
        Optional<Usuario> uOpt = usuarios.stream().filter(u -> u.getIdUsuario() == id).findFirst();
        if (uOpt.isEmpty()) {
            System.out.println("ID não encontrado.");
            return;
        }
        Usuario usuario = uOpt.get();
        System.out.printf("Editando usuário: %s\n", usuario.getNome());
        System.out.printf("Nome atual: %s. Novo nome (ou enter para manter): ", usuario.getNome());
        String novoNome = scanner.nextLine();
        if (!novoNome.isBlank()) {
            usuario.setNome(novoNome);
        }

        System.out.printf("Email atual: %s. Novo email (ou enter para manter): ", usuario.getEmail());
        String novoEmail = scanner.nextLine();
        if (!novoEmail.isBlank()) {
            usuario.setEmail(novoEmail);
        }
        System.out.println("Usuário atualizado com sucesso!");
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

    private static void cadastrarCliente() {
        System.out.println("\n--- CADASTRAR NOVO CLIENTE ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        clientes.add(new Cliente(nome, cpf));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void listarClientes() {
        System.out.println("\n--- CLIENTES CADASTRADOS ---");
        if(clientes.isEmpty()){
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        clientes.forEach(c -> System.out.printf("ID %d | Nome: %s | CPF: %s\n", c.getIdCliente(), c.getNome(), c.getCpf()));
    }

    private static void cadastrarFornecedor() {
        System.out.println("\n--- CADASTRAR NOVO FORNECEDOR ---");
        System.out.print("Nome do fornecedor: ");
        String nome = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();
        fornecedores.add(new Fornecedor(nome, cnpj));
        System.out.println("Fornecedor cadastrado com sucesso!");
    }

    private static void listarFornecedores() {
        System.out.println("\n--- FORNECEDORES CADASTRADOS ---");
        if(fornecedores.isEmpty()){
            System.out.println("Nenhum fornecedor cadastrado.");
            return;
        }
        fornecedores.forEach(f -> System.out.printf("ID %d | Nome: %s | CNPJ: %s\n", f.getIdFornecedor(), f.getNome(), f.getCnpj()));
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

    private static void gerarAnaliseDeVendas() {
        System.out.println("\n--- HISTÓRICO DE VENDAS ---");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        vendas.forEach(v -> {
            System.out.println("---------------------------------");
            String nomeCliente = (v.getCliente() != null) ? v.getCliente().getNome() : "Não informado";
            System.out.printf("Venda #%d | Data: %s | Vendedor: %s | Cliente: %s\n", v.getIdVenda(), v.getData().format(formatadorData), v.getVendedor().getNome(), nomeCliente);
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

    private static void gerarRelatorioEntradasEstoque() {
        System.out.println("\n--- HISTÓRICO DE ENTRADA DE MERCADORIAS ---");
        if(entradasEstoque.isEmpty()){
            System.out.println("Nenhuma entrada registrada.");
            return;
        }
        entradasEstoque.forEach(e -> {
            System.out.println("---------------------------------");
            System.out.printf("Entrada #%d | Data: %s\n", e.getIdEntrada(), e.getData().format(formatadorData));
            System.out.printf("Produto: %s | Qtd: %d\n", e.getProduto().getNome(), e.getQuantidade());
            System.out.printf("Fornecedor: %s | Responsável: %s\n", e.getFornecedor().getNome(), e.getEstoquista().getNome());
        });
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}