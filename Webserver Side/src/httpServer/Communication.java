package httpServer;

import java.sql.*;


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
			//"jdbc:mysql://hostname/dbname?user=user&password=password"
			con = DriverManager.getConnection(hostname + dbName +"?user="+ username + "&password=" + password);
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
		String Querry = "SELECT `DataBaseDate` FROM `date` ORDER BY `date`.`DateChanged` DESC";
		try{
			Statement getDateStatement = con.createStatement();
			ResultSet dbDate = getDateStatement.executeQuery(Querry);
			if(!dbDate.next()){
				date= "No date found";
			}else {
				date = dbDate.getString(1);
				//System.out.println("Date found to be " + date);
			}
			dbDate.close();

		}catch (SQLException ex){
			ex.printStackTrace();
			ConsoleCommands.close();
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
		System.out.println(querry);
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
	/**
	 * Makes a new book in the BookInfo table and adds the specified amounts of books to the bookid table
	 * If at any points it encounters an error it will spit a generic error message along with the stack trace and roll back what it was doing
	 * @param author The author of the book
	 * @param title The title of the book
	 * @param amount The amount of books to initially add
	 */
	public static void setNewBook(String username, String password,String author, String title, int amount) {
		connect(username, password);
		int linesChanged = 0;
		String infoQuerry = "INSERT INTO `bookinfo` (`Author`, `Title`) VALUES (?, ? )";
		String retrieveBookIdea =  "SELECT `idBook` FROM `bookinfo` WHERE author = ? AND title = ?";
		PreparedStatement addInfo = null;
		PreparedStatement getId = null;
		PreparedStatement addBooks = null;
		try {
			con.setAutoCommit(false);
			addInfo = con.prepareStatement(infoQuerry);
			addInfo.setString(1, author);
			addInfo.setString(2, title);
			linesChanged = addInfo.executeUpdate();
			if(linesChanged!=1) {
				throw new SQLException("Something is very wrong");
			}
			getId = con.prepareStatement(retrieveBookIdea);
			getId.setString(1, author);
			getId.setString(2, title);
			ResultSet id = getId.executeQuery();
			if (id.next()) {
				System.out.println("Id found to be" + id.getInt(1));
			}else {
				System.out.println("Book not found");
			}
			String addBooksQuerry = "INSERT INTO `bookid` (`BookID`) VALUES('"+id.getInt(1)+"')";

			for(int i = 0; i< amount; i++) {
				addBooks = con.prepareStatement(addBooksQuerry);
				if (addBooks.executeUpdate()!=1) {
					throw new SQLException("Something is very wrong");
				}

			}

			con.commit();
		} catch (SQLException e) {
			System.err.println("Something went wrong, rolling back");
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.err.println("Something is very very very wrong");
				e1.printStackTrace();
			}
			e.printStackTrace();


		} finally {
			if (addInfo!=null) {
				try {
					addInfo.close();
				} catch (SQLException e) {
					System.err.println("More of the wrong things are happening");
					e.printStackTrace();
				}
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println("Okay if this goes wrong you really should exit whilst you still can");
				e.printStackTrace();
			}
		}



	}


	protected static void querryholder(){
		@SuppressWarnings("unused")
		String Querry = "";
	}

}
