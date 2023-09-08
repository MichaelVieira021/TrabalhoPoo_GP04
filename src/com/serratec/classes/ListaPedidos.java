package com.serratec.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.serratec.conexao.Conexao;
import com.serratec.dao.PedidoDAO;

public class ListaPedidos {
	private Conexao con;
	private String schema;
	private ArrayList<Pedido> listapedidos = new ArrayList<>();
	
	public ListaPedidos(Conexao con, String schema) {
		this.con = con;
		this.schema = schema;
		
		carregarListaPedidos();
		
	}

	
 	public ArrayList<Pedido> getListapedidos() {
		return listapedidos;
	}

	public void setListapedidos(ArrayList<Pedido> listapedidos) {
		this.listapedidos = listapedidos;
	}



	private void carregarListaPedidos() {
 		PedidoDAO peddao = new PedidoDAO(con, schema);
		
		ResultSet tabela = peddao.carregarPedido();
		this.listapedidos.clear();
		
		try {
			tabela.beforeFirst();
			
			while (tabela.next()) {							
				this.listapedidos.add(dadosPedido(tabela));				
			}
			
			tabela.close();
		
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
 	
	private Pedido dadosPedido(ResultSet tabela) { 
		Pedido p = new Pedido();
		String dtEmissao; 

		try {
			dtEmissao = tabela.getString("dt_emissao");
			if (dtEmissao != null)
				p.setDt_emissao(LocalDate.parse(dtEmissao));
			p.setIdcliente(tabela.getInt("idcliente"));
			p.setIdpedido(tabela.getInt("idpedido"));
			
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
}
