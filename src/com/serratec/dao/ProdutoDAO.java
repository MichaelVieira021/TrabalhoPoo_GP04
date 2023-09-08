package com.serratec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.serratec.classes.Produto;
import com.serratec.conexao.Conexao;
import com.serratec.classes.Empresa.ProdutoCarrinho;

public class ProdutoDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	
	public ProdutoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
		prepararSqlAlteracaoEstoque();
		prepararSqlInclusaoProdutoCarrinho();
	}
	
	//------------------------------------------------------------
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".produto";	
		sql += " (nome, descricao, vl_custo, vl_venda, qtd_estoque, idcategoria)";
		sql += " values ";
		sql += " (?, ?, ?, ?, ?, ?)";
		
		try {
			this.pInclusao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlInclusaoProdutoCarrinho() {
		String sql = "insert into "+ this.schema + ".pedido_produto";	
		sql += " (idpedido, idproduto, quantidade)";
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
		String sql = "update "+ this.schema + ".produto";	
		sql += " set nome = ?,";
		sql += " descricao = ?,";
		sql += " vl_custo = ?,";
		sql += " vl_venda = ?,";
		sql += " qtd_estoque = ?,";
		sql += " idcategoria = ?";
		sql += " where idproduto = ?";
		
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
	
	//------------------------------------------------------------
	public int incluirProduto(Produto produto) {
		try {		
							
			pInclusao.setString(1, produto.getNome());
			pInclusao.setString(2, produto.getDescricao());
			pInclusao.setDouble(3, produto.getVl_custo());
			pInclusao.setDouble(4, produto.getVl_venda());
			pInclusao.setInt(5, produto.getQtd_estoque());
			pInclusao.setInt(6, produto.getIdcategoria());
			
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

	public int alterarProduto(Produto produto) {
		try {
			pAlteracao.setString(1, produto.getNome());
			pAlteracao.setString(2, produto.getDescricao());
			pAlteracao.setDouble(3, produto.getVl_custo());
			pAlteracao.setDouble(4, produto.getVl_venda());
			pAlteracao.setInt   (5, produto.getQtd_estoque());
			pAlteracao.setInt   (6, produto.getIdcategoria());
			pAlteracao.setInt   (7, produto.getIdproduto());
			
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
	
	//------------------------------------------------------------
	public ResultSet carregarProduto() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".produto order by idproduto";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
	
	public ArrayList<Produto> carregarProdutoMenu() {
        ArrayList<Produto> produtos = new ArrayList<>();
        String sql = "SELECT idproduto, nome, qtd_estoque FROM " + this.schema + ".produto ORDER BY idproduto";

        ResultSet tabela = conexao.query(sql);

        try {
            while (tabela.next()) {
            	int  idProduto = tabela.getInt("idproduto");
                String nomeProduto = tabela.getString("nome");
                int qtdEstoque = tabela.getInt("qtd_estoque");
                Produto produto = new Produto(idProduto, nomeProduto, qtdEstoque);
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }
	
	public Produto carregarProdutoMenu2(int id) {
		Produto produto = new Produto();
        String sql = "SELECT idproduto, nome, qtd_estoque FROM " + this.schema + ".produto where idproduto = "+id;

        ResultSet tabela = conexao.query(sql);
        Produto teste = new Produto();

        try {
            while (tabela.next()) {
            	int  idProduto = tabela.getInt("idproduto");
                String nomeProduto = tabela.getString("nome");
                //String desc = tabela.getString("descricao");
                //double vlc = tabela.getDouble("vl_custo");
                //double vlv = tabela.getDouble("vl_venda");
                int qtdEstoque = tabela.getInt("qtd_estoque");
               // int  idCategoria = tabela.getInt("idcategoria");
                //produto.setIdproduto(tabela.getInt("idproduto"));
                //produto.setNome(tabela.getString("nome"));
                //produto.setDescricao(tabela.getString("descricao"));
                //produto.setVl_custo(tabela.getDouble("vl_custo"));
                //produto.setVl_venda(tabela.getDouble("vl_venda"));
                //produto.setQtd_estoque(tabela.getInt("qtd_estoque"));
                //produto.setIdcategoria(tabela.getInt("idcategoria"));
                produto.setIdproduto(idProduto);
                produto.setNome(nomeProduto);
                //produto.setDescricao(desc);
               // produto.setVl_custo(vlc);
                //produto.setVl_venda(vlv);
                produto.setQtd_estoque(qtdEstoque);
                //produto.setIdcategoria(idCategoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }
}
