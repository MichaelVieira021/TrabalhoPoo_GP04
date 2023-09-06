package com.serratec.classes;

import java.util.Scanner;

import com.serratec.conexao.Conexao;
import com.serratec.dao.ClienteDAO;
import com.serratec.dao.ProdutoDAO;
import com.serratec.main.Main;
import com.serratec.uteis.Util;
import com.serratec.uteis.Menus;
import com.serratec.classes.ListaClientes;
import com.serratec.classes.ListaProdutos;
import java.util.ArrayList;

public class Empresa {
	private static final String nome = "g4Tech";
	private String cnpj = "34554354";
	public static ListaClientes clientes; 
	public static ListaProdutos produtos; 

	private com.serratec.conexao.Conexao con; 
	private String schema;
	
	// Criação de novo construtor, necessário para conectar com a main
	// con: O objeto de conexao com o banco de dados
	// schema: A String que contem o nome do schema da base de dados
	public Empresa(Conexao con, String schema) {
		super();
		this.con = con;
		this.schema = schema;
	}

	private ArrayList<com.serratec.classes.Cliente> cliente = new ArrayList<>();
	private ArrayList<com.serratec.classes.Produto> produto = new ArrayList<>();
	//private ArrayList<com.serratec.classes.Endereco> endereco = new ArrayList<>();
	private ArrayList<com.serratec.classes.Pedido> pedido = new ArrayList<>();	
	
 	public com.serratec.classes.Cliente cadastrarCliente() {
 		com.serratec.classes.Cliente c = new com.serratec.classes.Cliente();
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println(Util.LINHA);
		System.out.println("Cadastro de cliente: ");
		System.out.println(Util.LINHA);
		
		Util.br();
		
		System.out.println("Informe o nome: ");
		String s = in.nextLine();
		c.setNome(s);
		
		System.out.println("Informe o CPF (somente números): ");
		s = in.nextLine();
		c.setCpf(s);
			
		System.out.println("Informe o Email: ");
		s = in.nextLine();
		c.setEmail(s);
		
		System.out.println("Informe o Telefone: ");
		s = in.nextLine();
		c.setTelefone(s);
		
		c.setDt_nascimento(Util.validarData("Informe a data de nascimento (dd/MM/yyyy): "));
	
		System.out.println("Informe o CEP: ");
		s = in.nextLine();
		c.setEndereco(com.serratec.uteis.BuscarCEP.buscarCep(s));
		//in.close();
		
		return c;
	}
 	
 	public com.serratec.classes.Cliente adicionarCliente(com.serratec.classes.Cliente cliente) {
 		com.serratec.classes.Cliente c = new com.serratec.classes.Cliente();
		
		c.setNome(cliente.getNome());
		c.setCpf(cliente.getCpf());
		c.setDt_nascimento(cliente.getDt_nascimento());
		c.setEmail(cliente.getEmail());
		c.setTelefone(cliente.getTelefone());
		c.setEndereco(cliente.getEndereco());
		
		this.cliente.add(c);
		
		return cliente;
	}	

 	public com.serratec.classes.Produto cadastrarProduto() {
 		com.serratec.classes.Produto c = new com.serratec.classes.Produto();
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println(Util.LINHA);
		System.out.println("Cadastro de produto: ");
		System.out.println(Util.LINHA);
		
		Util.br();
		
		System.out.print("Produto: ");
		String s = in.next();
		c.setNome(s);
		
		System.out.print("Descrição: ");
		s = in.next();
		c.setDescricao(s);
		
		double vl = Util.validarDouble("Valor custo: ");
		c.setVl_custo(vl);
			
		int qtd = Util.validarInteiro("Quantidade: ");
		c.setQtd_estoque(qtd);
		
		int cat = Menus.menuCategorias();
		//int cat = Util.validarInteiro("Categoria: ");
		c.setIdcategoria(cat);
		
		return c;
	}

 	public Pedido cadastrarPedido() {
 		com.serratec.classes.Pedido pd = new com.serratec.classes.Pedido();
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println(Util.LINHA);
		System.out.println("Cadastro de pedido: ");
		System.out.println(Util.LINHA);
		
		Util.br();
						
		pd.setDt_emissao(Util.validarData("Data de emissão: "));
		
		pd.setIdcliente(Menus.menuClientes());
		
		Menus.menuProdutos();
		//pd.setIdcliente(Util.validarInteiro("Id do cliente: "));
		
		
		return pd;
	}
 	/*public com.serratec.classes.Produto adicionarProduto(com.serratec.classes.Produto produto) {
 		com.serratec.classes.Produto c = new com.serratec.classes.Produto();
		
		c.setNome(produto.getNome());
		c.setDescricao(produto.getDescricao());
		c.setVl_unitario(produto.getVl_unitario());
		c.setQtd_estoque(produto.getQtd_estoque());
		
		this.produto.add(c);
		
		return produto;
	}*/
 	
	public void adicionarProduto(Produto produto) {
		this.produto.add(produto);
	}
 	
