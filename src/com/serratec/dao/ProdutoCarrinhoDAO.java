package com.serratec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.serratec.conexao.Conexao;
import com.serratec.classes.Pedido;
import com.serratec.classes.Produto;
//import com.serratec.classes.PedidoItem;
import com.serratec.classes.Empresa.ProdutoCarrinho;

public class ProdutoCarrinhoDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	
	public ProdutoCarrinhoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusaoProdutoCarrinho();
		prepararSqlAlteracao();
	}
	
	public ProdutoCarrinhoDAO(Conexao conexao, String schema, int i) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusaoProdutoCarrinho();
		prepararSqlAlteracaoEstoque();
	}

//PREPARAR SQL--------------------------------------------
	private void prepararSqlInclusaoProdutoCarrinho() {
		String sql = "insert into "+ this.schema + ".pedido_produto";	
		sql += " (idpedido,idproduto,quantidade)";
		sql += " values ";
		sql += " (?, ?, ?)";
		
		try {
			this.pInclusao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlAlteracao() {
		String sql = "update "+ this.schema + ".pedido_produto";	
		sql += " set quantidade = ?,";		
		sql += " idproduto = ?";		
		sql += " where idpedido_produto = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlAlteracaoEstoque() {
		String sql = "update "+ this.schema + ".produto";	
		sql += " set qtd_estoque = ?";
		sql += " where idproduto = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

//--------------------------------------------------------
	public int alterarEstoque(Produto produto) {
		try {
			pAlteracao.setInt   (1, produto.getQtd_estoque());
			pAlteracao.setInt   (2, produto.getIdproduto());		
			
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto não alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int alterarPedidoItem(ProdutoCarrinho produtocarrinho) {
		try {
			pAlteracao.setInt(1, produtocarrinho.getQuantidade());
			pAlteracao.setInt(2, produtocarrinho.getIdproduto());
			pAlteracao.setInt(3, produtocarrinho.getIdpedidoitem());
			
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nProduto não alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}

	public int incluirProdutoCarrinho(ProdutoCarrinho produtocarrinho) {
		try {		
							
			pInclusao.setInt(1, produtocarrinho.getIdpedido());
			pInclusao.setInt(2, produtocarrinho.getIdproduto());
			pInclusao.setInt(3, produtocarrinho.getQuantidade());
			
			return pInclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nLivro nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}

//--------------------------------------------------------	
	public ResultSet carregarProdutoCarrinho() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".pedido_produto order by idpedido_produto";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
	
	public ResultSet alterarProdutoCarrinho () {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".pedido_produto order by idpedido_produto";
		
		tabela = conexao.query(sql);
		
		return tabela;
		
	}
	
	public ProdutoCarrinho carregarProdutoCarrinho(int id) {
				
		String sql = "select quantidade, idpedido_produto, idproduto from " + this.schema + ".pedido_produto where idpedido_produto = "+id;
		
		ResultSet tabela = conexao.query(sql);
		ProdutoCarrinho teste = new ProdutoCarrinho();
		
		try {
			while (tabela.next()) {
				teste.setQuantidade(tabela.getInt("quantidade"));
				teste.setIdpedidoitem(tabela.getInt("idpedido_produto"));
				teste.setIdproduto(tabela.getInt("idproduto"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teste;
	}
	
	public ArrayList <ProdutoCarrinho> carregarProdutoMenuItems(int id) {
		ArrayList <ProdutoCarrinho> produtoC = new ArrayList<>();

		String sql = "SELECT pp.idpedido_produto, pp.idpedido, pp.idproduto, pp.quantidade, p.nome FROM " + this.schema + ".pedido_produto pp"
				+ " LEFT JOIN " + this.schema + ".produto  p ON p.idproduto = pp.idproduto"
				+ " where idpedido = "+id+" ORDER BY p.nome";
        
        ResultSet tabela = conexao.query(sql);
        try {
            while (tabela.next()) {
            	ProdutoCarrinho teste = new ProdutoCarrinho();
            	Produto pr = new Produto();

                pr.setIdproduto(tabela.getInt("idpedido"));
                pr.setNome(tabela.getString("nome"));
                teste.setPr(pr);
                teste.setIdpedidoitem(tabela.getInt("idpedido_produto"));
                teste.setIdpedido(tabela.getInt("idpedido"));
                teste.setIdproduto(tabela.getInt("idproduto"));
                teste.setQuantidade(tabela.getInt("quantidade"));
                produtoC.add(teste);
   		
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtoC;
    }
	
    public ArrayList <ProdutoCarrinho> listarProdutosNoPedidoSelecionado(int id) {
		ArrayList <ProdutoCarrinho> ArrayProduto = new ArrayList<>();

		String sql = "SELECT " + this.schema + ".pedido.idpedido,";
		sql += " " + this.schema + ".produto.idproduto,";
		sql += " " + this.schema + ".produto.nome,";
		sql += " " + this.schema + ".pedido_produto.quantidade,";
		sql += " " + this.schema + ".produto.vl_venda,";
		sql += " " + this.schema + ".produto.vl_venda * " + this.schema + ".pedido_produto.quantidade as \"vl_total\"";
		sql += " FROM " + this.schema + ".pedido_produto";
		sql += " INNER JOIN " + this.schema + ".produto ON " + this.schema + ".pedido_produto.idproduto = " + this.schema + ".produto.idproduto";
		sql += " INNER JOIN " + this.schema + ".pedido  ON " + this.schema + ".pedido_produto.idpedido  = " + this.schema + ".pedido.idpedido";
		sql += " WHERE "+this.schema+".pedido_produto.idpedido = " + id;
    	
        ResultSet tabela = conexao.query(sql);
        try {
            while (tabela.next()) {
                ProdutoCarrinho pc = new ProdutoCarrinho();
            	int  idPedido = tabela.getInt("idpedido");
            	int  idProduto = tabela.getInt("idproduto");
            	int  qtd = tabela.getInt("quantidade");   
            	String nome = tabela.getString("nome");
                double vl_venda = tabela.getDouble("vl_venda");
                pc.setIdpedido(idPedido);
            	pc.setIdproduto(idProduto);
            	pc.setQuantidade(qtd);
            	pc.setNome(nome);
                pc.setVl_venda(vl_venda);

            	/*System.out.println(pc.getIdpedido());
            	System.out.println(pc.getIdproduto());
            	System.out.println(pc.getQuantidade());
            	System.out.println(pc.getNome());
            	System.out.println(pc.getVl_venda());*/
                
                ArrayProduto.add(pc);    		
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ArrayProduto;
    }
	
}
