package com.serratec.classes;

import java.time.LocalDate;

public class Pedido {
	private int idpedido;
	private LocalDate dt_emissao;
	private int idcliente;

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

	


}