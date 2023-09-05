package com.serratec.uteis;

import java.util.ArrayList;
import java.util.Scanner;
import com.serratec.classes.Categoria;
import com.serratec.dao.CategoriaDAO;
import com.serratec.main.Main;


public class Menus {
	
	public static Scanner scan = new Scanner(System.in);
	
	/*private static void teste(ArrayList<Categoria> categorias) {
        for(Categoria ct : categorias) {
        	System.out.println("║        ["+ct.getIdcategoria()+"] - "+ ct.getNm_categoria());
        }
	}*/
	
	public static int menuCategorias() {
		CategoriaDAO e  = new CategoriaDAO(Main.con, Main.SCHEMA);
		
		System.out.println("╔═══════════════════════════════════╗");
		System.out.println("║            CATEGORIAS             ║");
		System.out.println("║-----------------------------------║");
		for(Categoria ct : e.carregarCategoriaMenu()) {
        	System.out.println("║        ["+ct.getIdcategoria()+"] - "+ ct.getNm_categoria());
        }
		//teste(e.carregarCategoriaMenu());
		System.out.println("║                                   ");
		System.out.println("║-----------------------------------║");
		//System.out.println("║                                   ║");
		System.out.println("║    [0] -[CRIAR NOVA CATEGORIA]    ║");
		System.out.print  ("╚═══════════════════════════════════╝\n> ");
		int cat = Util.validarInteiro("Categoria: ");
		return cat;
	}
}
