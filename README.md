# Sistema de Gerenciamento para TechAMV

Este repositório contém o projeto de um sistema de gerenciamento para a **TechAMV**, uma loja especializada em eletrônicos e acessórios tecnológicos. O trabalho foi desenvolvido como parte do relatório de "Análise e Projeto de Sistema" da Universidade Federal de Ouro Preto.

## 🎯 Sobre o Projeto

O objetivo principal é criar um sistema de informação robusto, justificado pela alta rotatividade de produtos e pela necessidade de um controle de estoque preciso no nicho de eletrônicos. O sistema foi projetado para otimizar a gestão de vendas, estoque e gerar relatórios estratégicos para o negócio.

## 👥 Perfis de Usuário

O sistema foi desenhado para ser utilizado por três perfis distintos, cada um com suas permissões e responsabilidades:

* **Gerente**: Possui acesso irrestrito a todas as funcionalidades do sistema.
* **Vendedor**: É o operador da linha de frente, responsável por registrar as vendas.
* **Comprador/Estoquista**: Responsável pela gestão completa do inventário, desde a entrada de mercadorias até o ajuste de estoque.

## ⚙️ Principais Funcionalidades

As funcionalidades foram definidas com base nas necessidades de cada perfil de usuário.

### Para o Gerente
* **Gestão de Usuários**: Cadastrar, editar e desativar contas de funcionários.
* **Relatórios Estratégicos**: Acessar um painel com dados de lucro e produtos mais vendidos.
* **Definição de Estoque Mínimo**: Configurar o nível de estoque mínimo para cada produto.
* **Análise de Vendas**: Consultar o histórico detalhado de vendas por vendedor e cliente.

### Para o Vendedor
* **Registro de Vendas**: Registrar uma nova venda de forma rápida.
* **Consulta de Estoque**: Verificar a quantidade de produtos disponíveis em tempo real.
* **Bloqueio de Venda**: Ser impedido pelo sistema de vender um produto sem estoque.
* **Relatório de Desempenho Diário**: Gerar um relatório simples das suas vendas do dia.

### Para o Comprador/Estoquista
* **Registro de Entrada de Mercadorias**: Registrar a entrada de novos produtos no sistema.
* **Alertas de Estoque Baixo**: Receber alertas sobre produtos que atingiram o estoque mínimo.
* **Ajuste de Inventário**: Ajustar a quantidade de um produto no sistema, com justificativa.

## 🏗️ Arquitetura do Sistema

A arquitetura do sistema foi modelada com um **Diagrama de Classes UML** para descrever sua estrutura estática, classes, atributos e relacionamentos. As classes principais são:

* **Usuario (Abstrata)**: Classe base com dados comuns a todos os usuários, como `login()` e `logout()`.
* **Gerente, Vendedor, Estoquista**: Classes que herdam de `Usuario` e implementam métodos específicos de sua função.
* **Produto**: Representa um item comercializável, com informações como nome e preço.
* **Estoque**: Classe que encapsula a lógica de controle de quantidade, associada a um `Produto`.
* **Venda**: Registra uma transação, incluindo data, valor total, vendedor e cliente.
* **ItemVenda**: Detalha os produtos e quantidades de uma venda específica.
* **EntradaEstoque**: Garante a rastreabilidade da reposição ao conectar produto, fornecedor e estoquista.
* **Cliente, Fornecedor, Categoria**: Classes de dados que armazenam informações de entidades externas.

---

### 🧑‍💻 Equipe de Desenvolvimento

* Arthur Ramiro Martins
* Melissa Emely Santana
* Vitória de Souza Miranda

<p align="center">
  <b>Universidade Federal de Ouro Preto (UFOP)</b><br>
  Campus ICEA - João Monlevade<br>
  <i>21 de Julho de 2025</i>
</p>
