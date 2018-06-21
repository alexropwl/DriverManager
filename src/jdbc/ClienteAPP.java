package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ClienteAPP {

	static String usuario = "curso_java";
	static String senha = "schema";
	static String url = "jdbc:oracle:thin:@localhost:1521:XE ";

	static Connection conexao;

	public static void conectar() {

		try {
			conexao = DriverManager.getConnection(url, usuario, senha);
			conexao.setAutoCommit(false);

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

	public static void inserir(int cpf, String nome, String email) {

		try {
			String sql = "INSERT INTO CLIENTE VALUES (" + cpf + ", '" + nome + "', '" + email + "')";
			Statement stm = conexao.createStatement();
			stm.execute(sql);
			conexao.commit();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERRO AO INSERIR NO BANCO!");

		}

	}

	/*********************************************************************************************/

	public static void consultar(int cpf) {

		try {

			String sql = "SELECT * FROM CLIENTE WHERE cpf=" + cpf + "";
			Statement stm = conexao.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {

				System.out.println("CPF: " + rs.getInt(1) + " NOME " + rs.getString(2) + "EMAIL: " + rs.getString(3));

			}
			conexao.commit();

		}

		catch (Exception e) {

		}

	}

	/*********************************************************************************************/

	public static void consultartodos() {

		try {
			String sql = "SELECT * FROM CLIENTE";
			Statement stm = conexao.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			int cont = 0;

			while (rs.next()) {

				System.out.println("CPF: " + rs.getInt(1) + " NOME " + rs.getString(2) + "EMAIL: " + rs.getString(3));
				cont++;
				System.out.println("Numero de clientes listados: " + cont);
			}

		} catch (Exception e) {

		}

	}

	/*********************************************************************************************/

	public static void alterar(int cpf, String nome, String email) {

		try {

			String sql = "UPDATE CLIENTE SET nome = '" + nome + "', email = '" + email + "' WHERE cpf = " + cpf + " ";
			Statement stm = conexao.createStatement();
			stm.executeUpdate(sql);
			conexao.commit();

		}

		catch (Exception e) {

		}

	}

	/*********************************************************************************************/

	public static void excluir(int cpf) {

		try {
			String sql = "DELETE FROM CLIENTE WHERE cpf = " + cpf + "";
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

					inserir(cpf, nome, email);

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
					alterar(cpf, nome, email);

					break;

				default:
				case 5:
					System.out.println("Informe o CPF");
					cpf = scs.nextInt();
					excluir(cpf);

					break;
				}

			}

		} catch (Exception e) {

		}
	}

}
