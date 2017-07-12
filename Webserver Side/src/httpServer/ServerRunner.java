package httpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

public class ServerRunner {
	static Calendar cal = Calendar.getInstance();
    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
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
		address = "192.168.0.105";
		System.out.println(sdf.format(cal.getTime()) + "|\t" + "address");
		server = HttpServer.create(new InetSocketAddress(address,port), 0);
		System.out.println(sdf.format(cal.getTime()) +"|\t" + server.getAddress().getAddress().getHostAddress());
		server.createContext("/data", new InfoHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println(sdf.format(cal.getTime()) +"|\t" + "Server Started");
		
	}

	static class InfoHandler implements HttpHandler {
		public void handle(HttpExchange connection) throws IOException {
			System.out.println(sdf.format(cal.getTime()) +"|\t" + "someone is connecting");
			byte[] data = "Leave me alone!".getBytes();
			
			connections++;
			System.out.println(sdf.format(cal.getTime()) +"|\t" + "This is connection number = " + connections);
			try {
				data = querryhandler(getParameters(connection));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			connection.sendResponseHeaders(200, data.length);
			OutputStream os = connection.getResponseBody();
			os.write(data);
			os.close();
			
		}
	}
	
	/**
	 * Gets the HTTP paramaters from the given HttpExchange (connection)
	 * @param exchange
	 * @return
	 * @throws Exception
	 */
	private static byte[] getParameters(HttpExchange exchange) throws Exception{
		InputStream br = exchange.getRequestBody(); 
		byte[] encKey = new byte[8192];
         int bytesRead;
         ByteArrayOutputStream output = new ByteArrayOutputStream();
         while ((bytesRead=br.read(encKey))!= -1){
             output.write(encKey, 0, bytesRead);
         }
         encKey = output.toByteArray();
         br.close();
         return encKey;
	}
	
	/**
	 * This method will distinguish  between the different commands send in the htmlparameters and excecute the correct request
	 * @param qry the raw parameters
	 */
	private static byte[] querryhandler(byte[] qry){
		String stringQry = "";
		String reply = "Something went wrong";
		try {
			stringQry = new String(qry, "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] parts = stringQry.split(":");
		System.out.println(sdf.format(cal.getTime()) +"|\t" + "Querry contained " + parts.length + " parts");
		if (parts.length!=3){
			reply="There is an incorrect amount of seperators found, please do not use : in a message";
			return reply.getBytes();
		}
		System.out.println(sdf.format(cal.getTime()) +"|\t" + "Someone entered the following querry:\n" + parts[0]);
		switch (parts[0]){
		case "GET":
			switch (parts[1]){
			case "DATE":
			reply = Communication.getDate(parts[2],parts[3]);
			break;
			default:
				reply = "Incorrect request send";	
			}
			break;
		case "POST":
		case "DATE":

			System.out.println(sdf.format(cal.getTime()) +"|\t" + "Request was " + stringQry);
			break;
			
		default:
			reply = "Incorrect request send";
			break;
		}
		System.out.println(sdf.format(cal.getTime()) +"|\t" + reply);
		return reply.getBytes();
	}
	
}
