package com.serratec.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import com.serratec.conexao.Conexao;
import com.serratec.conexao.DadosConexao;

public class CreateDAO {
	private static Conexao conexao;	
	
	public static boolean createBD(String bd, String schema, DadosConexao dadosCon) {		
		boolean bdCriado = false;
		conexao = conectar("postgres", dadosCon);
		
		if (criarDatabase(conexao, bd)) {
			desconectar(conexao);
			
			conexao = conectar(bd, dadosCon);
			
			if (criarSchema(conexao, schema)) {
				criarEntidadeCliente(conexao, schema);
				criarEntidadeEndereco(conexao, schema);
				criarEntidadeCategoria(conexao, schema);
				criarEntidadeProduto(conexao, schema);
				criarEntidadePedido(conexao, schema);
				criarEntidadePedidoItem(conexao, schema);
				
				bdCriado = true;
			}
		}
		desconectar(conexao);
		
		return bdCriado;
	}
	
	//CONEXÃO--------------------------------------------------------------------------------
	private static Conexao conectar(String bd, DadosConexao dadosCon) {		
		dadosCon.setBanco(bd);
		Conexao conexao = new Conexao(dadosCon);
		conexao.conect();
		return conexao;
	}
	
	private static void desconectar(Conexao con) {
		con.disconect();
	}
	
	//---------------------------------------------------------------------------------------
	private static boolean criarDatabase(Conexao con, String bd) {		
		boolean bdExiste;
		int tentativas = 1;
		String sql;
				
		class Database {		
			public static ResultSet Exists(Conexao con, String bd) {
				ResultSet entidade;
				String sql = "select datname from pg_database where datname = '" + bd + "'";		
				entidade = con.query(sql);
				return entidade;
			}
		}
				
		do {
			try {
				bdExiste = Database.Exists(con, bd).next(); 
				
				if (!bdExiste) {
					sql = "create database "+ bd;		
					con.query(sql);
					tentativas++;
				}
			} catch (Exception e) {
				System.err.printf("Nao foi possivel criar o database %s: %s", bd, e);
				e.printStackTrace();
				return false;
			}
		} while (!bdExiste && (tentativas<=3));
		
		return bdExiste;
	}
	
	private static boolean criarSchema(Conexao con, String schema) {		
		boolean schemaExiste;
		int tentativas = 1;
		String sql;
				
		class Schema {		
			public static ResultSet Exists(Conexao con, String schema) {
				ResultSet entidade;
				String sql = "select * from pg_namespace where nspname = '" + schema + "'";		
				entidade = con.query(sql);
				return entidade;
			}
		}
				
		do {
			try {
				schemaExiste = Schema.Exists(con, schema).next(); 
				
				if (!schemaExiste) {
					sql = "create schema "+ schema;		
					con.query(sql);
					tentativas++;
				}
			} catch (Exception e) {
				System.err.printf("Nao foi possivel criar o schema %s: %s", schema, e);
				e.printStackTrace();
				return false;
			}
		} while (!schemaExiste && (tentativas<=3));
		
		return schemaExiste;
	}
	
	private static void criarTabela(Conexao con, String entidade, String schema) {				
		String sql = "create table " + schema + "." + entidade + " ()";		
		con.query(sql);		
	}
	
	private static void criarCampo(Conexao con, String schema, String entidade, 
			String atributo, String tipoAtributo, boolean primario, 
			boolean estrangeiro, String entidadeEstrangeira, 
			String atributoEstrangeiro) {
		
		if (!atributoExists(con, schema, entidade, atributo)) {
			String sql = "alter table " + schema + "." + entidade + " add column " + 
				atributo + " " + tipoAtributo + " "; 
			
			if (primario) {
				sql += "primary key "; 
			}

			if (estrangeiro) {
				sql += "references " + schema + "." + entidadeEstrangeira + "(" + atributoEstrangeiro + ") on delete cascade";
			}

			con.query(sql);
		}
	}
	
	/*private static void criarChaveComposta(Conexao con, String schema, String entidade, 
			String nomesCamposCompostos ) {
		
		boolean chaveExist = false;
		String sql = "SELECT CONNAME FROM pg_constraint where conname = 'chave_pk'";				
		ResultSet result = con.query(sql);
		
		try {
			chaveExist = (result.next()?true:false);
			
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		if (!chaveExist) {
			sql = "alter table " + schema + "." + entidade + " add constraint chave_pk" +
					" primary key (" + nomesCamposCompostos + ")";
				
		con.query(sql);
		}
	}*/
	

