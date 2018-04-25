package httpServer;

import java.sql.Connection;
import java.sql.Date;
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
	 * @param username Username to use to connect
	 * @param password Password to use to connect
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
		}finally{
			close();
		}
		return date;
	}

	/*
	 * sets the date to the value of temp
	 * returns a true if the date was succesfully set
	 */
	public static int setDateStorred(String username, String password, String date){
		connect(username, password);
		int linesChanged = 0;

		String querry = "INSERT INTO date (`DataBaseDate`) VALUES (?)";
		PreparedStatement dateInsert = null;

		if(verbose){System.out.println(querry);}
		try{
			dateInsert = con.prepareStatement(querry);
			dateInsert.setString(1, date);
			linesChanged = dateInsert.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
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

			close();
		}

		if (linesChanged==0){
			return 1;
		}else{
			return 0;
		}

	}
	/**
	 * Makes a new book in the BookInfo table and adds the specified amounts of books to the bookid table
	 * If at any points it encounters an error it will spit a generic error message along with the stack trace and roll back what it was doing
	 * 
	 * @param username Username to use to connect
	 * @param password Password to use to connect
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
	 * @param reconnect true if a new connection needs to be opened
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

	/**
	 * Gets a list of all the book types in the database
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @return An arraylist with book types
	 */
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
			return false;
		}finally{
			close();
		}
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
	 * @param department The name of the department
	 * @return true if teacher succesfully added
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
			return false;
		}finally{
			close();
		}
		if(linesChanged==1){
			return true;
		}

		return false;

	}

	/**
	 * Retrieves a single row in the settings table
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param SettingName The setting to retrieve
	 * @return The integer value of the setting
	 */
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
			e.printStackTrace();
		}finally{
			close();
		}
		return value;
	}

	/**
	 * Updates a single row in the settings table
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param SettingName The setting to update
	 * @param newSetting The new value of the corresponding setting
	 * @return true if succesfully updated setting
	 */
	public static boolean setSetting(String username, String password, String SettingName, int newSetting){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "UPDATE `settings` SET `Value` = ? WHERE `SettingName`=?";
		PreparedStatement settingStmnt = null;
		boolean succeeded = false;
		try {
			settingStmnt = con.prepareStatement(querry);
			settingStmnt.setString(1, String.valueOf(newSetting));
			settingStmnt.setString(2, SettingName);
			int linesChanged = settingStmnt.executeUpdate();
			if (linesChanged==1){
				succeeded = true;
			}


		} catch (SQLException e) {
			e.printStackTrace();
			succeeded = false;
		}finally{
			close();
		}
		return succeeded;
	}

	/**
	 * Gets the internal id used by the database for a given student
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param name The name as how the student is stored in the database
	 * @param id The student id as is stored in the database
	 * @return the internal database id for a student, 0 if not found
	 */
	public static int getStudent(String username, String password, String name, int id){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "SELECT `idStudent` FROM `students` WHERE `StudentID`=? AND `Name`=?";
		PreparedStatement studentStmnt = null;
		int value = 0;
		try {
			studentStmnt = con.prepareStatement(querry);
			studentStmnt.setString(1, String.valueOf(id));
			studentStmnt.setString(2, name);
			ResultSet results = studentStmnt.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("Student not found");
				}
			}else{
				value = results.getInt(1);
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return value;
	}

	/**
	 * Retrieves the balance of a student
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param idStudent The internal id as found by {@link #getStudent(String, String, String, int)}}
	 * @return The balance of a student
	 */
	public static float getStudentBalance(String username, String password,int idStudent){

		connect(username,password);//always connect first to see if the program needs to crash :)

		float balance = 0;
		String querry = "SELECT `Balance` FROM `balancestudents` WHERE `Students_idStudent`=?";
		PreparedStatement studentStmnt = null;
		try {
			studentStmnt = con.prepareStatement(querry);
			studentStmnt.setString(1, String.valueOf(idStudent));
			ResultSet results = studentStmnt.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("balance not found");
				}
			}else{
				balance = results.getFloat(1);
			}
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return balance;
	}

	/**
	 * Will update the balance of a student based on its current balance as found by {@link #getStudentBalance(String, String, int)}} and the given amount to add
	 * Uses the {@link #getDate(String, String)} to make sure it updates the transaction table correctly
	 * 
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param idStudent The internal id as found by {@link #getStudent(String, String, String, int)}}
	 * @param amountToAdd The amount to add to a students balance
	 * @return the new balance that is stored, is taken from the {@link #getStudentBalance(String, String, int)} method
	 */
	public static float updatebalanceStudent(String username, String password, int idStudent, float amountToAdd){
		String date = Communication.getDate(username, password);//First get the date, before opening a new connection
		float balance = Communication.getStudentBalance(username, password, idStudent);
		connect(username,password);//always connect first to see if the program needs to crash :)


		String updateQuerry ="UPDATE `balancestudents` SET `balance` = ? WHERE `Students_idStudent`=?";
		String transactionQuerry ="INSERT INTO `transaction` (`idUser`,`Date`,`Change`, `idType`) VALUES (?,?,?,?)";
		PreparedStatement updateBalanceStmnt = null;
		PreparedStatement transactionStmnt = null;
		try {
			con.setAutoCommit(false);
			updateBalanceStmnt = con.prepareStatement(updateQuerry);
			if(verbose){
				System.out.println("The new balance should be " + (amountToAdd+balance));
			}
			updateBalanceStmnt.setFloat(1,amountToAdd+balance);
			updateBalanceStmnt.setInt(2, idStudent);
			int linesChanged = updateBalanceStmnt.executeUpdate();
			if(linesChanged == 1 ){
				if(verbose){
					System.out.println("Updated balance");
				}
			}else{
				throw new SQLException("Failed to update balance");
			}
			transactionStmnt = con.prepareStatement(transactionQuerry);
			transactionStmnt.setInt(1, idStudent);
			transactionStmnt.setString(2, date);
			transactionStmnt.setFloat(3, amountToAdd);
			transactionStmnt.setString(4, "1");
			linesChanged = transactionStmnt.executeUpdate();
			if(linesChanged == 1){
				if(verbose){
					System.out.println("Inserted transaction");
				}
			}else{
				throw new SQLException("Failed to insert transaction");
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error updating balance, rolling back");
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			amountToAdd = 0;
		}finally{
			close();
		}
		return Communication.getStudentBalance(username, password, idStudent);
	}


	/**
	 * Gets the internal id used by the database for a given Teacher
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param name The name as how the teacher is stored in the database
	 * @param id The Teacher id as is stored in the database
	 * @return the internal database id for a teacher, 0 if not found
	 */
	public static int getTeacher(String username, String password, String name,String id){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "SELECT `idTeacher` FROM `teachers` WHERE `TeacherID`=? AND `Name`=?";
		PreparedStatement teacherStmnt = null;
		int value = 0;
		try {
			teacherStmnt = con.prepareStatement(querry);
			teacherStmnt.setString(1, id);
			teacherStmnt.setString(2, name);
			ResultSet results = teacherStmnt.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("Teacher not found");
				}
			}else{
				value = results.getInt(1);
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return value;
	}

	/**
	 * Retrieves the balance of a teacher
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param idTeacher The internal id as found by {@link #getTeacher(String, String, String, String)}}
	 * @return The balance of a teacher
	 */
	public static float getTeacherBalance(String username, String password,int idTeacher){

		connect(username,password);//always connect first to see if the program needs to crash :)

		float balance = 0;
		String querry = "SELECT `Balance` FROM `balanceteacher` WHERE `Teachers_idTeacher`=?";
		PreparedStatement teacherStmnt = null;
		try {
			teacherStmnt = con.prepareStatement(querry);
			teacherStmnt.setInt(1,idTeacher);
			ResultSet results = teacherStmnt.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("balance not found");
				}
			}else{
				balance = results.getFloat(1);
			}
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return balance;
	}

	/**
	 * Will update the balance of a teacher based on its current balance as found by {@link #getTeacherBalance(String, String, int)}} and the given amount to add
	 * Uses the {@link #getDate(String, String)} to make sure it updates the transaction table correctly
	 * 
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param idTeacher The internal id as found by {@link #getTeacher(String, String, String, String)}}
	 * @param amountToAdd The amount to add to a students balance
	 * @return the new balance that is stored, is taken from the {@link #getTeacherBalance(String, String, int)} method to check
	 */
	public static float updatebalanceTeacher(String username, String password, int idTeacher, float amountToAdd){
		String date = Communication.getDate(username, password);//First get the date, before opening a new connection
		float balance = Communication.getTeacherBalance(username, password, idTeacher);
		connect(username,password);//always connect first to see if the program needs to crash :)


		String updateQuerry ="UPDATE `balanceteachers` SET `balance` = ? WHERE `teachers_idTeacher`=?";
		String transactionQuerry ="INSERT INTO `transaction` (`idUser`,`Date`,`Change`, `idType`) VALUES (?,?,?,?)";
		PreparedStatement updateBalanceStmnt = null;
		PreparedStatement transactionStmnt = null;
		try {
			con.setAutoCommit(false);
			updateBalanceStmnt = con.prepareStatement(updateQuerry);
			if(verbose){
				System.out.println("The new balance should be " + (amountToAdd+balance));
			}
			updateBalanceStmnt.setFloat(1,amountToAdd+balance);
			updateBalanceStmnt.setInt(2, idTeacher);
			int linesChanged = updateBalanceStmnt.executeUpdate();
			if(linesChanged == 1 ){
				if(verbose){
					System.out.println("Updated balance");
				}
			}else{
				throw new SQLException("Failed to update balance");
			}
			transactionStmnt = con.prepareStatement(transactionQuerry);
			transactionStmnt.setInt(1, idTeacher);
			transactionStmnt.setString(2, date);
			transactionStmnt.setFloat(3, amountToAdd);
			transactionStmnt.setString(4, "0");
			linesChanged = transactionStmnt.executeUpdate();
			if(linesChanged == 1){
				if(verbose){
					System.out.println("Inserted transaction");
				}
			}else{
				throw new SQLException("Failed to insert transaction");
			}
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error updating balance, rolling back");
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			amountToAdd = 0;
		}finally{
			close();
		}
		return Communication.getStudentBalance(username, password, idTeacher);
	}

	/**
	 * Will get the id, activity and name from all the students
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @return an Arraylist with strings in it where the first 4 characters are the id, the 5th is a boolean showing activity and then the name
	 */
	public static ArrayList<String> getAllStudents(String username, String password){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "SELECT `StudentID`,`Name`,`Active` FROM `students` ORDER BY `Active` DESC, `StudentID` ASC";
		PreparedStatement settingStmnt = null;
		int value = 0;
		ArrayList<String> names = new ArrayList<String>();
		try {
			settingStmnt = con.prepareStatement(querry);
			ResultSet results = settingStmnt.executeQuery();
			while(results.next()){
				//We know the student id is a 4 digit int
				String line = String.valueOf(results.getInt(1));
				while(line.length()<4){
					line = "0" + line;
				}//This adds padding zeroes to an id to make sure the length is 4
				//Now we can just paste the name after it and add it to the array list
				line = line + results.getInt(3);
				line = line + results.getString(2);
				names.add(line);
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return names;
	}
	/**
	 * Will get the id, activity and name from all the students
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @return an Arraylist with strings in it where the first 4 characters are the id, the 5th is a boolean showing activity and then the name
	 */
	public static ArrayList<String> getAllTeachers(String username, String password){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "SELECT `TeacherID`,`Name`,`Active` FROM `teachers` ORDER BY `Active` DESC, `TeacherID` ASC";
		PreparedStatement settingStmnt = null;
		int value = 0;
		ArrayList<String> names = new ArrayList<String>();
		try {
			settingStmnt = con.prepareStatement(querry);
			ResultSet results = settingStmnt.executeQuery();
			while(results.next()){
				//We know the teacher id is a 3 char string
				String line = results.getString(1);
				while(line.length()<3){
					line = "0" + line;
				}//This adds padding zeroes to an id to make sure the length is 3
				//Now we can just paste the name after it and add it to the array list
				line = line + results.getInt(3);
				line = line + results.getString(2);
				names.add(line);
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return names;
	}

	/**
	 * Gets an arrayList of specific books that are currently borrowed by a student or teacher
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param id the internal id of the user to search for
	 * @param isStudent true if the internal id is for a student
	 * @return an Arraylist of specificbooks, or null if no books are being borrowed by the teacher
	 */
	public static ArrayList<SpecificBook> getBooksBorrowed(String username, String password, int id, boolean isStudent){
		connect(username,password);//always connect first to see if the program needs to crash :)
		String querry = "SELECT `BookID_BookNumberID` FROM `borrow` WHERE `Returned`=0 AND `id`=? AND `isStudent`=?"; //Will get the ids of books currently being borrowed by the student
		PreparedStatement statement = null;
		int value = 0;
		ArrayList<SpecificBook> books = new ArrayList<SpecificBook>();
		try {
			statement = con.prepareStatement(querry);
			statement.setInt(1, id);
			if(isStudent){
				statement.setString(2, "1");
			}else{
				statement.setString(2, "0");
			}
			ResultSet results = statement.executeQuery();
			while(results.next()){
				int bookNumberID = results.getInt(1);
				books.add(Communication.getBookInfoByBookID(username, password, bookNumberID, false));//get info and put it in the list
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return books;
	}

	/**
	 * Gets a specific book based on the internal id with all the details from that book
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param bookID The id to get the details from
	 * @param reconnect set to true if you are running this stand alone, set to false if you are running it whilst already connected to the database
	 * @return a specificBook with all the variables like id, author, title, borrowed, internal id
	 */
	public static SpecificBook getBookInfoByBookID(String username, String password, int bookID, boolean reconnect){
		if(reconnect){connect(username, password);}
		String querry = "SELECT `idBook`,`Author`, `Title`, `Borrowed` FROM bookinfo A JOIN bookid B ON A.idBook = B.BookID WHERE B.BookNumberID=? ";
		PreparedStatement statement = null;
		SpecificBook book = null;
		int value = 0;
		try {
			statement = con.prepareStatement(querry);
			statement.setInt(1, bookID);
			ResultSet results = statement.executeQuery();
			if(results.next()){
				book = new SpecificBook(results.getInt(1),results.getString(2),results.getString(3),bookID, results.getBoolean(4));
				
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(reconnect){close();}
		}
		return book;
	}

	/**
	 * Will borrow the book, needs to mark the book as borrowed and fill in the details in the borrow table
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param bookid The specific book id
	 * @param id The id of the one borrowing the book
	 * @param isStudent true if its a student
	 * @return true if successfully borrowed a book
	 */
	public static boolean borrowBook(String username, String password, int bookid, int id, boolean isStudent){
		String date = Communication.getDate(username, password);
		boolean success = false;
		connect(username, password);
		String updateQuerry ="UPDATE `bookid` SET `Borrowed` = 1 WHERE `BookNumberID`=?";
		String insertQuerry ="INSERT INTO `borrow` (`Date`,`BookID_BookNumberID`,`id`, `isStudent`) VALUES (?,?,?,?)";
		PreparedStatement updateStmnt = null;
		PreparedStatement insertStmnt = null;
		try {
			con.setAutoCommit(false);
			updateStmnt = con.prepareStatement(updateQuerry);
			updateStmnt.setInt(1, bookid);
			int linesChanged = updateStmnt.executeUpdate();
			if (linesChanged!=1){
				if (verbose) {
					System.err.println("Error setting a book to be borrowed, rolling back");
				}
				throw new SQLException("Failed set book to be borrowed");
			}else{
				insertStmnt = con.prepareStatement(insertQuerry);
				insertStmnt.setString(1, date);
				insertStmnt.setInt(2, bookid);
				insertStmnt.setInt(3, id);
				insertStmnt.setBoolean(4, isStudent);
				linesChanged = insertStmnt.executeUpdate();
				if (linesChanged!=1){
					if (verbose) {
						System.err.println("Error setting a book to be borrowed, rolling back");
					}
					throw new SQLException("Failed add book to borrow list");
				}else{
					if (verbose) {
						System.out.println("Book is borrowed");
					}
					success = true;
				}
			}

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			close();
		}
		return success;
	}
	/**
	 * Will check if the student is marked as active in the database, is needed before allowing to borrow
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param id The internal student ID from a student
	 * @return the value stored in a database, false if something goes wrong
	 */
	public static boolean checkIfActiveStudent(String username, String password, int id){
		connect(username, password);

		String querry = "SELECT `Active` FROM `students` WHERE `idStudent`=?";
		PreparedStatement studentStmnt = null;
		boolean value = false;
		try {
			studentStmnt = con.prepareStatement(querry);
			studentStmnt.setInt(1, id);
			ResultSet results = studentStmnt.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("Student not found");
				}
			}else{
				value = results.getBoolean(1);
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}

		return value;
	}

	/**
	 * Will check if the teacher is marked as active in the database, is needed before allowing to borrow
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param id The internal teacher ID from a student
	 * @return the value stored in a database, false if something goes wrong
	 */
	public static boolean checkIfActiveTeacher(String username, String password, int id){
		connect(username, password);

		String querry = "SELECT `Active` FROM `teachers` WHERE `idTeacher`=?";
		PreparedStatement teacherStmnt = null;
		boolean value = false;
		try {
			teacherStmnt = con.prepareStatement(querry);
			teacherStmnt.setInt(1, id);
			ResultSet results = teacherStmnt.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("Teacher not found");
				}
			}else{
				value = results.getBoolean(1);
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}

		return value;
	}

	/**
	 * Will get all the books currently borrowed
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @return A list of borrowed books
	 */
	public static ArrayList<BorrowedBook> getBorrowedBooks(String username, String password){
		connect(username, password);

		String querry = "SELECT `idBook`,`Author`,`Title`,`BookNumberID`,`Date`,`isStudent`,`id` FROM bookinfo A JOIN bookid B ON A.idBook = B.BookID JOIN borrow C ON B.BookNumberID = C.BookID_BookNumberID WHERE C.Returned='0' ";
		PreparedStatement allButNameStmnt = null;
		ResultSet results = null;

		ArrayList<BorrowedBook> books = new ArrayList<BorrowedBook>();
		try {
			allButNameStmnt = con.prepareStatement(querry);
			results = allButNameStmnt.executeQuery();
			while(results.next()){
				String name = getNameByID(results.getInt(7),results.getBoolean(6), username, password);
				BorrowedBook book = new BorrowedBook(results.getInt(1), results.getString(2), results.getString(3), results.getInt(4), results.getDate(5), name, results.getBoolean(6));
				books.add(book);
			}
			results.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(!results.isClosed()){
					results.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			close();
		}
		return books;
	}

	/**
	 * Gets the name of student or teacher
	 * @param id the internal id of the student or teacher
	 * @param isStudent true if the id is for a student, false for a teacher
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @return The name as stored in the database
	 * @throws SQLException throws exception if communication with the database is not working correctly, or if the user id was not found
	 */
	private static String getNameByID(int id, boolean isStudent, String username, String password) throws SQLException{
		if(con.isClosed()){
			connect(username,password);
		}
		String first, second, name;
		if (isStudent){
			first = "students";
			second =  "idStudent";
		}else{
			first = "teachers";
			second = "idTeacher";
		}
		String nameQuerry = "SELECT `Name` FROM "+first+" WHERE "+second+"=?";//first ? is either the students or teachers table, second is idStudent or idTeacher, third is the id from the first querry
		PreparedStatement nameStmnt = null;
		nameStmnt = con.prepareStatement(nameQuerry);
		nameStmnt.setInt(1, id);
		ResultSet results = nameStmnt.executeQuery();
		if(!results.next()){
			if(verbose){
				System.out.println("Error, I need to exit, user not present");
			}
			throw new SQLException("User id found in borrow table but not in teacher or student table");
		}else{
			name = results.getString(1);
		}
		results.close();
		return name;
	}
	
	/**
	 * Will tell you if a book is currently borrowed
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 * @param id The id of the book to check
	 * @return true if the book is currently on loan
	 */
	public static boolean isBorrowed(String username, String password, int id){
		connect(username, password);

		String querry = "SELECT `Borrowed` FROM `bookid` WHERE `BookNumberID`=?";
		PreparedStatement borrowedStatement = null;
		boolean value = false;
		try {
			borrowedStatement = con.prepareStatement(querry);
			borrowedStatement.setInt(1, id);
			ResultSet results = borrowedStatement.executeQuery();
			if(!results.next()){
				if(verbose){
					System.out.println("book not found");
				}
			}else{
				value = results.getBoolean(1);
			}
			results.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}

		return value;
	}
	
	public static void getAllLateBooks(String username, String password){
		
	}

}
