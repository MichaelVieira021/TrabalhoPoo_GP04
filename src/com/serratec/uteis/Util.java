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
				System.out.println(mensagem);
				String s = in.nextLine();
				numero = Integer.parseInt(s);
				validado = true;
			} catch (Exception e) {
				System.out.println("Informe um numero valido - " + e.getMessage());
			}
		} while (!validado);
		
		//in.close();
		
		return numero;
	}
	
	public static double validarDouble() {
		String s;
		double numero = 0.0;
		boolean validado = false;
		Scanner in = new Scanner(System.in);
		
		do {
			try {
				s = in.next();			
				numero = Double.parseDouble(s);
				validado = true;
			} catch (Exception e) {
				System.out.println("Informe um numero valido - " + e.getMessage());
			}
		} while (!validado);
		
		in.close();
		
		return numero;
	}
	
	//função para buscar cep
    public static String buscarCep() 
    {
    	//PROCURAR CEP
    	String logradouro;
    	String tipo_logradouro = null;
    	String bairro;
    	String cidade;
    	String uf;
    	String endereco;
    	String numero_residencia = "";
    	String complemento = "";
        String json;        
        String cep;
        
		System.out.println("Informe o CEP: ");
		cep = in.nextLine();
        
        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));
            json = jsonSb.toString();
            
            // JOptionPane.showMessageDialog(null, json);
            
            json = json.replaceAll("[{},:]", "");
            json = json.replaceAll("\"", "\n");                       
            String array[] = new String[30];
            array = json.split("\n");
            
            // JOptionPane.showMessageDialog(null, array);
         	System.out.println("Qual é o número da residência?");
         	validarInteiro(numero_residencia);
         	System.out.println("Qual é o tido do logradouro?");
         	tipo_logradouro = in.nextLine();
         	System.out.println("Algum complemento?");
         	complemento = in.nextLine();
            
            logradouro = array[7];            
            bairro = array[15];
            cidade = array[19]; 
            uf = array[23];
            
            //criar endereco
            Endereco novoEndereco = new Endereco(cep, tipo_logradouro, logradouro, 
            							numero_residencia, bairro, cidade, uf, complemento);
            endereco ="(" + tipo_logradouro + ") " + logradouro + ", " + numero_residencia + 
            		" - " + bairro + "\n" + cidade + " - " + uf + " - Complemento: " + complemento;
            System.out.println(Arrays.toString(array));
            //JOptionPane.showMessageDialog(null, logradouro + " " + bairro + " " + cidade + " " + uf);
			//System.out.println(logradouro + " " + bairro + " " + cidade + " " + uf);
            return endereco;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
