package httpServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Communication {

	public Communication() {
		// TODO Auto-generated constructor stub
	}
	private static final String hostname = "jdbc:mysql://localhost/";
	private static final String dbName = "AndroidDatabase";
	//private static final String username = "Android";
	//private static final String password = "";
	public static Connection con;

	/**
	 * Opens a connection to the database
	 * @return false if connection not established
	 */
	private static boolean connect(String username, String password){
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
	
	protected static String getDate(String username, String password){
		String date = null;
		connect(username, password);
		String Querry = "SELECT `DatabaseDate` FROM `Date` ORDER BY `DateChanged` DESC";
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
	/*
	 * sets the date to the value of temp
	 * returns a true if the date was succesfully set
	 */
	public boolean SetDateStorred(String username, String password, Date date){
		connect(username, password);
		int linesChanged = 0;
		
		String querry = "INSERT INTO date (`date`)VALUES('" + date + "')";
		try{
			Statement setDateStatement = con.createStatement();
			linesChanged = setDateStatement.executeUpdate(querry);
			
			setDateStatement.close();
			
		} catch (SQLException e1) {
			
		}
		close();
		
		if (linesChanged==0){
			return false;
		}else{
			return true;
		}
	}
	
	protected static void querryholder(){
		String Querry = "";
	}
	
}
