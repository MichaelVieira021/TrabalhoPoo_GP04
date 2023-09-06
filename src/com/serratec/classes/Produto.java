package com.serratec.classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
		//Categoria teste = new Categoria();
		System.out.println("Produto: " + this.getNome());
		System.out.println("Descrição: " + this.getDescricao());
		System.out.println("Valor: " + this.getVl_venda());
		System.out.println("Quantidade: " + this.getQtd_estoque());
		System.out.println("Categoria: " + this.getIdcategoria());
	}
	
	public void alterarProduto() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("Alteracao de dados");
		System.out.println("------------------------------------");
		System.out.println("");
		System.out.println("Nome: ");
		String s = in.nextLine();
		
		this.setNome(s);
		
		System.out.println("Descrição: ");
		s = in.nextLine();
		
		this.setDescricao(s);
	
		
		System.out.println("Valor Custo: ");
		double n = in.nextDouble();
		
		this.setVl_custo(n);
		
		this.setVl_venda(n*1.15);
		
		System.out.println("Quantidade Estoque ");
		int i= in.nextInt();
		
		this.setQtd_estoque(i);
		
		System.out.println("Id da categoria: ");
		int id = in.nextInt();
		
		this.setIdcategoria(id);
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
