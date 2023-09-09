package com.serratec.main;

import java.util.Scanner;
import com.serratec.classes.Cliente;
import com.serratec.classes.Produto;
import com.serratec.classes.Empresa;
import com.serratec.classes.Pedido;
import com.serratec.conexao.*;
import com.serratec.uteis.Menus;
import com.serratec.uteis.Util;
import com.serratec.dao.CreateDAO;
import com.serratec.dao.ClienteDAO;
import com.serratec.dao.ProdutoDAO;
import com.serratec.dao.PedidoDAO;
import com.serratec.dao.ProdutoCarrinhoDAO;
import com.serratec.classes.ListaPedidos;

public class Main {
	public static Empresa g4Tech;
	public static Conexao con;
	public static DadosConexao dadosCon = new DadosConexao();
	public static final String BANCO = "trabalhopooteste";
	public static final String SCHEMA = "grupo04";
	public static final String PATH = System.getProperty("user.dir");
	public static final String SFILE = "DadosConexao.ini";
	
	//public static final String LOCAL = "localhost";
	//public static final String PORTA = "5432";
	//public static final String USUARIO = "postgres";
	//public static final String SENHA = "123456";
	//public static final String BD = "PostgreSql";
	
	public static void main(String[] args) {
		System.out.println("Present Project Directory : "+ System.getProperty("user.dir"));
		if (configInicial()) {
			if (CreateDAO.createBD(BANCO, SCHEMA, dadosCon)) {
				con = new Conexao(dadosCon); 
				con.conect();
				g4Tech = new Empresa(con, SCHEMA);
				Menus.menuPrincipal();
			} else {
				System.out.println("Ocorreu um problema na criacao do banco de dados");
			}	
		} else
			System.out.println("Não foi possível executar o sistema.");
	}
	
	public static boolean configInicial() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		ArquivoConexao conexaoIni = new ArquivoConexao(PATH + "\\" + SFILE);
		boolean abrirSistema = false;
		
		if (conexaoIni.criarArquivo()) {
			if (conexaoIni.alimentaDadosConexao()) {
				dadosCon = conexaoIni.getData();
				abrirSistema = true;
			} else {
				conexaoIni.apagarArquivo();
				System.out.println("Arquivo de configuração de conexão:\n");
				System.out.println("Local: ");
				String local = input.nextLine();
				System.out.println("Porta: ");
				String porta = input.nextLine();
				System.out.println("Usuário: ");
				String usuario = input.nextLine();
				System.out.println("Senha: ");
				String senha = input.nextLine();
				System.out.println("Database: ");
				String banco = input.nextLine();
				
				if (conexaoIni.criarArquivo()) {
					conexaoIni.escreverArquivo("bd=PostgreSql");
					conexaoIni.escreverArquivo("local="+local);
					conexaoIni.escreverArquivo("porta="+porta);
					conexaoIni.escreverArquivo("usuario="+usuario);
					conexaoIni.escreverArquivo("senha="+senha);
					conexaoIni.escreverArquivo("banco="+banco);
					
					if (conexaoIni.alimentaDadosConexao()) {
						dadosCon = conexaoIni.getData();
						abrirSistema = true;
					} else System.out.println("Não foi possível efetuar a configuração.\nVerifique");	
				}
			}
		} else
			System.out.println("Houve um problema na criação do arquivo de configuração.");
		
		return abrirSistema;
	}
	
	//CLIENTE--------------------------------------------
	public static void cadastrarCliente() {
		Cliente c = new Cliente();
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
			
		c = g4Tech.cadastrarCliente();		
		int retorno = cdao.incluirCliente(c);
			
		if (retorno > 0) {
			Util.escrever("Cliente criado com sucesso.");
			g4Tech.adicionarCliente(c);
		}
	}
		
	public static void alterarCliente() {
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		Cliente c = g4Tech.localizarCliente();
			
		c.dadosPessoa();
		c.alterarCliente();
		g4Tech.atualizarDadosCliente(c);
		cdao.alterarCliente(c);
	}
		
	public static void imprimirCliente() {
		g4Tech.listarDadosClientes();
	}
	
	public static void excluirCliente() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		int i = 0;
		ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
		Cliente c = g4Tech.localizarCliente();
		
		if (c != null) {		
			Util.escrever("Deseja excluir o cliente: " + c.getNome());
			char s = input.next().charAt(0);
			
			if (s == 's' || s == 'S')
				i = cdao.excluirCliente(c);
			System.out.println("i: " + i);
			if (i > 0) {
				g4Tech.excluirCliente(c);
				System.out.println("Cliente excluido com sucesso.");
			}
		} else
			System.out.println("Cliente nao encontrado.");
	}

	//PRODUTO--------------------------------------------
	public static void cadastrarProduto() {
		Produto p = new Produto();
		ProdutoDAO pdao = new ProdutoDAO(con, SCHEMA);
			
		p = g4Tech.cadastrarProduto();
		pdao.incluirProduto(p);
		g4Tech.adicionarProduto(p);
	}
		
	public static void alterarProduto() {
		ProdutoDAO proddao = new ProdutoDAO(con, SCHEMA);
		Produto prod = g4Tech.localizarProduto();
			
		prod.dadosProdutos();
		prod.alterarProduto();
		g4Tech.atualizarDadosProduto(prod);
		proddao.alterarProduto(prod);
	}

	public static void imprimirProduto() {
		g4Tech.listarDadosProdutos();
	}
	
	public static void excluirProduto() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		int i = 0;
		ProdutoDAO proddao = new ProdutoDAO(con, SCHEMA);
		Produto prod = g4Tech.localizarProduto();
		
		if (prod != null) {		
			Util.escrever("Deseja excluir o Produto: " + prod.getNome());
			char s = input.next().charAt(0);
			
			if (s == 's' || s == 'S') {
				i = proddao.excluirProduto(prod);
			}
			if (i > 0) {
				g4Tech.excluirProduto(prod);
				System.out.println("Produto excluido com sucesso.");
			}
		} else
			System.out.println("Produto não encontrado.");
	}
		
	//PEDIDO-----------------------------------------------
	public static void cadastrarPedido() {
		Pedido pd = new Pedido();
		pd = g4Tech.cadastrarPedido();
	}
		
	public static void alterarPedido() {
		Pedido pd = g4Tech.localizarPedido();
		ListaPedidos l = new ListaPedidos(con, SCHEMA, 2);

		pd.dadosPedidos();
		pd.alterarPedido();
		Menus.menuProdutosCarrinho(pd);
		
		//l.carregarListaPedidosCarrinho();
		//prodC.carregarProdutoMenu2(pd.getIdpedido());
		//Menus.menuProdutosCarrinho(pd);
	}
		
	public static void imprimirPedido() {
		g4Tech.listarDadosPedidos();
	}
	
	public static void excluirPedido() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		int i = 0;
		PedidoDAO pedidao = new PedidoDAO(con, SCHEMA);
		Pedido pedi = g4Tech.localizarPedido();
		
		if (pedi != null) {		
			Util.escrever("Deseja excluir o Pedido n°: "  + pedi.getIdpedido() + " Feito no dia: " + pedi.getDt_emissao());
			char s = input.next().charAt(0);
			
			if (s == 's' || s == 'S') {
				i = pedidao.excluirPedido(pedi);
			}
			if (i > 0) {
				g4Tech.excluirPedido(pedi);
				System.out.println("Produto excluido com sucesso.");
			}
		} else
			System.out.println("Produto não encontrado.");
	}
		
}
