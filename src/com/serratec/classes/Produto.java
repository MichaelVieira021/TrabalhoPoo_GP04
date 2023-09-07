package com.serratec.classes;

public class Produto {
	private int idproduto;
	private String nome;
	private String descricao;
	private double vl_custo;
	private double vl_venda;
	private static int qtd_estoque;
	private int idcategoria;

	public Produto() {}

	public Produto(int idproduto, String nome) {
		this.setIdcategoria(idproduto);
		this.setNome(nome);
	}
	public void dadosProdutos() {
		Categoria teste = new Categoria();
		System.out.println("Produto: " + this.getNome());
		System.out.println("Descrição: " + this.getDescricao());
		System.out.println("Valor: " + this.getVl_venda());
		System.out.println("Quantidade: " + this.getQtd_estoque());
		System.out.println("Categoria: " + teste.getNm_categoria());
	}


	public int getIdcategoria() {
		return idcategoria;
	}


	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
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

	public double getVl_custo() {
		return vl_custo;
	}


	public void setVl_custo(double vl_custo) {
		this.vl_custo = vl_custo;
	}


	public double getVl_venda() {
		return vl_venda;
	}


	public void setVl_venda(double vl_venda) {
		this.vl_venda = getVl_custo() * 1.15;
	}


	public int getQtd_estoque() {
		return qtd_estoque;
	}


	public void setQtd_estoque(int qtd_estoque) {
		Produto.qtd_estoque = qtd_estoque;
	}	
}