 	/*
 	public com.serratec.classes.Endereco adicionarEndereco(com.serratec.classes.Endereco endereco) {
 		com.serratec.classes.Endereco c = new com.serratec.classes.Endereco();
		
		c.setCep(endereco.getCep());
		c.setBairro(endereco.getBairro());
		c.setCidade(endereco.getCidade());
		c.setComplemento(endereco.getComplemento());
		c.setLogradouro(endereco.getLogradouro());
		c.setNumero(endereco.getNumero());
		c.setTipo_logra(endereco.getTipo_logra());
		c.setUf(endereco.getUf());
		
		this.endereco.add(c);
		
		return endereco;
	}*/	
 	
	public void adicionarPedido(Pedido pedido) {
		this.pedido.add(pedido);
	}

	
	public com.serratec.classes.Cliente localizarCliente() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		com.serratec.classes.Cliente cl = new com.serratec.classes.Cliente();
		
		int i = -1;
				
		System.out.println("Informe o CPF do cliente: ");
		String s = in.next();
		clientes = new ListaClientes(con, schema);
		
		for (Cliente c : clientes.getListacliente()) {
			System.out.println("i = " + i + " CPF: " + c.getCpf() + " Input: " + s);
			if (c.getCpf().equals(s)) {
				i = clientes.getListacliente().lastIndexOf(c);
				System.out.println("i encontrado: " + i);
				break;
			}
		}
		
		if (i >= 0) {
			cl.setNome(clientes.getListacliente().get(i).getNome());
			cl.setCpf(clientes.getListacliente().get(i).getCpf());
			cl.setEndereco(clientes.getListacliente().get(i).getEndereco());
			cl.setDt_nascimento(clientes.getListacliente().get(i).getDt_nascimento());
			cl.setIdcliente(clientes.getListacliente().get(i).getIdcliente());
			cl.setEmail(clientes.getListacliente().get(i).getEmail());
			cl.setTelefone(clientes.getListacliente().get(i).getTelefone());
			
			return cl;	
		} else
			return null;
	}
	
	public void atualizarDadosCliente(com.serratec.classes.Cliente cl) {
		int i = 0;
		
		for (Cliente c : clientes.getListacliente()) {
			if (c.getIdcliente() == cl.getIdcliente()) {
				i = clientes.getListacliente().lastIndexOf(c);
				System.out.println("i: " + i);
				break;
			}
		}
		
		clientes.getListacliente().get(i).setNome(cl.getNome());
		clientes.getListacliente().get(i).setCpf(cl.getCpf());
		clientes.getListacliente().get(i).setEndereco(cl.getEndereco());
		clientes.getListacliente().get(i).setDt_nascimento(cl.getDt_nascimento());
		clientes.getListacliente().get(i).setEmail(cl.getEmail());
		clientes.getListacliente().get(i).setTelefone(cl.getTelefone());
	}
	
	public com.serratec.classes.Produto localizarProduto() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		com.serratec.classes.Produto prod = new com.serratec.classes.Produto();
		
		int i = -1;
				
		System.out.println("Digite o Nome: "); 
		String s = in.nextLine();
		produtos = new ListaProdutos(con, schema);
		
		for (Produto c : produtos.getListaProdutos()) {
			if (c.getNome().equals(s)) {
				//System.out.println("NOME:  " + prod1.getNome() );
				i = produtos.getListaProdutos().lastIndexOf(c);
				//System.out.println("Id do Produto: " + (i+1));
				break;
			}
		}
		
		if (i >= 0) {
			prod.setNome(produtos.getListaProdutos().get(i).getNome());
			prod.setDescricao(produtos.getListaProdutos().get(i).getDescricao());
			prod.setVl_custo(produtos.getListaProdutos().get(i).getVl_custo());
			prod.setVl_venda(produtos.getListaProdutos().get(i).getVl_venda());
			prod.setQtd_estoque(produtos.getListaProdutos().get(i).getQtd_estoque());
			prod.setIdcategoria(produtos.getListaProdutos().get(i).getIdcategoria());
			return prod;	
		} else
			return null;
	}
	public void atualizarDadosProduto(com.serratec.classes.Produto proddao) {
		int i = 0;
		
	  for (int index = 0; index < produtos.getListaProdutos().size(); index++) {
	        Produto prod = produtos.getListaProdutos().get(index);
	        if (prod.getNome().equals(proddao.getNome())) {
	            i = index-1; // Encontrou o produto
	            break;
	        }
	    }
		
		produtos.getListaProdutos().get(i).setNome(proddao.getNome());
		produtos.getListaProdutos().get(i).setDescricao(proddao.getDescricao());
		produtos.getListaProdutos().get(i).setVl_custo(proddao.getVl_custo());
		produtos.getListaProdutos().get(i).setVl_venda(proddao.getVl_venda());
		produtos.getListaProdutos().get(i).setQtd_estoque(proddao.getQtd_estoque());
		produtos.getListaProdutos().get(i).setIdcategoria(proddao.getIdcategoria());
	}
	
	
	
}
