package httpServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Communication {
	public static boolean verbose = false;

	public Communication() {
		// TODO Auto-generated constructor stub
	}
	private static final String hostname = "jdbc:mysql://192.168.178.10:3306/";
	private static final String dbName = "androiddatabase";
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
			System.out.println("Failed to connect to database, exciting program");
			
			System.out.println(ex.toString());
			
			System.exit(1);
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
	 * @param password password for said login.
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
			close();
		}
		close();
		return date;
	}

	/*
	 * sets the date to the value of temp
	 * returns a true if the date was succesfully set
	 */
	public static int setDateStorred(String username, String password, String date){
		connect(username, password);
		int linesChanged = 0;

		String querry = "INSERT INTO Date (`DatabaseDate`) VALUES (?)";
		PreparedStatement dateInsert = null;

		if(verbose){System.out.println(querry);}
		try{
			dateInsert = con.prepareStatement(querry);
			dateInsert.setString(1, date);
			linesChanged = dateInsert.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
			close();
			return 2;
		}finally {
			if (dateInsert!=null) {
				try {
					dateInsert.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		close();

		if (linesChanged==0){
			return 1;
		}else{
			return 0;
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
		if(author.length()>30||title.length()>75){
			System.err.println("To long an input given when adding books");
			throw new IllegalArgumentException("To long a string given");
		}
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
			int bookID = Communication.getBookID(username, password, author, title, false);
			String addBooksQuerry = "INSERT INTO `bookid` (`BookID`) VALUES('"+bookID+"')";

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
		close();
	}

	/**
	 * Retrieves the book ID of a specific book type, not of the individual versions of the book
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param author Author to search for
	 * @param title Title to search for
	 * @return The Id found
	 */
	public static int getBookID(String username, String password, String author, String title, boolean reconnect) {
		if(reconnect){connect(username, password);}
		String retrieveBookIdea =  "SELECT `idBook` FROM `bookinfo` WHERE author = ? AND title = ?";
		PreparedStatement getId = null;
		int idNumber = 0;
		try {
			getId = con.prepareStatement(retrieveBookIdea);
			getId.setString(1, author);
			getId.setString(2, title);
			ResultSet id = getId.executeQuery();
			if (id.next()) {
				idNumber = id.getInt(1);
				//System.out.println("Id found to be " + idNumber);
			}else {
				System.out.println("Book not found");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (getId!=null){
				try {
					getId.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(reconnect){close();}
		return idNumber;
	}

	
	public static ArrayList<Book> getAllBooks(String username, String password) {
		connect(username, password);
		String retrieveAllBooks = "SELECT * FROM `bookinfo` ORDER BY `idBook` ASC";
		String retrieveIndividualBookIdea =  "SELECT `idBook` FROM `bookinfo` WHERE author = ? AND title = ?";
		PreparedStatement getIds = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try {
			Statement getBooksStatement = con.createStatement();
			ResultSet allBooks = getBooksStatement.executeQuery(retrieveAllBooks);
			while (allBooks.next()){
				int id = allBooks.getInt(1); //Retrieve the next BookID on the list
				ArrayList<Integer> bookNumberIDs = Communication.getAllBookNumbersByID(username, password, id);
				bookList.add(new Book(id, allBooks.getString(2), allBooks.getString(3), bookNumberIDs.size()));
				
			}
			allBooks.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (getIds!=null){
				try {
					getIds.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		close();
		
		return bookList;
	}

	/**
	 * Obtains an ArrayList with all the BookNumberIDs corresponding to the given BookID
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param BookID The BookID to search for
	 * @return an ArrayList with all the BookNumberIDs that where found
	 */
	public static ArrayList<Integer> getAllBookNumbersByID(String username, String password, int BookID){
		connect(username, password);
		String retrieveBooksOfID = "SELECT `BookNumberID` FROM `bookid` WHERE `BookID`=?";
		PreparedStatement bookId = null;
		ArrayList<Integer> BookNumberIDs = new ArrayList<Integer>();
		try {
			bookId = con.prepareStatement(retrieveBooksOfID);
			bookId.setString(1, String.valueOf(BookID));
			ResultSet results = bookId.executeQuery();
			while(results.next()){
				BookNumberIDs.add(results.getInt(1));
			}
			results.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (bookId!=null){
				try {
					bookId.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		close();
		return BookNumberIDs;
	}
	/**
	 * Will insert a new student into the database, presumes that the info is correct, but will give a false if it failed
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param name Name of the new student
	 * @param id 4 digit id of the new student
	 * @param telephone telephone of the new student
	 * @return true if succesfully added the student
	 */
	public static boolean addStudent(String username, String password, String name, int id, String telephone){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "INSERT INTO `students` (`StudentID`,`Name`,`TelephoneNumber`) VALUES (?,?,?)";
		int linesChanged = 0;
		PreparedStatement studentInsert = null;
		if(verbose){System.out.println(querry);}
		try {
			studentInsert = con.prepareStatement(querry);
			studentInsert.setString(1, String.valueOf(id));
			studentInsert.setString(2, name);
			studentInsert.setString(3, telephone);
			linesChanged = studentInsert.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
			close();
			return false;
		}
		close();
		if(linesChanged==1){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Will insert a new teacher into the database, presumes that the info is correct, will return false if failed
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param name Name of the teacher to add
	 * @param id 3 digit string
	 * @param department
	 * @return
	 */
	public static boolean addTeacher(String username, String password, String name, String id, String department){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "INSERT INTO `teachers` (`TeacherID`,`Name`,`department`) VALUES (?,?,?)";
		int linesChanged = 0;
		PreparedStatement teacherInsert = null;
		if(verbose){System.out.println(querry);}
		try {
			teacherInsert = con.prepareStatement(querry);
			teacherInsert.setString(1, id);
			teacherInsert.setString(2, name);
			teacherInsert.setString(3, department);
			linesChanged = teacherInsert.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
			close();
			return false;
		}
		close();
		if(linesChanged==1){
			return true;
		}
		
		return false;
		
	}
	
	public static int getSetting(String username, String password, String SettingName){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "SELECT `Value` FROM `settings` WHERE `SettingName`=?";
		PreparedStatement settingStmnt = null;
		int value = 0;
		try {
			settingStmnt = con.prepareStatement(querry);
			settingStmnt.setString(1, SettingName);
			ResultSet results = settingStmnt.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("Incorrect setting");
				}
			}else{
				value = results.getInt(1);
			}
			results.close();
			
		} catch (SQLException e) {
			close();
			e.printStackTrace();
		}
		close();
		return value;
	}
}
