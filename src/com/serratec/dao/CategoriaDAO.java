
package com.serratec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.serratec.classes.Categoria;
import com.serratec.conexao.Conexao;

public class CategoriaDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	
	public CategoriaDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
	}
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".categoria";	
		sql += " (nm_categoria, descricao)";
		sql += " values ";
		sql += " (?, ?)";
		
		try {
			this.pInclusao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	private void prepararSqlAlteracao() {
		String sql = "update "+ this.schema + ".categoria";	
		sql += " set nm_categoria = ?,";
		sql += " descricao = ?,";
		sql += " where idcategoria = ?";
		
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
	
	public int incluirCategoria(Categoria categoria) {
		try {		
							
			pInclusao.setString(1, categoria.getNm_categoria());
			pInclusao.setString(2, categoria.getDescricao());
			pInclusao.setInt(3, categoria.getIdcategoria());
			
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
	
	public ResultSet carregarCategoria() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".categoria order by idcategoria";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
	
	public ArrayList<Categoria> carregarCategoriaMenu() {
        ArrayList<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT idcategoria, nm_categoria FROM " + this.schema + ".categoria ORDER BY idcategoria";

        ResultSet tabela = conexao.query(sql);

        try {
            while (tabela.next()) {
            	int  idcategoria = tabela.getInt("idcategoria");
                String nomeCategoria = tabela.getString("nm_categoria");
                Categoria categoria = new Categoria(idcategoria, nomeCategoria);
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }
}