	//CRIAR ENTIDADES --------------------------------------------------------------
	private static void criarEntidadeCliente(Conexao con, String schema) {
		String entidade = "cliente";
		
		if (!entidadeExists(con, schema, entidade))		
			criarTabela(con, entidade, schema);
		
		if (entidadeExists(con, schema, entidade)) {
			criarCampo(con, schema, entidade, "idcliente", "serial"	 	 	, true,  false, null, null);
			criarCampo(con, schema, entidade, "nome"	 , "varchar(100)"	, false, false, null, null);
			criarCampo(con, schema, entidade, "cpf"		 , "varchar(11)" 	, false, false, null, null);
			criarCampo(con, schema, entidade, "email"	 , "varchar(150)"	, false, false, null, null);
			criarCampo(con, schema, entidade, "telefone" , "varchar(16)"	, false, false, null, null);
			criarCampo(con, schema, entidade, "dt_nascimento"	, "date"	, false, false, null, null);
			criarCampo(con, schema, entidade, "endereco"	 	, "text"	, false, false, null, null);
			cadastrarClientes(con, schema, entidade);
		}		
	}
	
	private static void criarEntidadeEndereco(Conexao con, String schema) {
		String entidade = "endereco";
		
		if (!entidadeExists(con, schema, entidade))		
			criarTabela(con, entidade, schema);
		
		if (entidadeExists(con, schema, entidade)) {
			criarCampo(con, schema, entidade, "idendereco"		, "serial"	 	 	, true,  false, null, null);
			criarCampo(con, schema, entidade, "cep"	 			, "varchar(14)"		, false, false, null, null);
			criarCampo(con, schema, entidade, "tipo_logra"		, "varchar(255)" 	, false, false, null, null);
			criarCampo(con, schema, entidade, "logradouro"		, "varchar(255)"	, false, false, null, null);
			criarCampo(con, schema, entidade, "numero" 			, "varchar(16)"		, false, false, null, null);
			criarCampo(con, schema, entidade, "bairro"			, "varchar(255)"	, false, false, null, null);
			criarCampo(con, schema, entidade, "cidade"	 		, "varchar(255)"	, false, false, null, null);
			criarCampo(con, schema, entidade, "uf"	 			, "varchar(2)"		, false, false, null, null);
			criarCampo(con, schema, entidade, "complemento"	 	, "varchar(255)"	, false, false, null, null);
		}		
	}
	
	private static void criarEntidadeCategoria(Conexao con, String schema) {
		String entidade = "categoria";
		
		if (!entidadeExists(con, schema, entidade))		
			criarTabela(con, entidade, schema);
		
		if (entidadeExists(con, schema, entidade)) {
			criarCampo(con, schema, entidade, "idcategoria"			, "serial"	 	 		, true,  false, null, null);
			criarCampo(con, schema, entidade, "nm_categoria"	 	, "varchar(255)"		, false, false, null, null);
			criarCampo(con, schema, entidade, "descricao"			, "text" 				, false, false, null, null);
			cadastrarCategorias(con,schema,entidade);
		}		
	}
	
	private static void criarEntidadeProduto(Conexao con, String schema) {
		String entidade = "produto";
		
		if (!entidadeExists(con, schema, entidade))		
			criarTabela(con, entidade, schema);
		
		if (entidadeExists(con, schema, entidade)) {
			criarCampo(con, schema, entidade, "idproduto"			, "serial"	 	 		, true,  false, null, null);
			criarCampo(con, schema, entidade, "nome"	 			, "varchar(255)"		, false, false, null, null);
			criarCampo(con, schema, entidade, "descricao"			, "text" 				, false, false, null, null);
			criarCampo(con, schema, entidade, "vl_custo"			, "double precision"	, false, false, null, null);
			criarCampo(con, schema, entidade, "vl_venda" 			, "double precision"	, false, false, null, null);
			criarCampo(con, schema, entidade, "qtd_estoque"			, "int"					, false, false, null, null);
			criarCampo(con, schema, entidade, "idcategoria"	 		, "int"					, false, true, "categoria", "idcategoria");
		    cadastrarProdutos(con,schema,entidade);   
		}		
	}
	
	private static void criarEntidadePedido(Conexao con, String schema) {
		String entidade = "pedido";
		
		if (!entidadeExists(con, schema, entidade))		
			criarTabela(con, entidade, schema);
		
		if (entidadeExists(con, schema, entidade)) {			
			criarCampo(con, schema, entidade, "idpedido"			, "serial"	 	 		, true,  false, null, null);
			criarCampo(con, schema, entidade, "dt_emissao"	 		, "date"				, false, false, null, null);
			criarCampo(con, schema, entidade, "idcliente"			, "int" 				, false, true, "cliente", "idcliente");
		
		}		
	}

	private static void criarEntidadePedidoItem(Conexao con, String schema) {
		String entidade = "pedido_produto";
		
		if (!entidadeExists(con, schema, entidade))		
			criarTabela(con, entidade, schema);
		
		if (entidadeExists(con, schema, entidade)) {
			criarCampo(con, schema, entidade, "idpedido_produto"	, "serial" 			, true, false, null, null);
			criarCampo(con, schema, entidade, "idpedido"			, "int"	 	 		, false,  true, "pedido", "idpedido");
			criarCampo(con, schema, entidade, "idproduto"	 		, "int"				, false,  true, "produto", "idproduto");
			criarCampo(con, schema, entidade, "quantidade"			, "int" 			, false, false, null, null);
		
		}		
	}
	
