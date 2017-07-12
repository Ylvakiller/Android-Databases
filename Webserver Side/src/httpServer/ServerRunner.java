package httpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServerRunner {
	static float version = 20;
	static Calendar cal = Calendar.getInstance();
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	public static HttpServer server;
	private final static int port = 2026;
	private static int connections = 0;
	static int error = 200;
	public ServerRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {

		System.out.println(version);
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
			error=200;
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


			connection.sendResponseHeaders(error, data.length);
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
			error = 400;
			System.out.println("errorCode: " + error + ".\n" + e1 + "\n");
			e1.printStackTrace();
		}
		String[] parts = stringQry.split(":");
		System.out.println(sdf.format(cal.getTime()) +"|\t" + "Querry contained " + parts.length + " parts");

		System.out.println(sdf.format(cal.getTime()) +"|\t" + "Someone entered the following querry:\n" + parts[0]);
		switch (parts[0]) {
			case "GET":
				switch (parts[1]) {
					case "DATE":
						reply = Communication.getDate(parts[2], parts[3]);
						break;
					default:
						reply = "Incorrect request send";
						error = 400;
				}
				break;
			case "POST":
				switch (parts[1]) {
					case "DATE":
						int response;
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date date = null;
						try {
							date = format.parse(parts[2]);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						response = Communication.setDateStorred(parts[3], parts[4], date);
						if (response == 1) {
							// lines are updated, atleast 1.
							error = 201;
						} else if (response == 0) {
							// 0 lines updated.
							error = 200;
						} else if (response == 2) {
							// error has been caught and send to console.
							error = 400;
						}
					default:
						reply = "Incorrect request send";
						error = 400;
						break;
				}
			case "TEST":

				System.out.println(sdf.format(cal.getTime()) + "|\t" + "Request was " + stringQry);
				break;

			default:
				reply = "Incorrect request send";
				error = 100;
				break;
		}
		System.out.println(sdf.format(cal.getTime()) +"|\t" + reply);
		return reply.getBytes();
	}

}
