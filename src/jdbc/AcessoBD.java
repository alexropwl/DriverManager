package jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class AcessoBD {

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

	public static void consultar() throws SQLException {

		String consulta = "SELECT * FROM CLIENTE";
		Statement stm = conexao.createStatement();

		ResultSet rs = stm.executeQuery(consulta);

		while (rs.next()) {

			JOptionPane.showMessageDialog(null,
					"CPF: "+rs.getInt(1) + " NOME "+ rs.getString(2) +"EMAIL: " + rs.getString(3));

		}

	}

	public static void informeBD() throws SQLException {

		DatabaseMetaData meta = conexao.getMetaData();
		String fabricante = meta.getDatabaseProductName();
		String versaoBD = meta.getDatabaseProductVersion();

		JOptionPane.showMessageDialog(null, fabricante + "======>" + versaoBD);

	}

	public static void main(String[] args) throws SQLException {

		conectar();
		informeBD();
		consultar();
		conexao.close();

	}

}
