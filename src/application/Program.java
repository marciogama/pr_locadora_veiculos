package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {

//		Testando a conexão com o banco de dados
//		Connection conn = DB.getConnection();
//		DB.closeConnection();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			
			st = conn.createStatement();
			
			rs = st.executeQuery("select * from carro");
//		     rs = st.executeQuery("select * from cliente");
			
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + ", " + rs.getString("placa"));
//		     	   System.out.println(rs.getString("cpf") + ", " + rs.getString("nome"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
