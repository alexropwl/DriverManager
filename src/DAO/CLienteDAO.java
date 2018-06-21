package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CLienteDAO {

	static String usuario = "curso_java";
	static String senha = "schema";
	static String url = "jdbc:oracle:thin:@localhost:1521:XE ";

	static Connection conexao;

	public static void conectar() {

		try {
			conexao = DriverManager.getConnection(url, usuario, senha);
			conexao.setAutoCommit(false);
			DatabaseMetaData meta = conexao.getMetaData();
			System.out.println("CONECTADO AO BANCO =>>> " + meta.getDatabaseProductVersion());

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*********************************************************************************************/
	public static void desconectar() throws SQLException {

		conexao.close();

	}

	/*********************************************************************************************/

	public static void inserir(Cliente cliente) {

		try {
			String sql = "INSERT INTO CLIENTE VALUES (" + cliente.getCpf() + ", '" + cliente.getNome() + "'," + " '"
					+ cliente.getEmail() + "')";
			Statement stm = conexao.createStatement();
			stm.execute(sql);
			conexao.commit();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERRO AO INSERIR NO BANCO!");

		}

	}

	/**
	 * @throws SQLException
	 *******************************************************************************************/

	// INSERE DADOS NO BANCO COM A INTERFACE PREPAREDSTATEMENT
	public static void inserirPS(int cpf, String nome, String email) {

		try {
			String sql = "INSERT INTO CLIENTE VALUES(?,?,?)";
			PreparedStatement pst = conexao.prepareStatement(sql);
			pst.setInt(1, cpf);
			pst.setString(2, nome);
			pst.setString(3, email);

			pst.executeUpdate();

			conexao.commit();
		}

		catch (Exception e) {
			e.printStackTrace();

		}

	}

	/*********************************************************************************************/

	// INSERE DADOS NO BANCO COM STORED PROCEDURE
	public static void inserirSP(int cpf, String nome, String email) {

		try {
			String sql = "{CALL sp_inserircliente(?,?,?)}";
			CallableStatement cs = conexao.prepareCall(sql);
			cs.setInt(1, cpf);
			cs.setString(2, nome);
			cs.setString(3, email);

			cs.execute();
			conexao.commit();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/*********************************************************************************************/

	public static Cliente consultar(int cpf) throws SQLException {

		String sql = "SELECT * FROM CLIENTE WHERE cpf=" + cpf + "";
		Statement stm = conexao.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		Cliente cliente = null;

		while (rs.next()) {

			cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3));

		}

		return cliente;

	}

	/*********************************************************************************************/

	public static List<Cliente> consultartodos() throws SQLException {

		String sql = "SELECT * FROM CLIENTE";
		Statement stm = conexao.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		List<Cliente> clientes = new LinkedList<>();

		while (rs.next()) {
			Cliente cliente = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3));
			clientes.add(cliente);

		}
		return clientes;

	}

	/*********************************************************************************************/

	public static void alterar(Cliente cliente) {

		try {

			String sql = "UPDATE CLIENTE SET nome = '" + cliente.getNome() + "', email = '" + cliente.getEmail()
					+ "' WHERE cpf = " + cliente.getCpf() + " ";
			Statement stm = conexao.createStatement();
			stm.executeUpdate(sql);
			conexao.commit();

		}

		catch (Exception e) {

		}

	}

	/*********************************************************************************************/

	public static void excluir(Cliente cliente) {

		try {
			String sql = "DELETE FROM CLIENTE WHERE cpf = " + cliente.getCpf() + "";
			Statement stm = conexao.createStatement();
			stm.executeUpdate(sql);
			conexao.commit();

		}

		catch (Exception e) {

		}

	}

	/*********************************************************************************************/

	public static void main(String[] args) {

		try {
			conectar();

			Scanner scs = new Scanner(System.in);
			int op = 0;
			int cpf;
			String nome, email;

			while (op != 6) {

				System.out.println("GERENCIAMENTO DE CLIENTES");
				System.out.println("==========================");
				System.out.println(" 1 - consultar todos");

				System.out.println("2 - consultar 1 cliente");

				System.out.println("3 - cadastrar novo");

				System.out.println("4 - alterar 1 cliente");

				System.out.println("5 -  remover 1 cliente");

				System.out.println("6 - sair");
				System.out.println("==========================");

				op = scs.nextInt();

				switch (op) {
				case 1:
					consultartodos();

					break;

				case 2:
					System.out.println("Informar o CPF");
					cpf = scs.nextInt();
					consultar(cpf);
					break;

				case 3:
					System.out.println("Informe o CPF");
					cpf = scs.nextInt();
					System.out.println("Informe o nome");
					nome = scs.next();
					System.out.println("Informe o email");
					email = scs.next();

					inserirSP(cpf, nome, email);

					break;

				case 4:
					System.out.println("Informe o CPF");
					cpf = scs.nextInt();
					scs.nextLine();
					System.out.println("Informe o nome");
					nome = scs.next();
					scs.nextLine();
					System.out.println("Informe o email");
					email = scs.next();
					// alterar(cpf, nome, email);

					break;

				default:
				case 5:
					System.out.println("Informe o CPF");
					cpf = scs.nextInt();
					// excluir(cpf);

					break;
				}

			}

		} catch (Exception e) {

		}
	}

}
