package httpServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;


public class ConsoleCommands extends Thread {
	private static String User = "AndroidServer";
	private static String pwd = "Hello";
	private static boolean debugMode = true;
	private static Scanner keyboard;
	private static String actualUser;
	private static String actualPwd;

	public ConsoleCommands() {
		// TODO Auto-generated constructor stub
	}

	public ConsoleCommands(Runnable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ConsoleCommands(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ConsoleCommands(ThreadGroup arg0, Runnable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ConsoleCommands(ThreadGroup arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ConsoleCommands(Runnable arg0, String arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ConsoleCommands(ThreadGroup arg0, Runnable arg1, String arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public ConsoleCommands(ThreadGroup arg0, Runnable arg1, String arg2,
			long arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public void run(){

		if(debugMode) {
			actualUser=User;
			actualPwd = pwd;
		}else {
			System.out.println("this stuff should get the username from the http stuff");
			actualUser = User;
			actualPwd = pwd;
		}

		System.out.println("Console command interperenter starting");
		ConsoleCommands.printCommands();
		keyboard = new Scanner(System.in);
		while (true){
			String input = keyboard.nextLine();
			input=input.toLowerCase();
			switch (input){
			case "help":
			case "/?":
				ConsoleCommands.printCommands();
				break;
			case "stop":
				ServerRunner.server.stop(0);
				keyboard.close();
				System.exit(0);
				break;
			case "get date":
				System.out.println(Communication.getDate(actualUser, actualPwd));
				break;
			case "set date":{
				System.out.println("Please input the date you want.");
				String date = keyboard.nextLine();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date dateObject;
				try {
					dateObject = format.parse(date);
					System.out.println("Date parsed as \t" + dateObject.toString());
					if (0==Communication.setDateStorred(actualUser, actualPwd, format.format(dateObject))) {

					}
				} catch (ParseException e) {
					System.err.println("Incorrect date given");
					e.printStackTrace();
				}}
			break;
			case "add book":
			{
				System.out.println("Adding new book, please give me the author");
				String author = keyboard.nextLine();
				System.out.println("And now the title");
				String title = keyboard.nextLine();
				System.out.println("And now the amount to add");
				int amount = keyboard.nextInt();
				Communication.setNewBook(actualUser, actualPwd, author, title, amount);
				System.out.println("Added the books");
			}
			break;
			case "print books":
			{
				if (Communication.verbose){System.out.println("I will now get all the books and print them");}
				ArrayList<Book> bookList = Communication.getAllBooks(actualUser, actualPwd);
				if (bookList.size()!=0){
					System.out.println("The books that are stored are");
					StringBuilder builder = new StringBuilder();
					Formatter formatter = new Formatter(builder);
					formatter.format("|%-4s|%-6s|%-30s|%-75s", "ID","Amount","Author","Title");
					for (int i = 0; i < bookList.size(); i++) {
						if (i%10==0){//Print the header every 10 lines
							System.out.format("\n"+builder.toString()+"\n");
						}
						bookList.get(i).printBook();

					}
					formatter.close();
				}
			}
			break;
			case "verbose on":

				System.out.println("Turning verbose on");
				Communication.verbose = true;
				break;
			case "verbose off":
				System.out.println("Turning verbose off");
				Communication.verbose = false;
				break;
			case "add student":
			{
				String studentName = null;
				boolean success = false;
				while(!success){
					System.out.println("Enter the student name:");
					studentName = keyboard.nextLine();
					if(studentName.length()>45){
						System.out.println("Your name is to long, please enter again");
					}else{success = true;}
				}
				String telephoneNumber = null;
				success = false;

				while(!success){
					System.out.println("Now enter a telephone number");
					telephoneNumber = keyboard.nextLine();//Due to the mess that is telephone numbers, I am not checking if this is correct
					//For more info, watch https://www.youtube.com/watch?v=LsxRaFNropw
					if(telephoneNumber.length()>50){
						System.out.println("Yeaahhh, this is not a valid telephone number");
					}else{
						success = true;
					}
				}
				int id = 0;
				success = false;
				while(!success){
					System.out.println("Enter the student id:");
					id = Integer.valueOf(keyboard.nextLine());
					if(id>9999){
						System.out.println("Your id is to long, please enter again");
					}else if(id<0){
						System.out.println("Did you just enter a negative id? You fool, try again!");
					}else{success = true;}
				}

				if (Communication.addStudent(actualUser, actualPwd, studentName, id, telephoneNumber)){
					System.out.println("Student added");
				}else{
					System.out.println("Failed to add student");
				}
			}
			break;

			case "add teacher":
			{
				String teacherName = null;
				boolean successful = false;
				while(!successful){
					System.out.println("Enter the teacher name:");
					teacherName = keyboard.nextLine();
					if(teacherName.length()>45){
						System.out.println("Your name is to long, please enter again");
					}else{successful = true;}
				}
				String departmentName = null;
				successful = false;

				while(!successful){
					System.out.println("Now enter the department name");
					departmentName = keyboard.nextLine();
					if(departmentName.length()>45){
						System.out.println("This department name is to long, please try again and keep it in under 45 characters");
					}else{
						successful = true;
					}
				}
				String teacherID = null;
				successful = false;
				while(!successful){
					System.out.println("Enter the teacher id:");
					teacherID = keyboard.nextLine();
					if(teacherID.length()!=3){
						System.out.println("Incorrect teacher id, please enter again");
					}else{
						successful = true;
					}
				}

				if (Communication.addTeacher(actualUser, actualPwd, teacherName, teacherID, departmentName)){
					System.out.println("Teacher added");
				}else{
					System.out.println("Failed to add teacher");
				}
			}
			break;
			case "settings":
				ConsoleCommands.getSetting(actualUser, actualPwd);
				break;

			case "deposit student":
			{	
				System.out.println("Please give me the student id of the student that you want to desposit money for");
				int id = Integer.valueOf(keyboard.nextLine());
				if(id>9999){
					System.out.println("Your id is to long, please enter again");
				}else if(id<0){
					System.out.println("Did you just enter a negative id? You fool, try again!");
				}else{
					System.out.println("Now enter the full student name");
					String studentName = keyboard.nextLine();
					if(studentName.length()>45){
						System.out.println("Your name is to long, please enter again");
					}else{
						int idStudent = Communication.getStudent(actualUser, actualPwd, studentName, id);
						if(idStudent==0){
							System.out.println("I could not locate the user");
						}else{
							System.out.println("Student found, retrieving current balance");
							float currentBalance = Communication.getStudentBalance(actualUser, actualPwd, idStudent);
							System.out.println("The current balance of "+ studentName + " is " + currentBalance);
							System.out.println("Please enter the amount you want to deposit");
							float change = Float.valueOf(keyboard.nextLine());
							System.out.println("The new balance should be " + (currentBalance+change));
							float newBalance = Communication.updatebalance(actualUser, actualPwd, idStudent, change);
							System.out.println("Balance of student \"" + studentName + "\" is now " + newBalance);
						}
					}
				}
			}
				break;
			default:
				System.out.println("Please try again");
				break;
			}
			System.out.println("Please enter your next command");
		}
	}

	public static void close() {
		System.out.println("Server is shutting down");
		ServerRunner.server.stop(0);
		System.exit(0);
	}
	/**
	 * Prints all the console commands available, sadly has to be hardcoded due to the switch statement in the interperenter
	 */
	public static void printCommands(){
		System.out.println("Current commands available");
		System.out.println("help \t\t\tBrings up this screen");
		System.out.println("/? \t\t\tBrings up this screen");

		System.out.println("stop \t\t\tStops the webserver completely/n");
		System.out.println("settings\t\tView and change settings in the database");
		System.out.println("verbose on\t\tTurns verbose messages in the console on");
		System.out.println("verbose off\t\tTurns verbose messages in the console off\n");

		System.out.println("get date\t\tGets the current date the database is operating on");
		System.out.println("set date\t\tAllows you to change the date that the database is operating on");
		System.out.println("add book\t\tAdd books, will ask for the required information and amount");
		System.out.println("print books\t\tPrints all the books in the database");
		System.out.println("add student\t\tAdd a student to the database");
		System.out.println("add teacher\t\tAdd a teacher to the database");
		System.out.println("deposit student\t\tDeposit money for a student");





	}

	/**
	 * Will allow the user to retrieve and change a setting in the settings table on the database.
	 * @param username Username to use to connect
	 * @param password Password to use to connect
	 */
	public static void getSetting(String username, String password){
		System.out.println("Enter the number of the setting you wish to change");
		System.out.println("1\tStudent book amount limit");
		System.out.println("2\tTeacher book amount limit");
		System.out.println("3\tStudent borrow day limit");
		System.out.println("4\tTeacher borrow day limit");

		System.out.println("5\tOnly display all settings");
		int setting  = Integer.valueOf(keyboard.nextLine());

		int v1,v2,v3,v4;
		switch (setting){
		case 1:
			v1 = Communication.getSetting(username, password, "StudentBookBorrowLimit");
			System.out.println("The current maximum amount of books a student can borrow is "+ v1);
			System.out.println("Please enter the new amount");
			{

				int temp = Integer.valueOf(keyboard.nextLine());
				if(Communication.setSetting(username, password, "StudentBookBorrowLimit", temp)){
					System.out.println("Updated setting to new value");
				}else{
					System.out.println("Failed to update setting to new value");
				}
			}
			break;
		case 2:
			v2 = Communication.getSetting(username, password, "TeacherBookBorrowLimit");
			System.out.println("The current maximum amount of books a teacher can borrow is "+ v2);
			System.out.println("Please enter the new amount");
			{

				int temp = Integer.valueOf(keyboard.nextLine());
				if(Communication.setSetting(username, password, "TeacherBookBorrowLimit", temp)){
					System.out.println("Updated setting to new value");
				}else{
					System.out.println("Failed to update setting to new value");
				}
			}
			break;
		case 3:
			v3 = Communication.getSetting(username, password, "StudentBookDayLimit");
			System.out.println("A student can borrow a book for up to "+ v3 + " days");
			System.out.println("Please enter the new amount");
			{

				int temp = Integer.valueOf(keyboard.nextLine());
				if(Communication.setSetting(username, password, "StudentBookDayLimit", temp)){
					System.out.println("Updated setting to new value");
				}else{
					System.out.println("Failed to update setting to new value");
				}
			}
			break;
		case 4:
			v4 = Communication.getSetting(username, password, "TeacherBookDayLimit");
			System.out.println("A teacher can borrow a book for up to "+ v4 + " days");
			System.out.println("Please enter the new amount");
			{

				int temp = Integer.valueOf(keyboard.nextLine());
				if(Communication.setSetting(username, password, "TeacherBookDayLimit", temp)){
					System.out.println("Updated setting to new value");
				}else{
					System.out.println("Failed to update setting to new value");
				}
			}
			break;
		case 5:
			v1 = Communication.getSetting(username, password, "StudentBookBorrowLimit");
			System.out.println("The current maximum amount of books a student can borrow is "+ v1);
			v2 = Communication.getSetting(username, password, "TeacherBookBorrowLimit");
			System.out.println("The current maximum amount of books a teacher can borrow is "+ v2);
			v3 = Communication.getSetting(username, password, "StudentBookDayLimit");
			System.out.println("A student can borrow a book for up to "+ v3 + " days");
			v4 = Communication.getSetting(username, password, "TeacherBookDayLimit");
			System.out.println("A teacher can borrow a book for up to "+ v4 + " days");
			break;
		default:
			System.out.println("Incorrect setting, returning to main screen");
			break;
		}
		keyboard.nextLine();
	}

}
