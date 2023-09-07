package com.serratec.uteis;

//import java.util.ArrayList;
import java.util.Scanner;
import com.serratec.classes.Categoria;
import com.serratec.classes.Cliente;
import com.serratec.classes.Produto;
import com.serratec.dao.ClienteDAO;
import com.serratec.dao.CategoriaDAO;
import com.serratec.dao.ProdutoDAO;
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
	
	public static int menuClientes() {
		ClienteDAO e  = new ClienteDAO(Main.con, Main.SCHEMA);
		
		int teste = 0;
		
		System.out.println("╔═══════════════════════════════════╗");
		System.out.println("║            CLIENTES               ║");
		System.out.println("║-----------------------------------║");
		for(Cliente cl : e.carregarClienteMenu()) {
			teste++;
        	System.out.println("║        ["+cl.getIdcliente()+"] - "+ cl.getNome());
        }
		//teste(e.carregarCategoriaMenu());
		System.out.println("║                                   ");
		System.out.println("║-----------------------------------║");
		//System.out.println("║                                   ║");
		System.out.println("║    [0] -[CRIAR NOVO CLIENTE]      ║");
		System.out.print  ("╚═══════════════════════════════════╝\n> ");
		//pd.setIdcliente(Util.validarInteiro("Id do cliente: "));
		
		int cat;
		do {
			cat = Util.validarInteiro("");
			if(cat < 0 || cat > teste) {
				System.out.println("Opção inválida!");
			}else {
				if(cat == 0) {
					Main.cadastrarCliente();
					cat = teste+1;
					break;
				}
			}
		}while(cat < 0 || cat > teste);
		return cat;
	}
	
	public static int menuProdutos() {
		ProdutoDAO e  = new ProdutoDAO(Main.con, Main.SCHEMA);
		
		System.out.println("╔═══════════════════════════════════╗");
		System.out.println("║            CATEGORIAS             ║");
		System.out.println("║-----------------------------------║");
		for(Produto ct : e.carregarProdutoMenu()) {
        	System.out.println("║        ["+ct.getIdcategoria()+"] - "+ ct.getNome());
        }
		//teste(e.carregarCategoriaMenu());
		System.out.println("║                                   ");
		System.out.println("║-----------------------------------║");
		System.out.println("║                                   ║");
		//System.out.println("║    [0] -[CRIAR NOVA CATEGORIA]    ║");
		System.out.print  ("╚═══════════════════════════════════╝\n> ");
		int cat = Util.validarInteiro("Produto: ");
		return cat;
	}
}