	//---------------------------------------------------------------------------------------
	public static boolean databaseExists(Conexao con, String bd) {
		ResultSet entidade;
		boolean dbExists = false;
		
		String sql = "select datname from pg_database where datname = '" + bd + "'";		
		entidade = con.query(sql);
		
		try {
			dbExists = entidade.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dbExists;
	}

	public static boolean entidadeExists(Conexao con, String schema, String entidade) {
		boolean entidadeExist = false;
		String sql = 
				"SELECT n.nspname AS schemaname, c.relname AS tablename " +
				   "FROM pg_class c " +
				   "LEFT JOIN pg_namespace n ON n.oid = c.relnamespace " +
				   "LEFT JOIN pg_tablespace t ON t.oid = c.reltablespace " +
				"WHERE c.relkind = 'r' " +
				"AND n.nspname = '" + schema + "' " +
				"AND c.relname = '" + entidade + "'";
		
		ResultSet tabela = con.query(sql);
		
		try {
			entidadeExist = (tabela.next()?true:false); 
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return entidadeExist;
	}
	
	public static boolean atributoExists(Conexao con, String schema, 
			String entidade, String atributo) {
		
		boolean atributoExist = false;
		
		String sql = "select table_schema, table_name, column_name from information_schema.columns "
				+ "where table_schema = '" + schema + "' "
				+ "and table_name = '" + entidade + "' "
				+ "and column_name = '" + atributo + "'";
		
		ResultSet result = con.query(sql);
		
		try {
			atributoExist = (result.next()?true:false);
			
		} catch (SQLException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return atributoExist;
	}
	
	
	//---------------------------------------------------------------------------------------
	private static void cadastrarClientes(Conexao con, String schema, String entidade) {
		ResultSet tabela = con.query("select nome from " + schema + "." + entidade + " limit 1");
		
		try {
			if (!tabela.next()) {
				String sqlClientes = "insert into " + schema + "." + entidade;
				sqlClientes += " (nome, cpf, email, telefone, dt_nascimento, endereco)";
				sqlClientes += " values";
				sqlClientes += "('Lucas', '12345678901', 'lucas@mail' , '12345678', '11/11/2011','Rua das Flores'),";
				sqlClientes += "('JP Galvao', '98765432109', 'jp@mail' , '87654321', '01/01/2001','Bolso do Bruno Henrique'),";
				sqlClientes += "('Bruno Lage', '65498732110', 'blage@mail' , '98732165', '22/02/2022','Rua dos Tolos')";
				con.query(sqlClientes);
				tabela.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	private static void cadastrarCategorias(Conexao con, String schema, String entidade) {
		ResultSet tabela = con.query("select nm_categoria from " + schema + "." + entidade + " limit 1");
		
		try {
			if (!tabela.next()) {
				String sqlCategoria = "insert into " + schema + "." + entidade;
				sqlCategoria += " (nm_categoria, descricao)";
				sqlCategoria += " values";
				sqlCategoria += "('Teclados', 'Descrição de Teclados'),";
				sqlCategoria += "('Monitores', 'Descrição de Monitores'),";
				sqlCategoria += "('Mouses', 'Descrição de Mouses')";
				con.query(sqlCategoria);
				tabela.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
    private static void cadastrarProdutos(Conexao con, String schema, String entidade) {
        ResultSet tabela = con.query("select nome from " + schema + "." + entidade + " limit 1");
        
        try {
            if (!tabela.next()) {
                String sqlProduto = "insert into " + schema + "." + entidade;
                sqlProduto += " (nome, descricao, vl_custo, vl_venda, qtd_estoque, idcategoria)";
                sqlProduto += " values";
                sqlProduto += "('Kit Gamer', 'Descrição Kit Gamer', '150', '180', '50', '1'),";
                sqlProduto += "('Monitor Gamer', 'Descrição Monitor gamer', '500', '660', '50', '2'),";
                sqlProduto += "('Mouse Gamer', 'Descrição Mouse gamer', '50', '90', '4', '3'),";
                sqlProduto += "('Webcan Gamer', 'Descrição Webcan gamer', '200', '300', '7', '3'),";
                sqlProduto += "('Teclado Gamer', 'Descrição Teclado gamer', '180', '200', '33', '2'),";
                sqlProduto += "('Mousepad Gamer', 'Descrição Mousepad gamer', '20', '30', '80', '2'),";
                sqlProduto += "('Geladeira Gamer', 'Descrição Geladeira gamer', '4000', '5000', '50', '1'),";
                sqlProduto += "('Monitor Comum', 'Descrição Monitor gamer', '600', '700', '5', '2')";
                con.query(sqlProduto);
                tabela.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }
 
}
