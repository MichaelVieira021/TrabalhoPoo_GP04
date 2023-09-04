package com.serratec.classes;

public class Produto {
	private int idproduto;
	private String nome;
	private String descricao;
	private double vl_unitario;
	private static int qtd_estoque;
	//private Categoria categoria;

	
	public void dadosProdutos() {
		Categoria teste = new Categoria();
		System.out.println("Produto: " + this.getNome());
		System.out.println("Descrição: " + this.getDescricao());
		System.out.println("Valor: " + this.getVl_unitario());
		System.out.println("Quantidade: " + this.getQtd_estoque());
		System.out.println("Categoria: " + teste.getNome());
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public int getIdproduto() {
		return idproduto;
	}


	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public double getVl_unitario() {
		return vl_unitario;
	}


	public void setVl_unitario(double vl_unitario) {
		this.vl_unitario = vl_unitario;
	}


	public int getQtd_estoque() {
		return qtd_estoque;
	}


	public void setQtd_estoque(int qtd_estoque) {
		Produto.qtd_estoque = qtd_estoque;
	}	
}
