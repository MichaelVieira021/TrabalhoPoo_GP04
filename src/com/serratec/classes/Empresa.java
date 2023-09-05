package com.serratec.classes;

import java.util.Scanner;

import com.serratec.conexao.Conexao;
import com.serratec.uteis.Util;
import com.serratec.uteis.ValidarCPF;

//import com.serratec.classes.Cliente;
import java.util.ArrayList;

public class Empresa {
	private static final String nome = "g4Tech";
	private String cnpj = "34554354";
	
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
	//private ArrayList<com.serratec.classes.Pedido> pedido = new ArrayList<>();	
	
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
		/*do {
			s = in.nextLine();
			System.out.println(ValidarCPF.isCPF(s));
			c.setCpf(ValidarCPF.imprimeCPF(s));
		} while(ValidarCPF.isCPF(s) != true);*/
		
		s = in.nextLine();
		c.setCpf(s);
		
		c.setDt_nascimento(Util.validarData("Informe a data de nascimento (dd/MM/yyyy): "));
		
		System.out.println("Informe o Email: ");
		s = in.nextLine();
		c.setEmail(s);
		
		System.out.println("Informe o Telefone: ");
		s = in.nextLine();
		c.setTelefone(s);
	
		//com.serratec.uteis.Util.buscarCep();
		//c.setEndereco(s);
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
		
		System.out.println("Produto: ");
		String s = in.nextLine();
		c.setNome(s);
		
		System.out.println("Descrição: ");
		s = in.nextLine();
		c.setDescricao(s);
		
		System.out.println("Valor custo: ");
		Double n = in.nextDouble();
		//double vl = Util.validarDouble();
		c.setVl_custo(n);
		
		System.out.println("Quantidade: ");
		s = in.nextLine();
		int qtd = Util.validarInteiro(s);
		c.setQtd_estoque(qtd);
	
		System.out.println("Categoria: ");
		s = in.nextLine();
		
		//in.close();
		
		//produto.add(c);
		
		return c;
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
 	
}
