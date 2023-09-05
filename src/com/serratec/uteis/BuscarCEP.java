package com.serratec.uteis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Scanner;

import com.serratec.classes.Endereco;
import com.serratec.dao.EnderecoDAO;
import com.serratec.main.Main;

public class BuscarCEP {
	public static Scanner in = new Scanner(System.in);

	//função para buscar cep
    public static String buscarCep(String cep) 
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
         	//validarInteiro(numero_residencia);
         	numero_residencia = in.nextLine();
         	System.out.println("Qual é o tido do logradouro?");
         	tipo_logradouro = in.nextLine();
         	System.out.println("Algum complemento?");
         	complemento = in.nextLine();
            
            logradouro = array[7];            
            bairro = array[15];
            cidade = array[19]; 
            uf = array[23];
            
            endereco ="(" + tipo_logradouro + ") " + logradouro + ", " + numero_residencia + 
            		" - " + bairro + " - " + cidade + " - " + uf + " - Complemento: " + complemento;
            
            // DESCONTINUADO: CRIAVA NOVO ENDEREÇO NA CLASSE Endereco
            //criar endereco
            /*Endereco novoEndereco = new Endereco(cep, tipo_logradouro, logradouro, 
            							numero_residencia, bairro, cidade, uf, complemento);
            int retorno = com.serratec.main.Main.cadastrarEndereco().incluirEndereco(novoEndereco);
            com.serratec.classes.Empresa.adicionarEndereco(novoEndereco);*/
            
            //VERIFICAÇÕES PARA VER SE ESSA JOÇA ESTA FUNCIONANDO
            //System.out.println(Arrays.toString(array));
            //JOptionPane.showMessageDialog(null, logradouro + " " + bairro + " " + cidade + " " + uf);
			//System.out.println(logradouro + " " + bairro + " " + cidade + " " + uf);
            //System.out.println(endereco);
            
            return endereco;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
}
