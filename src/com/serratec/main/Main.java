package com.serratec.main;

import com.serratec.classes.Cliente;
import com.serratec.classes.Empresa;
import com.serratec.conexao.Conexao;
import com.serratec.conexao.DadosConexao;
import com.serratec.uteis.Util;
import com.serratec.dao.CreateDAO;
import com.serratec.dao.ClienteDAO;

public class Main {
	public static Empresa g4Tech;
	public static Conexao con;
	public static DadosConexao dadosCon = new DadosConexao();
	public static final String BANCO = "trabalhopooteste";
	public static final String SCHEMA = "grupo04";
	public static final String LOCAL = "localhost";
	public static final String USUARIO = "postgres";
	public static final String SENHA = "123456";
	public static final String PORTA = "5432";
	public static final String BD = "PostgreSql";
	
	public static void main(String[] args) {
		
		dadosCon.setBanco(BANCO);
		dadosCon.setLocal(LOCAL);
		dadosCon.setUser(USUARIO);
		dadosCon.setSenha(SENHA);
		dadosCon.setPorta(PORTA);
		dadosCon.setBd(BD);
		
		if (CreateDAO.createBD(BANCO, SCHEMA, dadosCon)) {
			con = new Conexao(dadosCon); 
			con.conect();
			//g4Tech = new Empresa(con, SCHEMA);
			menuPrincipal();
		} else {
			System.out.println("Ocorreu um problema na criacao do banco de dados");
		}
		
	}
		
		public static void menuPrincipal() {
			
			int opcao = 0;
			
			do {	
				System.out.println("╔═══════════════════════════════════╗");
				System.out.println("║                MENU               ║");
				System.out.println("║-----------------------------------║");
				System.out.println("║                                   ║");
				System.out.println("║        [1] - Cadastrar            ║");
				System.out.println("║        [2] - Alterar              ║");
				System.out.println("║        [3] - Excluir              ║");
				System.out.println("║        [4] - Localizar            ║");
				System.out.println("║                                   ║");
				System.out.println("║-----------------------------------║");
				System.out.println("║      Digite '4' para [Sair]       ║");
				System.out.print("╚═══════════════════════════════════╝\n> ");
				
				opcao = Util.validarInteiro("Informe uma opcao: ");
				
				escolherMenu(opcao);
				
			} while (opcao != 0);
	}
		
		public static void escolherMenu(int opcao) {
			
			switch (opcao) {
			case 1: menuCadastrar(); break;
			//case 2: menuAlterar(); break;
			//case 3: menuLocalizar(); break;
			//case 4: menuExcluir(); break;
			case 0: 
				System.out.println("\nPrograma finalizado.");	
				break;
			default: System.out.println("Opcao invalida");
			}
		}
		
		public static void menuCadastrar() {
			menuPadrao(Util.CRUD.CADASTRAR);			
		}
		
		public static int menuPadrao(Util.CRUD tipoCrud) {
			int opcao = 0;
			
			do {
				Util.escrever(Util.LINHA);
				
				switch (tipoCrud) {
				case CADASTRAR: Util.escrever("CADASTRAR"); break;
				case ALTERAR: Util.escrever("ALTERAR"); break;
				case IMPRIMIR: Util.escrever("IMPRIMIR"); break;
				case EXCLUIR: Util.escrever("EXCLUIR"); break;
				}
						
				Util.escrever(Util.LINHA);
				Util.escrever("[1]- Cliente");
				Util.escrever("[2]- Produto");
				Util.escrever("[3]- Pedidos");
				Util.escrever("[0]- Voltar");
				Util.escrever(Util.LINHA);
				
				opcao = Util.validarInteiro("Informe uma opcao: ");			
				
				switch (tipoCrud) {
				case CADASTRAR: escolherMenuCadastrar(opcao); break;
				//case ALTERAR: escolherMenuAlterar(opcao); break;
				//case IMPRIMIR: escolherMenuImprimir(opcao); break;
				//case EXCLUIR: escolherMenuExcluir(opcao); break;
				}
				
			} while (opcao != 0);
			
			return opcao;
		}
		
		public static void escolherMenuCadastrar(int opcao) {		
			switch (opcao) {
			case 1: cadastrarCliente(); break;
			//case 2: cadastrarFuncionario(); break;
			//case 3: cadastrarLivro(); break;
			case 0: break;
			default: Util.escrever("Opcao invalida");
			}
		}
		
		public static void cadastrarCliente() {
			Cliente c = new Cliente();
			ClienteDAO cdao = new ClienteDAO(con, SCHEMA);
			
			c = g4Tech.cadastrarCliente();		
			
			int retorno = cdao.incluirCliente(c);
			
			if (retorno > 0) {
				Util.escrever("Cliente criado com sucesso.");
				g4Tech.adicionarCliente(c);
			}
		}

}
