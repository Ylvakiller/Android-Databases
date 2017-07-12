package httpServer;

import java.sql.*;
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

	/**
	 *
	 * @param username login name
	 * @param password passwordt for said login.
	 * @return string with requested date.
	 */
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
//
//	/**
//	 *
//	 * @param username login name
//	 * @param password passwordt for said login.
//	 * @return string with requested student.
//	 */
//	public static String searchStudent(String username, String password, String name){
//
//	    connect(username, password);
//
//	}

	/*
	 * sets the date to the value of temp
	 * returns a true if the date was succesfully set
	 */
	public static int setDateStorred(String username, String password, String date){
		connect(username, password);
		int linesChanged = 0;

		String querry = "INSERT INTO Date (`DatabaseDate`) VALUES ('" + date + "')";
		try{
			Statement setDateStatement = con.createStatement();
			linesChanged = setDateStatement.executeUpdate(querry);

			setDateStatement.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			return 2;
		}
		close();

		if (linesChanged==0){
			return 0;
		}else{
			return 1;
		}
	}


	protected static void querryholder(){
		String Querry = "";
	}

}
