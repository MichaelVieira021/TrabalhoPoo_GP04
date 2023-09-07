package com.serratec.classes;

import java.util.Scanner;
import com.serratec.conexao.Conexao;
import com.serratec.uteis.Util;
import com.serratec.uteis.Menus;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Empresa {
	//private static final String nome = "g4Tech";
	//private String cnpj = "34554354";
	public static ListaClientes clientes; 
	public static ListaProdutos produtos; 
	
	private ArrayList<com.serratec.classes.Cliente> cliente = new ArrayList<>();
	private ArrayList<com.serratec.classes.Produto> produto = new ArrayList<>();
	private ArrayList<com.serratec.classes.Pedido> pedido = new ArrayList<>();
	
	private com.serratec.conexao.Conexao con; 
	private String schema;
	
	public Empresa(Conexao con, String schema) {
		super();
		this.con = con;
		this.schema = schema;
	}

	//----------------------------------------------------------------------
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

	public com.serratec.classes.Cliente localizarCliente() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		com.serratec.classes.Cliente cl = new com.serratec.classes.Cliente();
		
		int i = -1;
				
		System.out.println("Informe o CPF do cliente: ");
		String s = in.next();
		clientes = new ListaClientes(con, schema);
		
		for (Cliente c : clientes.getListacliente()) {
			if (c.getCpf().equals(s)) {
				i = clientes.getListacliente().lastIndexOf(c);
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
	
	public void listarDadosClientes() {
 		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		clientes = new ListaClientes(con, schema);

		System.out.println("\n==================================================================================");
		System.out.println("                               LISTAGEM DE CLIENTES                               ");
		System.out.println("==================================================================================");
		System.out.println("Nome\t\t\t\tCPF\t\tEmail\t\t\tDt.Nasc.");
		System.out.println("----------------------------------------------------------------------------------");
		for (Cliente c : clientes.getListacliente()) {
			System.out.printf("%-25s\t", c.getNome());
			System.out.printf("%-15s\t", c.getCpf());
			System.out.printf("%-10s\t\t", c.getEmail());
			if (c.getDt_nascimento() != null)
				//System.out.printf("%s", c.getDt_nascimento().format(dtf));
				System.out.println("Não sei usar Data :(");
			System.out.println();
		}

		System.out.println();
	}
	
	public void excluirCliente(com.serratec.classes.Cliente cl) {
		int i = -1;
		clientes = new ListaClientes(con, schema);
		for (Cliente c : clientes.getListacliente()) {
			if (c.getIdcliente() == cl.getIdcliente())
				i = clientes.getListacliente().lastIndexOf(c);
		}
		
		if (i >= 0)
			this.cliente.remove(i);
	}
	
	//-----------------------------------------------------------------------
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

	public void adicionarProduto(Produto produto) {
		this.produto.add(produto);
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
				i = produtos.getListaProdutos().lastIndexOf(c);
				break;
			}
		}
		
		if (i >= 0) {
			prod.setIdproduto(produtos.getListaProdutos().get(i).getIdproduto());
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
	
	
	//------------------------------------------------------------------------
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
 	
	public void adicionarPedido(Pedido pedido) {
		this.pedido.add(pedido);
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
 	
 	
 	/*public com.serratec.classes.Endereco adicionarEndereco(com.serratec.classes.Endereco endereco) {
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
