package com.serratec.uteis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class BuscarCEP {
	public static Scanner in = new Scanner(System.in);

    public static String buscarCep(String cep){
    	String logradouro;
    	String bairro;
    	String cidade;
    	String uf;
    	String endereco;
    	String numero_residencia = "";
    	String complemento = "";
        String json;        
        
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
         	numero_residencia = in.nextLine();
         	
         	System.out.print("Complemento: ");
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
