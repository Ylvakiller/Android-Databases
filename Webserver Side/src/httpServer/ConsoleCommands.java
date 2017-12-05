package httpServer;

import java.util.Scanner;

public class ConsoleCommands extends Thread {

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
		System.out.println("Console command interperenter starting");
		Scanner keyboard = new Scanner(System.in);
		String commands[] = new String[10];
		commands[0] = "stop";
		commands[1] = "Get Date";
		commands[2] = "Set Date";
		while (true){
			String input = keyboard.nextLine();
			switch (input){
			case "get date":
				System.out.println("Getting Date");
				System.out.println(Communication.getDate("AndroidServer", ""));
				break;
			}
			if (input.equalsIgnoreCase(commands[0])){//Termination command
				ServerRunner.server.stop(0);
				keyboard.close();
				System.exit(0);
			}else if (input.equalsIgnoreCase(commands[1])){
				System.out.println("Getting Date");
				System.out.println(Communication.getDate("AndroidServer", ""));

			}else {
				System.err.println("Command not found.\nCommand given: \t" +input);
			}
		}
	}
	
	public static void close() {
		System.out.println("Server is shutting down");
		ServerRunner.server.stop(0);
		System.exit(0);
	}

}
