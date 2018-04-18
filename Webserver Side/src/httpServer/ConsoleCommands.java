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
		String actualUser;
		String actualPwd;
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
		Scanner keyboard = new Scanner(System.in);
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
			case "set date":
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
				}
			case "add book":
				System.out.println("Adding new book, please give me the author");
				String author = keyboard.nextLine();
				System.out.println("And now the title");
				String title = keyboard.nextLine();
				System.out.println("And now the amount to add");
				int amount = keyboard.nextInt();
				Communication.setNewBook(actualUser, actualPwd, author, title, amount);
				System.out.println("Added the books");
				break;
			case "print books":
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
		System.out.println("verbose on\t\tTurns verbose messages in the console on");
		System.out.println("verbose off\t\tTurns verbose messages in the console off\n");

		System.out.println("get date\t\tGets the current date the database is operating on");
		System.out.println("set date\t\tAllows you to change the date that the database is operating on");
		System.out.println("add book\t\tAdd books, will ask for the required information and amount");
		System.out.println("print books\t\tPrints all the books in the database");


	}

}
