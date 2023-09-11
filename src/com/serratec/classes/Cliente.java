package com.serratec.classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
	
	public void dadosPessoa() {
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 			
		
		System.out.println("");
		System.out.println("Dados-------------------------------");
		System.out.printf("Nome: %s%n", this.nome);
		
		if (dt_nascimento != null)
			//System.out.printf("Data nasc.: %d%n", dt_nascimento.toString().formatted(dtf));
			System.out.println("Data nasc.: VAI TER SUA DATA AQUI");
		else
			System.out.printf("Data nasc.: %n");
		
		System.out.printf("CPF: %s%n", this.cpf);
		System.out.printf("Email: %s%n", this.email);
		System.out.println("------------------------------------");
	}
	
	public void alterarCliente() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("Alteracao de dados");
		System.out.println("------------------------------------");
		System.out.println("");
		System.out.println("Nome: ");
		String s = in.nextLine();
		
		if (!s.isEmpty() && !s.isBlank() && s != null)
			this.nome = s;
		
		System.out.println("CPF: ");
		s = in.nextLine();
		
		if (!s.isEmpty() && !s.isBlank() && s != null)
			this.cpf = s;
	
		System.out.println("Digite seu EMAIL: ");
		s = in.nextLine();
		
		if (!s.isEmpty() && !s.isBlank() && s != null)
			this.email = s;
		
		System.out.println("Digite seu TELEFONE: ");
		s = in.nextLine();
		
		if (!s.isEmpty() && !s.isBlank() && s != null)
			this.telefone = s;
		
		System.out.println("Digite seu CEP: ");
		s = in.nextLine();
		
		if (!s.isEmpty() && !s.isBlank() && s != null)
			this.endereco = com.serratec.uteis.BuscarCEP.buscarCep(s);
		
		System.out.println("Data nascimento (dd/MM/yyyy): ");
		s = in.nextLine();
		
		if (!s.isEmpty() && !s.isBlank() && s != null) {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyy"); 
			
			this.dt_nascimento = LocalDate.parse(s, formato);
		}
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
