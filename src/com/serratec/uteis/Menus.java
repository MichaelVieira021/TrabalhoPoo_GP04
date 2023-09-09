package com.serratec.uteis;

import java.util.Scanner;
import com.serratec.classes.Categoria;
import com.serratec.classes.Cliente;
import com.serratec.classes.Empresa.ProdutoCarrinho;
import com.serratec.classes.Pedido;
import com.serratec.classes.Produto;
import com.serratec.dao.ClienteDAO;
import com.serratec.dao.CategoriaDAO;
import com.serratec.dao.ProdutoDAO;
import com.serratec.dao.ProdutoCarrinhoDAO;
import com.serratec.main.Main;
public class Menus {
	
	public static Scanner scan = new Scanner(System.in);
	
	//MENU CLASSES------------------------------
	public static int menuCategorias() {
		CategoriaDAO e  = new CategoriaDAO(Main.con, Main.SCHEMA);
		
		System.out.println("╔═══════════════════════════════════╗");
		System.out.println("║            CATEGORIAS             ║");
		System.out.println("║-----------------------------------║");
		for(Categoria ct : e.carregarCategoriaMenu()) {
        	System.out.println("║        ["+ct.getIdcategoria()+"] - "+ ct.getNm_categoria());
        }
		System.out.println("║                                   ");
		System.out.println("║-----------------------------------║");
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
		System.out.println("║                                   ");
		System.out.println("║-----------------------------------║");
		System.out.println("║    [0] -[CRIAR NOVO CLIENTE]      ║");
		System.out.print  ("╚═══════════════════════════════════╝\n> ");
		
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
		System.out.println("║             PRODUTOS              ║");
		System.out.println("║-----------------------------------║");
		for(Produto ct : e.carregarProdutoMenu()) {
        	System.out.println("║        ["+ct.getIdcategoria()+"] - "+ ct.getNome()+ " - \t\t" + ct.getQtd_estoque());
        }
		System.out.println("║                                   ");
		System.out.println("║-----------------------------------║");
		System.out.println("║                                   ║");
		System.out.print  ("╚═══════════════════════════════════╝\n> ");
		int cat = Util.validarInteiro("Produto: ");
		return cat;
	}

	public static int menuProdutosCarrinho(Pedido pd) {
		ProdutoCarrinhoDAO e  = new ProdutoCarrinhoDAO(Main.con, Main.SCHEMA);
		
		System.out.println("╔═══════════════════════════════════╗");
		System.out.println("║         CARRINHO PEDIDO           ║");
		System.out.println("║-----------------------------------║");
		for(ProdutoCarrinho c : e.carregarProdutoMenuItems(pd.getIdpedido())) {
        	System.out.println("║        ["+c.getIdproduto()+"] - "+ c.getNome()+ " - \t\t" + c.getQuantidade());
        }
		System.out.println("║                                   ");
		System.out.println("║-----------------------------------║");
		System.out.println("║                                   ║");
		System.out.print  ("╚═══════════════════════════════════╝\n> ");
		int cat = Util.validarInteiro("Produto: ");
		return cat;
	}
	
	//-----------------------------------------
	public static void menuPrincipal() {
		
		int opcao = 0;
		
		do {	
			System.out.println("╔═══════════════════════════════════╗");
			System.out.println("║                MENU               ║");
			System.out.println("║-----------------------------------║");
			System.out.println("║                                   ║");
			System.out.println("║        [1] - Cadastrar            ║");
			System.out.println("║        [2] - Alterar              ║");
			System.out.println("║        [3] - Imprimir             ║");
			System.out.println("║        [4] - Excluir              ║");
			System.out.println("║                                   ║");
			System.out.println("║-----------------------------------║");
			System.out.println("║      Digite '0' para [Sair]       ║");
			System.out.print  ("╚═══════════════════════════════════╝\n> ");
			
			opcao = Util.validarInteiro("Informe uma opcao: ");
			
			escolherMenu(opcao);
			
		} while (opcao != 0);
	}
	
	public static int menuPadrao(Util.CRUD tipoCrud) {
		int opcao = 0;
		
		do {
			Util.escrever(Util.LINHA);
			
			switch (tipoCrud) {
			case CADASTRAR: Util.escrever("CADASTRAR"); break;
			case ALTERAR: Util.escrever("ALTERAR"); break;
			case IMPRIMIR: Util.escrever("IMPRIMIR"); break;
			case EXCLUIR: Util.escrever("EXCLUIR"); break;
			}
					
			Util.escrever(Util.LINHA);
			Util.escrever("[1]- Cliente");
			Util.escrever("[2]- Produto");
			Util.escrever("[3]- Pedidos");
			Util.escrever("[0]- Voltar");
			Util.escrever(Util.LINHA);
			
			opcao = Util.validarInteiro("Informe uma opcao: ");			
			
			switch (tipoCrud) {
			case CADASTRAR: escolherMenuCadastrar(opcao); break;
			case ALTERAR: escolherMenuAlterar(opcao); break;
			case IMPRIMIR: escolherMenuImprimir(opcao); break;
			case EXCLUIR: escolherMenuExcluir(opcao); break;
			}
			
		} while (opcao != 0);
		
		return opcao;
	}
	
	
	//CRUD-----------------------------------------------------------------------
	public static void escolherMenu(int opcao) {
		
		switch (opcao) {
		case 1: menuCadastrar(); break;
		case 2: menuAlterar(); break;
		case 3: menuImprimir(); break;
		case 4: menuExcluir(); break;
		case 0: 
			System.out.println("\nPrograma finalizado.");	
			break;
		default: System.out.println("Opcao invalida");
		}
	}
	
	//CADASTRAR-------------------------------------
	public static void menuCadastrar() {
		menuPadrao(Util.CRUD.CADASTRAR);			
	}
	
	public static void escolherMenuCadastrar(int opcao) {
		switch (opcao) {
		case 1: Main.cadastrarCliente(); break;
		case 2: Main.cadastrarProduto(); break;
		case 3: Main.cadastrarPedido(); break;
		case 0: break;
		default: Util.escrever("Opcao invalida");
		}
	}
	
	
	//ALTERAR---------------------------------------
	public static void menuAlterar() {
		menuPadrao(Util.CRUD.ALTERAR);
	}

	public static void escolherMenuAlterar(int opcao) {
		switch (opcao) {
		case 1: Main.alterarCliente(); break;
		case 2: Main.alterarProduto(); break;
		case 3: Main.alterarPedido(); break;
		case 0: break;
		default: Util.escrever("Opcao invalida");
		}
	}

	//IMPRIMIR----------------------------------------
	public static void menuImprimir() {
		menuPadrao(Util.CRUD.IMPRIMIR);
	}
	
	public static void escolherMenuImprimir(int opcao) {		
		switch (opcao) {
		case 1: Main.imprimirCliente(); break;
		case 2: Main.imprimirProduto(); break;
		case 3: Main.imprimirPedido(); break;
		case 0: break;
		default: Util.escrever("Opcao invalida");
		}
	}

	//EXCLUIR----------------------------------------
	public static void menuExcluir() {
		menuPadrao(Util.CRUD.EXCLUIR);
	}
	
	public static void escolherMenuExcluir(int opcao) {		
		switch (opcao) {
		case 1: Main.excluirCliente(); break;
		case 2: Main.excluirProduto(); break;
		case 3: Main.excluirPedido(); break;
		case 0: break;
		default: Util.escrever("Opcao invalida");
		}
	}



}
