package com.serratec.uteis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

import com.serratec.classes.Endereco;

public class Util {
	public static Scanner in = new Scanner(System.in);
	public static final String CABECALHO = "SISTEMA BIBLIOTECA NACIONAL";
	public static final String LINHA = "----------------------------------";
	public static final String LINHAD = "==================================";
	
	public enum CRUD {
		CADASTRAR,
		ALTERAR,
		EXCLUIR,
		IMPRIMIR
	}
	
	public static void br() {
		System.out.println("");
	}
	
	public static void escrever(String mensagem) {
		System.out.println(mensagem);
	}
	
	public static LocalDate validarData(String mensagem) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataConvertida = null;
		String sData; 
		boolean dataValidada = false;
		
		do {
			System.out.println(mensagem);
			sData = in.nextLine();
			sData = in.nextLine();
			try {
				dataConvertida = LocalDate.parse(sData, dtf);   
				dataValidada = true;
				return dataConvertida;
			} catch (Exception e) {
				System.out.println("Data invalida");
				return null;
			}
			
		} while (!dataValidada);
	}
	
 	public static int validarInteiro(String mensagem) {
		int numero = 0;
		boolean validado = false;		
		
		do {
			try {
				System.out.print(mensagem);
				String s = in.next();
				numero = Integer.parseInt(s);
				validado = true;
			} catch (Exception e) {
				System.out.println("Informe um numero valido - " + e.getMessage());
			}
		} while (!validado);
		
		return numero;
	}
	
	public static double validarDouble(String mensagem) {
		double numero = 0.0;
		boolean validado = false;
		
		do {
			try {
				System.out.print(mensagem);
				String s = in.next();			
				numero = Double.parseDouble(s);
				validado = true;
			} catch (Exception e) {
				System.out.println("Informe um numero valido - " + e.getMessage());
			}
		} while (!validado);
		
		return numero;
	}
	
}
