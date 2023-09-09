package com.serratec.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.serratec.classes.Pedido;
import com.serratec.conexao.Conexao;

public class PedidoDAO {
	private Conexao conexao;
	private String schema;
	
	PreparedStatement pInclusao;
	PreparedStatement pAlteracao;
	
	public PedidoDAO(Conexao conexao, String schema) { 
		this.conexao = conexao;
		this.schema = schema;
		prepararSqlInclusao();
		prepararSqlAlteracao();
	}
	
	//------------------------------------------------------------
	private void prepararSqlInclusao() {
		String sql = "insert into "+ this.schema + ".pedido";	
		sql += " (dt_emissao,idcliente)";
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
		String sql = "update "+ this.schema + ".pedido";	
		sql += " set dt_emissao = ?,";		
		sql += " idcliente = ?,";
		sql += " where idpedido = ?";
		
		try {
			this.pAlteracao =  conexao.getC().prepareStatement(sql);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	//------------------------------------------------------------
	public int incluirPedido(Pedido pedido) {
		try {		
							
			pInclusao.setDate(1, Date.valueOf(pedido.getDt_emissao()));
			pInclusao.setInt(2, pedido.getIdcliente());
		
			
			
			return pInclusao.executeUpdate();
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("is null")) {
				System.err.println("\nPedido não incluido.\nVerifique se foi chamado o conect:\n" + e);				
			} else {				
				System.err.println(e);
				e.printStackTrace();
			}
			return 0;
		}
	}
	
	public ResultSet carregarPedido() {
		ResultSet tabela;				
		String sql = "select * from " + this.schema + ".pedido order by idpedido";
		
		tabela = conexao.query(sql);
		
		return tabela;
	}
	
	public ArrayList<Pedido> carregarPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT idpedido, dt_emissao, idcliente FROM " + this.schema + ".pedido ORDER BY idproduto";

        ResultSet tabela = conexao.query(sql);

        try {
            while (tabela.next()) {
                int  idpedido = tabela.getInt("idpedido");
                LocalDate dtEmissao = tabela.getDate("dt_emissao").toLocalDate();
                int idcliente = tabela.getInt("idcliente");
                Pedido pedido = new Pedido();
                pedido.setIdpedido(idpedido);
                pedido.setDt_emissao(dtEmissao);
                pedido.setIdcliente(idcliente);
                //Produto produto = new Produto(idProduto, nomeProduto, qtdEstoque);
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
    
	public int ultimoIdPedido() {
	    int ultimoIdPedido = -1;

	    String sql = "select MAX(idpedido) from " + this.schema + ".pedido";

	    try (PreparedStatement preparedStatement = conexao.getC().prepareStatement(sql);
	         ResultSet resultSet = preparedStatement.executeQuery()) {

	        if (resultSet.next()) {
	            ultimoIdPedido = resultSet.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ultimoIdPedido;
	}
}
