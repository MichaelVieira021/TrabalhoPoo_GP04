package com.serratec.dao;

import java.sql.Date;
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
		sql += " idproduto = ?,";		
		sql += " where idpedido = ? and idproduto = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	

	
	public ResultSet carregarProdutoCarrinho() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".pedido_produto order by idpedido_produto";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
	
	
	public ResultSet alterarProdutoCarrinho () {
		
		ArrayList <ProdutoCarrinho> pdc = new ArrayList <> ();
		
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".pedido_produto order by idpedido_produto";
		
		tabela = conexao.query(sql);
		
		return tabela;
		
	}
	
	public ArrayList <ProdutoCarrinho> carregarProdutoMenu2(int id) {
		ArrayList <ProdutoCarrinho> produtoC = new ArrayList<>();
		
        String sql = "SELECT idpedido, idproduto, quantidade FROM " + this.schema + ".pedido_produto where idpedido = "+id;

        ResultSet tabela = conexao.query(sql);
        ProdutoCarrinho teste = new ProdutoCarrinho();

        try {
            while (tabela.next()) {
            	int  idPedido = tabela.getInt("idpedido");
            	int  idProduto = tabela.getInt("idproduto");
            	int  qtd = tabela.getInt("quantidade");                
            	teste.setIdpedido(idPedido);
            	teste.setIdproduto(idProduto);
            	teste.setQuantidade(qtd);
            	
            	produtoC.add(teste);
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtoC;
    }
	
}
