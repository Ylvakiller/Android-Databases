package httpServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		Scanner keyboard = new Scanner(System.in);
		String commands[] = new String[10];
		commands[0] = "stop";
		commands[1] = "get date";
		commands[2] = "set date";
		commands[3] = "";
		while (true){
			String input = keyboard.nextLine();
			input=input.toLowerCase();
			switch (input){
			case "stop":
				ServerRunner.server.stop(0);
				keyboard.close();
				System.exit(0);
				break;
			case "get date":
				System.out.println(Communication.getDate(User, pwd));
				break;
			case "set date":
				System.out.println("Please input the date you want.");
				String date = keyboard.nextLine();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date dateObject;
				try {
					dateObject = format.parse(date);
					System.out.println("Date parsed as \t" + dateObject.toString());
					System.out.println(Communication.setDateStorred(User, pwd, format.format(dateObject)));
				} catch (ParseException e) {
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
				
			break;
			default:
				System.err.println("Command not found.\nCommand given: \t" +input);
				break;
			}
		}
	}

	public static void close() {
		System.out.println("Server is shutting down");
		ServerRunner.server.stop(0);
		System.exit(0);
	}

}
