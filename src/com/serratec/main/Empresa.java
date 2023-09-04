package com.serratec.main;

import java.util.Scanner;
import com.serratec.uteis.Util;
//import com.serratec.classes.Cliente;
import java.util.ArrayList;

public class Empresa {
	private static final String nome = "g4Tech";
	private String cnpj = "34554354";
	
	private ArrayList<com.serratec.classes.Cliente> cliente = new ArrayList<>();
	
 	public com.serratec.classes.Cliente cadastrarCliente() {
 		com.serratec.classes.Cliente c = new com.serratec.classes.Cliente();
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println(Util.LINHA);
		System.out.println("Cadastro de cliente: ");
		System.out.println(Util.LINHA);
		
		Util.br();
		
		System.out.println("Informe o nome: ");
		String s = in.nextLine();
		c.setNome(s);
		
		System.out.println("Informe o CPF: ");
		s = in.nextLine();
		c.setCpf(s);
		
		c.setDt_nascimento(Util.validarData("Informe a data de nascimento (dd/MM/yyyy): "));
		
		System.out.println("Informe o Email: ");
		s = in.nextLine();
		c.setEmail(s);
		
		System.out.println("Informe o Telefone: ");
		s = in.nextLine();
		c.setTelefone(s);
	
		System.out.println("Informe o endereco: ");
		s = in.nextLine();
		//c.setEndereco(s);
		
		//in.close();
		
		return c;
	}
 	
 	public com.serratec.classes.Cliente adicionarCliente(com.serratec.classes.Cliente cliente) {
 		com.serratec.classes.Cliente c = new com.serratec.classes.Cliente();
		
		c.setNome(cliente.getNome());
		c.setCpf(cliente.getCpf());
		c.setDt_nascimento(cliente.getDt_nascimento());
		c.setEmail(cliente.getEmail());
		c.setTelefone(cliente.getTelefone());
		c.setEndereco(cliente.getEndereco());
		
		this.cliente.add(c);
		
		return cliente;
	}	
}
