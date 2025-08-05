package br.com.techamv;

import br.com.techamv.repositorio.BancoDeDados;
import br.com.techamv.visao.MenuPrincipal;

public class Aplicacao {

    public static void main(String[] args) {
        BancoDeDados.inicializar();
        MenuPrincipal menu = new MenuPrincipal();
        menu.exibir();
    }
}