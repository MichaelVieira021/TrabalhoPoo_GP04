package com.serratec.classes;

import java.util.Scanner;
import com.serratec.conexao.Conexao;
import com.serratec.dao.ClienteDAO;
import com.serratec.dao.PedidoDAO;
import com.serratec.dao.ProdutoDAO;
import com.serratec.main.Main;
import com.serratec.uteis.Util;
import com.serratec.uteis.Menus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Empresa {
	//private static final String nome = "g4Tech";
	//private String cnpj = "34554354";
	public static ListaClientes clientes; 
	public static ListaProdutos produtos; 
	public static ListaPedidos pedidos;
	public static ListaPedidos pedidos2;
	
	private ArrayList<com.serratec.classes.Cliente> cliente = new ArrayList<>();
	private ArrayList<com.serratec.classes.Produto> produto = new ArrayList<>();
	private ArrayList<com.serratec.classes.Pedido> pedido = new ArrayList<>();
	private static ArrayList<ProdutoCarrinho> pedidocarrinho = new ArrayList<>();
	
	private static Conexao con; 
	private static String schema;
	
	public static class ProdutoCarrinho extends Produto{
		private int idpedidoitem;
		private int idpedido;
		private int quantidade;
		private Produto pr;


		public Produto getPr() {
			return pr;
		}

		public void setPr(Produto pr) {
			this.pr = pr;
		}

		public int getIdpedido() {
			return idpedido;
		}

		public void setIdpedido(int idpedido) {
			this.idpedido = idpedido;
		}


		public int getIdpedidoitem() {
			return idpedidoitem;
		}

		public void setIdpedidoitem(int idpedidoitem) {
			this.idpedidoitem = idpedidoitem;
		}

		public int getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(int quantidade) {
			this.quantidade = quantidade;
		}
	}
	
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
				System.out.printf("%s", c.getDt_nascimento().format(dtf));
				//System.out.println("Não sei usar Data :(");
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
 	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
 	    LocalDateTime nowt = LocalDateTime.now();  
 	    LocalDate now = LocalDate.now();  
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println(Util.LINHA);
		System.out.println("Cadastro de pedido: ");
		System.out.println(Util.LINHA);
		
		Util.br();
		System.out.println("Data de Emissão: " + dtf.format(nowt));				
		pd.setDt_emissao(now);
		
		pd.setIdcliente(Menus.menuClientes());
		
		//PedidoDAO pddao = new PedidoDAO(con, com.serratec.main.Main.SCHEMA);
		//pddao.incluirPedido(pd);
		
		inserirNoBd(inserirProdutoCarrinho(pd),pedidocarrinho);
		
		//Menus.menuProdutos();
		//pd.setIdcliente(Util.validarInteiro("Id do cliente: "));
		
		return pd;
	}
 	
	public void adicionarPedido(Pedido pedido) {
		this.pedido.add(pedido);
	}

	public Pedido inserirProdutoCarrinho(Pedido pd) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		//ArrayList<ProdutoCarrinho> Pedidocarrinho = new ArrayList<>();
		
		ProdutoCarrinho teste = new ProdutoCarrinho();
		//int idped = -1;
		//pedidos = new ListaPedidos(con, schema);
		
		//for (Pedido c : pedidos.getListapedidos()) {
		//	idped = pedidos.getListapedidos().lastIndexOf(c)+1;
		//}
		
		int quant;
		int opcao = 1;
		boolean verEstoque = false, prIgual = true;
		System.out.println("=====================================");
		System.out.println("	   INSERIR PRODUTOS");
		System.out.println("=====================================");
		int idprod;
		do {
			do {
				idprod = Menus.menuProdutos();
				for(ProdutoCarrinho pr : pedidocarrinho) {
					if(pr.getPr().getIdproduto() == idprod) { 
						pedidocarrinho.remove(pedidocarrinho.indexOf(pr));
						prIgual = true;
						break;
					}else prIgual = false;
				}
				prIgual = false;
			}while(prIgual);
			
			quant = Util.validarInteiro("Digite a quantidade a ser adicionada: ");			
			verEstoque = verificarEstoque(idprod, quant);
			
			if (!verEstoque) {
				Util.escrever("Produto fora de estoque!");
				opcao = 1;
			} else {
				System.out.println("Produto adicionado com Sucesso!");
				System.out.println("Digite 1 para adicionar outro produto.");
				opcao = in.nextInt();	
			}
			
			
				
		} while (opcao==1);
		
		
		
		System.out.println("Pedido realizado com Sucesso!");
		return pd;
	}
	
	public void inserirNoBd(Pedido pd, ArrayList<ProdutoCarrinho> pc) {
		PedidoDAO pddao = new PedidoDAO(con, com.serratec.main.Main.SCHEMA);
		ProdutoDAO prdao = new ProdutoDAO(con, com.serratec.main.Main.SCHEMA);
		
		
		pddao.incluirPedido(pd);
		int idPedido = pddao.ultimoIdPedido();		
		
		for (ProdutoCarrinho pdc : pedidocarrinho) {
				pdc.setIdpedido(idPedido);
				prdao.incluirProdutoCarrinho(pdc);
				prdao.alterarEstoque(pdc.getPr());
		}
		pedidocarrinho.clear();
	}

	public static boolean verificarEstoque(int idprod, int quant) {
		boolean retorno = false;
		int i = -1;
		int j = -1;
		
		//pedidos = new ListaPedidos(con, schema);
		//produtos = new ListaProdutos(con, schema);
		com.serratec.classes.Produto prod = new com.serratec.classes.Produto();
		ProdutoDAO e  = new ProdutoDAO(Main.con, Main.SCHEMA);
		//com.serratec.classes.Pedido ped = new com.serratec.classes.Pedido();
		prod = e.carregarProdutoMenu2(idprod);
		/*for (Produto c : e.carregarProdutoMenu()) {
			if (c.getIdproduto() == idprod) {
				i = c.getIdproduto();
				prod.setIdproduto(c.getIdproduto());
				prod.setQtd_estoque(c.getQtd_estoque());
				System.out.println("---------------");
				System.out.println("Produto c ID: " + prod.getIdproduto());
				System.out.println("Produto c Estoque: " + prod.getQtd_estoque());
				break;
			}
		}*/
		
		/*for (Pedido c : pedidos.getListapedidos()) {
			if (c.getIdpedido() == idped) {
				j = pedidos.getListapedidos().lastIndexOf(c);
				ped.setIdpedido(pedidos.getListapedidos().get(j).getIdpedido());
				System.out.println("Pedido c ID: " + ped.getIdpedido());
				break;
			}
		}*/
		
		if (prod.getQtd_estoque() > 0 && prod.getQtd_estoque() >= quant) {
			prod.setQtd_estoque(prod.getQtd_estoque()-quant);
			adicionarProdutoCarrinho(prod, quant);
			retorno = true;
		} else {
			if (prod.getQtd_estoque() == 0) {
				System.out.println("Produto fora de estoque.");
			} else {
				System.out.println("A quantidade solicitada e superior a quantidade disponivel.");
			}
		}
		return retorno;	
	}

	
	private static void adicionarProdutoCarrinho(Produto l, int quant) {
		ProdutoCarrinho le = new ProdutoCarrinho();
		ProdutoDAO pddao = new ProdutoDAO(con, schema);
		
		le.setIdproduto(l.getIdproduto());
		le.setQtd_estoque(l.getQtd_estoque());
		le.setQuantidade(quant);
		le.setPr(l);
		//c.setIdpedido(c.getIdpedido()+1);
		System.out.println("---------------");
		System.out.println("ID do produto: " + le.getIdproduto());
		System.out.println("Estoque do produto: " + le.getQtd_estoque());
		System.out.println("Quantidade comprada do produto: " + le.getQuantidade());
		//System.out.println("ID do Pedido: " + c.getIdpedido());
		
		pedidocarrinho.add(le);
		
		//PrepararSqlAlterarEstoque
		//pddao.alterarEstoque(l);	
		//pddao.incluirProdutoCarrinho(le,c);
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
 	
	public com.serratec.classes.Pedido localizarPedido() {

		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		com.serratec.classes.Pedido pd = new com.serratec.classes.Pedido();
		
		PedidoDAO pdao = new PedidoDAO(con,schema);
		
		int i = -1;
				
		 
		int s = Util.validarInteiro("Digite o código: ");
		pedidos = new ListaPedidos(con, schema, 1);
		pedidos2 = new ListaPedidos(con, schema, 2);
		
		//pedido = pdao.carregarPedidos ();
		
		for (Pedido pd1 : pedidos.getListapedidos()) {
			if (pd1.getIdpedido() == s) {
				i = pedidos.getListapedidos().lastIndexOf(pd1);
				break;
			}
		}
		
		
		if (i >= 0) {
			Pedido ped = new Pedido ();
			
			ped = pedidos.getListapedidos().get(i);
			
			pd.setIdpedido(ped.getIdpedido());
			pd.setDt_emissao(ped.getDt_emissao());
			pd.setIdcliente(ped.getIdcliente());
			
			Menus.menuProdutosCarrinho(pd);
			
			return pd;	
		} else
			return null;
	}

	public void imprimirProdutoCarrinho (Pedido pd) {
		
	}
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
