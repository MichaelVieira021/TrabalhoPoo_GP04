package com.serratec.classes;

public class Cliente extends Pessoa{
	private int idcliente;
	private String email;
	private String telefone;
	private String endereco;
	
	public Cliente() {}

	public Cliente(int idcliente, String nome) {
		this.setIdcliente(idcliente);
		this.setNome(nome);
	}
	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
