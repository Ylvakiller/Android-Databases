package httpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerRunner {

	private final static int port = 8000;
	private static int connections = 0;
	private static int maxConnections = 1;

	public ServerRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress("192.168.0.101",port), 0);
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
			System.out.println("someone is connecting!");
			String data = "Leave me alone!";
			System.out.println("Amount connections = " + connections);
			connections++;
			if (connections>=maxConnections){
				data = "Last allowed connection";
			}
			try {
				getParameters(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			t.sendResponseHeaders(200, data.length());
			OutputStream os = t.getResponseBody();
			os.write(data.getBytes());
			os.close();
			if (connections>=maxConnections){
				System.exit(0);
			}
			
		}
	}
	
	
	private static void getParameters(HttpExchange exchange) throws Exception{
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
		System.out.println(qry);
		
	}
}
