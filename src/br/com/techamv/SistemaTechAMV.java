package br.com.techamv;

import br.com.techamv.modelo.*;
import br.com.techamv.modelo.usuario.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
            
            Usuario usuarioLogado = autenticarUsuario(email, senha);

            if (usuarioLogado != null) {
                System.out.println("\nLogin bem-sucedido! Bem-vindo(a), " + usuarioLogado.getNome());
                direcionarParaMenu(usuarioLogado);
            } else {
                System.out.println("Credenciais ou status de usuário inválido.");
            }
        }
        System.out.println("Sistema finalizado.");
        scanner.close();
    }

    private static Usuario autenticarUsuario(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha) && u.isAtivo()) {
                return u;
            }
        }
        return null;
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
    
    private static Produto encontrarProdutoPorId(int id) {
        for (Produto p : produtos) {
            if (p.getIdProduto() == id) {
                return p;
            }
        }
        return null;
    }
    
    private static Usuario encontrarUsuarioPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == id) {
                return u;
            }
        }
        return null;
    }
    
    private static Cliente encontrarClientePorId(int id) {
        for (Cliente c : clientes) {
            if (c.getIdCliente() == id) {
                return c;
            }
        }
        return null;
    }
    
    private static Fornecedor encontrarFornecedorPorId(int id) {
        for (Fornecedor f : fornecedores) {
            if (f.getIdFornecedor() == id) {
                return f;
            }
        }
        return null;
    }

    private static void registrarVenda(Vendedor vendedor) {
        System.out.println("\n--- REGISTRO DE VENDA ---");
        List<ItemVenda> itens = new ArrayList<>();
        while (true) {
            consultarEstoque();
            System.out.print("Digite o ID do produto (ou 0 para finalizar): ");
            int id = lerOpcao();
            if (id == 0) break;
            
            Produto produto = encontrarProdutoPorId(id);

            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }
            
            int quantidadeJaNoCarrinho = 0;
            for(ItemVenda item : itens) {
                if (item.getProduto().equals(produto)) {
                    quantidadeJaNoCarrinho += item.getQuantidade();
                }
            }

            int estoqueDisponivelParaVenda = produto.getEstoque().getQuantidade() - quantidadeJaNoCarrinho;

            System.out.printf("Disponível para esta venda: %d\n", estoqueDisponivelParaVenda);
            System.out.print("Quantidade: ");
            int qtd = lerOpcao();
            
            if (qtd <= 0) {
                System.out.println("Quantidade inválida.");
            } else if (estoqueDisponivelParaVenda < qtd) {
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
            clienteAssociado = encontrarClientePorId(idCliente);
            if (clienteAssociado == null) {
                System.out.println("Cliente não encontrado. Venda seguirá sem cliente associado.");
            }
        }

        for (ItemVenda item : itens) {
            item.getProduto().getEstoque().remover(item.getQuantidade());
        }
        
        Venda v = new Venda(vendedor, clienteAssociado, itens);
        vendas.add(v);

        System.out.printf("Venda #%d registrada com sucesso! Total: R$ %.2f\n", v.getIdVenda(), v.getValorTotal());
        if(v.getCliente() != null){
            System.out.println("Cliente: " + v.getCliente().getNome());
        }
    }

    private static void consultarEstoque() {
        System.out.println("\n--- ESTOQUE ATUAL ---");
        for (Produto p : produtos) {
            System.out.printf("ID %d - %s | Preço: R$ %.2f | Qtd: %d\n", p.getIdProduto(), p.getNome(), p.getPreco(), p.getEstoque().getQuantidade());
        }
    }

    private static void registrarEntradaEstoque(Estoquista estoquista) {
        System.out.println("\n--- REGISTRAR ENTRADA DE MERCADORIA ---");
        consultarEstoque();
        System.out.print("Digite o ID do produto que receberá entrada: ");
        int id = lerOpcao();
        Produto produto = encontrarProdutoPorId(id);
        
        if (produto == null) {
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
        Fornecedor fornecedor = encontrarFornecedorPorId(idFornecedor);
        
        if(fornecedor == null){
            System.out.println("Fornecedor não encontrado. Operação cancelada.");
            return;
        }

        produto.getEstoque().adicionar(qtd);
        EntradaEstoque novaEntrada = new EntradaEstoque(produto, fornecedor, estoquista, qtd);
        entradasEstoque.add(novaEntrada);

        System.out.println("Entrada de estoque registrada e quantidade atualizada com sucesso!");
    }


    private static void ajustarInventario() {
        System.out.println("\n--- AJUSTAR INVENTÁRIO ---");
        consultarEstoque();
        System.out.print("Digite o ID do produto para ajustar: ");
        int id = lerOpcao();
        Produto p = encontrarProdutoPorId(id);

        if (p == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        
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
        for (Usuario u : usuarios) {
            System.out.printf("ID %d | %s | %s | %s | Status: %s\n", u.getIdUsuario(), u.getTipo(), u.getNome(), u.getEmail(), u.isAtivo() ? "Ativo" : "Inativo");
        }
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
        Usuario usuario = encontrarUsuarioPorId(id);

        if (usuario == null) {
            System.out.println("ID não encontrado.");
            return;
        }
        
        System.out.printf("Editando usuário: %s\n", usuario.getNome());
        System.out.printf("Nome atual: %s. Novo nome (ou enter para manter): ", usuario.getNome());
        String novoNome = scanner.nextLine();
        if (novoNome != null && !novoNome.trim().isEmpty()) {
            usuario.setNome(novoNome);
        }

        System.out.printf("Email atual: %s. Novo email (ou enter para manter): ", usuario.getEmail());
        String novoEmail = scanner.nextLine();
        if (novoEmail != null && !novoEmail.trim().isEmpty()) {
            usuario.setEmail(novoEmail);
        }
        System.out.println("Usuário atualizado com sucesso!");
    }

    private static void desativarUsuario() {
        System.out.println("\n--- DESATIVAR USUÁRIO ---");
        listarUsuarios();
        System.out.print("Digite o ID do usuário para desativar: ");
        int id = lerOpcao();
        Usuario usuario = encontrarUsuarioPorId(id);
        
        if (usuario != null) {
            usuario.setAtivo(false);
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
        for (Cliente c : clientes) {
            System.out.printf("ID %d | Nome: %s | CPF: %s\n", c.getIdCliente(), c.getNome(), c.getCpf());
        }
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
        for (Fornecedor f : fornecedores) {
            System.out.printf("ID %d | Nome: %s | CNPJ: %s\n", f.getIdFornecedor(), f.getNome(), f.getCnpj());
        }
    }

    private static void gerarRelatorioDesempenhoVendedor(Vendedor vendedor) {
        System.out.println("\n--- DESEMPENHO DE VENDAS (HOJE) ---");
        double total = 0.0;
        for(Venda v : vendas) {
            if (v.getVendedor().equals(vendedor) && v.getData().toLocalDate().equals(LocalDate.now())) {
                total += v.getValorTotal();
            }
        }
        System.out.printf("Total vendido por %s hoje: R$ %.2f\n", vendedor.getNome(), total);
    }

    private static void verificarAlertasEstoqueBaixo() {
        List<Produto> produtosComEstoqueBaixo = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getEstoque().getQuantidade() <= p.getEstoqueMinimo()) {
                produtosComEstoqueBaixo.add(p);
            }
        }
        
        if (!produtosComEstoqueBaixo.isEmpty()) {
            System.out.println("\nALERTA: PRODUTOS COM ESTOQUE BAIXO!");
            for(Produto p : produtosComEstoqueBaixo) {
                System.out.printf("- %s | Atual: %d | Mínimo: %d\n", p.getNome(), p.getEstoque().getQuantidade(), p.getEstoqueMinimo());
            }
        }
    }

    private static void gerarAnaliseDeVendas() {
        System.out.println("\n--- HISTÓRICO DE VENDAS ---");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        for (Venda v : vendas) {
            System.out.println("---------------------------------");
            String nomeCliente = "Não informado";
            if (v.getCliente() != null) {
                nomeCliente = v.getCliente().getNome();
            }
            System.out.printf("Venda #%d | Data: %s | Vendedor: %s | Cliente: %s\n", v.getIdVenda(), v.getData().format(formatadorData), v.getVendedor().getNome(), nomeCliente);
            
            for (ItemVenda item : v.getItens()) {
                System.out.printf("  -> %s (%d un): R$ %.2f\n", item.getProduto().getNome(), item.getQuantidade(), item.getSubtotal());
            }
            System.out.printf("Total da Venda: R$ %.2f\n", v.getValorTotal());
        }
    }

    private static void gerarRelatorioProdutosMaisVendidos() {
        System.out.println("\n--- PRODUTOS MAIS VENDIDOS ---");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        
        // Etapa 1: Contar as vendas de cada produto usando um Mapa
        Map<String, Integer> contagem = new HashMap<>();
        for (Venda v : vendas) {
            for (ItemVenda item : v.getItens()) {
                String nomeProduto = item.getProduto().getNome();
                int quantidadeAtual = 0;
                if (contagem.containsKey(nomeProduto)) {
                    quantidadeAtual = contagem.get(nomeProduto);
                }
                contagem.put(nomeProduto, quantidadeAtual + item.getQuantidade());
            }
        }
        
        // Etapa 2: Encontrar o mais vendido, imprimir, remover e repetir
        while (!contagem.isEmpty()) {
            String produtoMaisVendido = null;
            int maxUnidades = -1;

            // Encontra o produto com mais unidades vendidas no mapa
            for (String nomeProduto : contagem.keySet()) {
                int unidades = contagem.get(nomeProduto);
                if (unidades > maxUnidades) {
                    maxUnidades = unidades;
                    produtoMaisVendido = nomeProduto;
                }
            }
            
            // Imprime o resultado encontrado e o remove para a próxima iteração
            if (produtoMaisVendido != null) {
                System.out.printf("- %s | Unidades Vendidas: %d\n", produtoMaisVendido, maxUnidades);
                contagem.remove(produtoMaisVendido);
            }
        }
    }

    private static void gerarRelatorioEntradasEstoque() {
        System.out.println("\n--- HISTÓRICO DE ENTRADA DE MERCADORIAS ---");
        if(entradasEstoque.isEmpty()){
            System.out.println("Nenhuma entrada registrada.");
            return;
        }
        for (EntradaEstoque e : entradasEstoque) {
            System.out.println("---------------------------------");
            System.out.printf("Entrada #%d | Data: %s\n", e.getIdEntrada(), e.getData().format(formatadorData));
            System.out.printf("Produto: %s | Qtd: %d\n", e.getProduto().getNome(), e.getQuantidade());
            System.out.printf("Fornecedor: %s | Responsável: %s\n", e.getFornecedor().getNome(), e.getEstoquista().getNome());
        }
    }

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

        clientes.add(new Cliente("João da Silva", "111.222.333-44"));
        clientes.add(new Cliente("Maria Oliveira", "555.666.777-88"));

        fornecedores.add(new Fornecedor("Eletrônicos Brasil S/A", "12.345.678/0001-99"));
        fornecedores.add(new Fornecedor("Importados Tech Ltda", "98.765.432/0001-11"));
    }
}