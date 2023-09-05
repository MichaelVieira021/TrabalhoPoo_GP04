package com.serratec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.serratec.classes.Produto;
import com.serratec.conexao.Conexao;

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
	}
	
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
	
	/*public int alterarCliente(Livro livro) {
		try {
			pAlteracao.setString(1, livro.getTitulo());
			pAlteracao.setString(2, livro.getAutor());
			pAlteracao.setString(3, livro.getEditora());
			pAlteracao.setString(4, livro.getIsbn());
			pAlteracao.setInt(5, livro.getNumPaginas());
			pAlteracao.setInt(6, livro.getQuantidade());
			pAlteracao.setInt(7, livro.getIdlivro());
			
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nLivro nao alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}*/
	
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
	
	public ResultSet carregarProduto() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".produto order by idproduto";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
}
