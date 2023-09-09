package com.serratec.classes;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido {
	private int idpedido;
	private LocalDate dt_emissao;
	private int idcliente;
	private ArrayList<Produto> pedidoitem = new ArrayList<>();

	public void dadosPedidos() {
		System.out.println("ID Pedido: " + this.getIdcliente());
		System.out.println("Emissao: " + this.getDt_emissao());
		System.out.println("ID Cliente: " + this.getIdcliente());
	}
	
	public void alterarPedido(){
		
	}
	
	public int getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}

	public LocalDate getDt_emissao() {
		return dt_emissao;
	}

	public void setDt_emissao(LocalDate dt_emissao) {
		this.dt_emissao = dt_emissao;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public ArrayList<Produto> getPedidoitem() {
		return pedidoitem;
	}

	public void setPedidoitem(ArrayList<Produto> pedidoitem) {
		this.pedidoitem = pedidoitem;
	}


}