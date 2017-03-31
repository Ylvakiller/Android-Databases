package httpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerRunner {

	public static HttpServer server;
	private final static int port = 2026;
	private static int connections = 0;

	public ServerRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		ConsoleCommands console = new ConsoleCommands();
		console.start();
		String address = InetAddress.getLocalHost().getHostAddress();
		//address = "asa.fawlty.nl";
		address = "192.168.0.101";
		System.out.println(address);
		server = HttpServer.create(new InetSocketAddress(address,port), 0);
		/*HttpContext context = server.createContext("/test");
		context.getFilters().add(new ParameterFilter());*/
		System.out.println(server.getAddress().getAddress().getHostAddress());
		server.createContext("/data", new InfoHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("Server Started");
		
	}

	static class InfoHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			System.out.println("someone is connecting");
			String data = "Leave me alone!";
			
			connections++;
			System.out.println("This is connection number = " + connections);
			try {
				data = decrypt(querryhandler(getParameters(t)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			t.sendResponseHeaders(200, data.length());
			OutputStream os = t.getResponseBody();
			os.write(data.getBytes());
			os.close();
			
		}
	}
	
	
	private static String getParameters(HttpExchange exchange) throws Exception{
		Headers reqHeaders = exchange.getRequestHeaders();
		String contentType = reqHeaders.getFirst("Content-Type");
		String encoding = "ISO-8859-1";
		
		// read the query string from the request body
		String qry;
		InputStream in = exchange.getRequestBody();
		try {
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    byte buf[] = new byte[4096];
		    for (int n = in.read(buf); n > 0; n = in.read(buf)) {
		        out.write(buf, 0, n);
		    }
		    qry = new String(out.toByteArray(), encoding);
		} finally {
		    in.close();
		}
		
		return qry;
	}
	
	/**
	 * This method will distinguish  between the different commands send in the htmlparameters
	 * @param qry the raw parameters
	 */
	private static String querryhandler(String qry){
		if (!qry.contains(":")){
			//Invalid Command
			return "Invalid command request";
		}else{
			String result = "Invalid command request";
			//Possible command found
			String type = qry.substring(0, qry.indexOf(":"));
			System.out.println("Found command:" + type);
			switch (type){
			case "GET":
				System.out.println("recognised Get");
				 result = getHandler(qry.substring(qry.indexOf(":")+1));
				break;
			case "SET":
				
			}
			return result;
		}
	}
	
	private static String getHandler(String command){
		String returnString = command;
		System.out.println(command);
		System.out.println("Obtained:GET " + command);
		switch (command){//Switch to see what needs to be gotten
		case "dbDate":
			returnString = Communication.getDate();
			break;
		}
		return returnString;
	}
	
	private static String decrypt(String message){
		System.out.println("Raw querry = \t|" + message);
		//Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
		//message = encryption.decryptOrNull(message);
		System.out.println("decrypted querry = \t|" + message);
		
		return message;
	}
}
