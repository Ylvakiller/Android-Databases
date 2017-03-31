package httpServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Communication {

	public Communication() {
		// TODO Auto-generated constructor stub
	}
	private static final String hostname = "jdbc:mysql://localhost/";
	private static final String dbName = "Android";
	private static final String username = "Android";
	private static final String password = "";
	public static Connection con;

	/**
	 * Opens a connection to the database
	 * @return false if connection not established
	 */
	private static boolean connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(hostname + dbName, username, password);
		}catch(SQLException ex){
			System.out.println(ex.toString());
			return false;
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * Closes connection to database
	 */
	private static void close() {
		try {
			con.close();
		}catch (SQLException ex){
			System.out.println(ex.toString());
		}
		
	}
	
	protected static String getDate(){
		String date = null;
		connect();
		String Querry = "SELECT `dbDate` FROM `Time` ORDER BY `datemodified` DESC";
		try{
			Statement getDateStatement = con.createStatement();
			ResultSet dbDate = getDateStatement.executeQuery(Querry);
			dbDate.next();
			date = dbDate.getString(1);
			dbDate.close();
			System.out.println("Date found to be " + date);
		}catch (SQLException ex){
			ex.printStackTrace();
		}
		close();
		return date;
	}
	
	
	protected static void querryholder(){
		String Querry = "";
	}
	
}
