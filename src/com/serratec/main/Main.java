package com.serratec.main;

import com.serratec.classes.Cliente;
import com.serratec.classes.Produto;
import com.serratec.classes.Empresa;
import com.serratec.classes.Pedido;
import com.serratec.conexao.Conexao;
import com.serratec.conexao.DadosConexao;
import com.serratec.uteis.Menus;
import com.serratec.uteis.Util;
import com.serratec.dao.CreateDAO;
import com.serratec.dao.PedidoDAO;
import com.serratec.dao.ClienteDAO;
import com.serratec.dao.ProdutoDAO;

public class Main {
	public static Empresa g4Tech;
	public static Conexao con;
	public static DadosConexao dadosCon = new DadosConexao();
	public static final String BANCO = "trabalhopooteste";
	public static final String SCHEMA = "grupo04";
	public static final String LOCAL = "localhost";
	public static final String USUARIO = "postgres";
	public static final String SENHA = "123456";
	public static final String PORTA = "5432";
	public static final String BD = "PostgreSql";
	
	public static void main(String[] args) {
		
		dadosCon.setBanco(BANCO);
		dadosCon.setLocal(LOCAL);
		dadosCon.setUser(USUARIO);
		dadosCon.setSenha(SENHA);
		dadosCon.setPorta(PORTA);
		dadosCon.setBd(BD);
		
		if (CreateDAO.createBD(BANCO, SCHEMA, dadosCon)) {
			con = new Conexao(dadosCon); 
			con.conect();
			//clientes = new ListaClientes(con, SCHEMA);
			g4Tech = new Empresa(con, SCHEMA);
			Menus.menuPrincipal();
		} else {
			System.out.println("Ocorreu um problema na criacao do banco de dados");
		}
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
		
	public static void excluirCliente() {

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

	public static void excluirProduto() {
		
	}
		
	//PEDIDO-----------------------------------------------
	public static void cadastrarPedido() {
		Pedido pd = new Pedido();
		PedidoDAO pddao = new PedidoDAO(con, SCHEMA);
			
		pd = g4Tech.cadastrarPedido();
		pddao.incluirPedido(pd);
		g4Tech.adicionarPedido(pd);			
	}
		
	public static void alterarPedido() {

	}
		
	public static void excluirPedido() {

	}
		
}
