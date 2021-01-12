package com.unicred.dao.SimpleJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Configuracao {

	public static int maxlinhas = 10;

	// BANCO ESTA NA NUVEM COM ACESSO PELA INTERNET
	private static String PG_Banco = "jdbc:postgresql://200.150.197.47:5432/postgres";
	private static String PG_Driver = "org.postgresql.Driver";
	private static String PG_Usuario = "webadmin";
	private static String PG_Senha = "OJiqWUq3oc";

	// ********************************************
	// Controle da conexao com o BD
	// *******************************************
	private static Configuracao instancia = null;
	private static Connection con = null; 


	// construtor
	private Configuracao() {
		inicializa();
	}

	static synchronized public Configuracao getInstancia() {
		if (instancia == null) // se ainda  não foi instanciado
		{
			instancia = new Configuracao();
		}

		try {
			if (con.isClosed()) {
				instancia = new Configuracao();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		// d� problema quando perde a conex�o com o banco de dados, instancia fica <> de null, mas sem conex�o...a� todos perdem a conex�o
		return instancia;
	}

	public void inicializa() {
		try {
			Class.forName(PG_Driver);
			con = DriverManager.getConnection(PG_Banco, PG_Usuario, PG_Senha);

		} catch (Exception e) {
			mensagem = "Erro ao Conectar ao BD. Erro: " + e.getMessage();
		}

	}

	public synchronized Connection getConexao() throws SQLException {

		return con;
	}
	// *******************************************
	// Controle da conex�o com o BD
	// *******************************************
	private static String mensagem = "";

	public static String getMensagem() {
		return mensagem;
	}

	public static String getIB_Banco() {
		return PG_Banco;
	}

	public static String getIB_Driver() {
		return PG_Driver;
	}

	public static String getIB_Usuario() {
		return PG_Usuario;
	}

	public static String getIB_Senha() {
		return PG_Senha;
	}
}
