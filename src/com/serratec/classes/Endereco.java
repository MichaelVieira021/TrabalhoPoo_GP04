package com.serratec.classes;

public class Endereco {
	private int idendereco;
	private String cep;
	private String tipo_logra;
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
	private String uf;
	private String complemento;
	
	public Endereco(String cep, String tipo_logra, String logradouro, String numero, String bairro,
			String cidade, String uf, String complemento) {
		super();
		this.cep = cep;
		this.tipo_logra = tipo_logra;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.complemento = complemento;
	}
	public int getIdendereco() {
		return idendereco;
	}
	public void setIdendereco(int idendereco) {
		this.idendereco = idendereco;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getTipo_logra() {
		return tipo_logra;
	}
	public void setTipo_logra(String tipo_logra) {
		this.tipo_logra = tipo_logra;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	
}
