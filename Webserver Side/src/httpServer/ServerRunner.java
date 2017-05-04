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
import java.util.Base64;

public class ServerRunner {

	public static HttpServer server;
	private final static int port = 2026;
	private static int connections = 0;
	public static KeyPair key;
	public ServerRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		ConsoleCommands console = new ConsoleCommands();
		console.start();
		String address = InetAddress.getLocalHost().getHostAddress();
		//address = "asa.fawlty.nl";
		address = "192.168.0.105";
		System.out.println(address);
		key = Encryption.generateKeyPair();
		System.out.println("Key genned");
		server = HttpServer.create(new InetSocketAddress(address,port), 0);
		/*HttpContext context = server.createContext("/test");
		context.getFilters().add(new ParameterFilter());*/
		System.out.println(server.getAddress().getAddress().getHostAddress());
		server.createContext("/data", new InfoHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("Server Started");
		System.out.println("Public key");
		System.out.println(key.getPublic().toString());
		
	}

	static class InfoHandler implements HttpHandler {
		public void handle(HttpExchange connection) throws IOException {
			System.out.println("someone is connecting");
			byte[] data = "Leave me alone!".getBytes();
			
			connections++;
			System.out.println("This is connection number = " + connections);
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
		/*Headers reqHeaders = exchange.getRequestHeaders();
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
		
		return out;*/
	}
	
	/**
	 * This method will distinguish  between the different commands send in the htmlparameters
	 * @param qry the raw parameters
	 */
	private static byte[] querryhandler(byte[] qry){
		String Stringqry = "";
		try {
			Stringqry = new String(qry, "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.out.println("Raw Querry \t|"+ new String(qry,"ISO-8859-1" ).replace("\n", "").replace("\r", "") + "|" );
			System.out.println("Length " + new String(qry,"ISO-8859-1" ).replace("\n", "").replace("\r", "").length());
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (Stringqry.contains("PublicKey")){
			System.out.println("Recognised request for public key");
			return key.getPublic().getEncoded();
		}else{
			try {
				System.out.println("non base 64 version" + Base64.getMimeDecoder().decode(new String(qry,"ISO-8859-1" ).replace("\n", "").replace("\r", "")));
				Stringqry = new String (Encryption.decrypt(Base64.getMimeDecoder().decode(qry)), "ISO-8859-1");
				System.out.println("Decrypted: " + Stringqry);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(qry);
		if (Stringqry.contains(":")){
			//Invalid Command
			return "Invalid command request".getBytes();
		}else{
			String result = "Invalid command request";
			//Possible command found
			String type = Stringqry.substring(0, Stringqry.indexOf(":"));
			System.out.println("Found command:" + type);
			switch (type){
			case "POST":
				System.out.println("recognised POST");
				result = Base64.getEncoder().encodeToString(ServerRunner.key.getPublic().getEncoded());
				 //result = getHandler(qry.substring(qry.indexOf(":")+1));
				break;
			default:
				break;
				
			}
			return result.getBytes();
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
		System.out.println("Raw querry = \t" + message);
		System.out.println("End of Message");
		try {
			//message = Encryption.decrypt("",message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("decrypted querry = \t|" + message);
		
		return message;
	}
}
