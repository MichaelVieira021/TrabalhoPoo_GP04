package com.serratec.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.serratec.classes.Endereco;
import com.serratec.conexao.Conexao;

public class EnderecoDAO {

	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	PreparedStatement pExclusao;
	
	public EnderecoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		//prepararSqlAlteracao();
		//prepararSqlExclusao();
	}
	
	/*private void prepararSqlExclusao() {
		String sql = "delete from "+ this.schema + ".endereco";
		sql += " where idendereco = ?";
		
		try {
			this.pExclusao = conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}*/
	
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".endereco";	
		sql += " (cep, tipo_logra, logradouro, numero, bairro, cidade, uf, complemento)";
		sql += " values ";
		sql += " (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			this.pInclusao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	/*private void prepararSqlAlteracao() {
		String sql = "update "+ this.schema + ".endereco";	
		sql += " set nome = ?,";
		sql += " cpf = ?,";
		sql += " email = ?,";
		sql += " telefone = ?,";
		sql += " dt_nascimento = ?";
		sql += " endereco = ?";
		sql += " where idcliente = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}*/
	
	public int alterarEndereco(Endereco endereco) {
		try {
			pAlteracao.setString(1, endereco.getCep());
			pAlteracao.setString(2, endereco.getTipo_logra());
			pAlteracao.setString(3, endereco.getLogradouro());
			pAlteracao.setString(4, endereco.getNumero());
			pAlteracao.setString(5, endereco.getBairro());
			pAlteracao.setString(6, endereco.getCidade());
			pAlteracao.setString(7, endereco.getUf());
			pAlteracao.setString(8, endereco.getComplemento());
			pAlteracao.setInt(9, endereco.getIdendereco());	
			return pAlteracao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nCliente nao alterado.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public int incluirEndereco(Endereco endereco) {
		try {							
			pInclusao.setString(1, endereco.getCep());
			pInclusao.setString(2, endereco.getTipo_logra());
			pInclusao.setString(3, endereco.getLogradouro());
			pInclusao.setString(4, endereco.getNumero());
			pInclusao.setString(5, endereco.getBairro());
			pInclusao.setString(6, endereco.getCidade());
			pInclusao.setString(7, endereco.getUf());
			pInclusao.setString(8, endereco.getComplemento());
			//pInclusao.setInt(9, cliente.getIdcliente());	
			
			return pInclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nCliente nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	/*public int excluirEndereco(Endereco endereco) {
		try {
			//pExclusao.setInt(1, cliente.getIdcliente());
			
			return pExclusao.executeUpdate();
		} catch  (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nEndereco nao incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}*/
	
	@SuppressWarnings("exports")
	public ResultSet carregarEndereco() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".endereco order by idendereco";
		
		tabela = conexao.query(sql);
			
		return tabela;
	}
}
