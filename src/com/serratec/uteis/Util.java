package com.serratec.uteis;

import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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
	
	public void preencherEspaco(String teste) {
		for(int i = 0; i < teste.length(); i++) {
			
		}
	}
	
	   public static String preencherEspacos(int tamanho, String texto) {
	        StringBuilder resultado = new StringBuilder(texto);
	        
	        int preencher = tamanho - texto.length();
	        
	        for (int i = 0; i < preencher; i++) {
	            resultado.append(" ");
	        }
	        
	        //resultado.append("║");
	        
	        return resultado.toString();
	    }
	
	public static LocalDate validarData(String mensagem) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataConvertida = null;
		String sData; 
		boolean dataValidada = false;
		
		do {
			System.out.println(mensagem);
			sData = in.next();
			try {
				dataConvertida = LocalDate.parse(sData, dtf);   
				dataValidada = true;
				return dataConvertida;
			} catch (Exception e) {
				System.out.println("Data invalida");
			}
			
		} while (!dataValidada);
		return null;
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
				System.err.println("Informe um numero valido - " + e.getMessage());
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
	
    public static String validarCPF() {
        String cpf;
        do {
            System.out.println("Informe o CPF: ");
            cpf = in.next();
            if (!verificarNumerico(cpf) || cpf.length() != 11) {
                System.err.println("Erro: CPF inválido. Por favor, digite um CPF com 11 dígitos numéricos.");
            }
        } while (!verificarNumerico(cpf) || cpf.length() != 11);
        return cpf;
    }
    
    public static boolean verificarNumerico(String str) {
        return str.matches("\\d+");
    }
    
    public static String buscarCep(){
    	String cep;
    	String logradouro;
    	String bairro;
    	String cidade;
    	String uf;
    	String endereco;
    	String numero_residencia = "";
    	String complemento = "";
        String json;        
        
	    do {
	        System.out.println("Informe o CEP: ");
			cep = in.next();
	        if (!Util.verificarNumerico(cep) || cep.length() != 8) {
	            System.err.println("ERRO: CEP inválido. Por favor, digite um CEP com 8 dígitos numéricos.");
	        }
	    } while (!Util.verificarNumerico(cep) || cep.length() != 8);
		
        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));
            json = jsonSb.toString();
            json = json.replaceAll("[{},:]", "");
            json = json.replaceAll("\"", "\n");                       
            String array[] = new String[30];
            array = json.split("\n");
            
         	System.out.print("Número da residência:");
         	numero_residencia = in.next();
         	
         	System.out.print("Complemento: ");
         	complemento = in.nextLine();
         	complemento = in.nextLine();
            
            logradouro = array[7];            
            bairro = array[15];
            cidade = array[19]; 
            uf = array[23];
            
            if(complemento == "") {
                endereco = logradouro + ", " + numero_residencia + " - " + bairro + " - " + 
             		   cidade + " - " + uf;
            }else {
            	endereco = logradouro + ", " + numero_residencia + " - " + bairro + " - " + 
            			cidade + " - " + uf + " - Complemento: " + complemento;
            }
            
            return endereco;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }

	
}
