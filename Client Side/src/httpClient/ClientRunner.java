package httpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientRunner {

	private final static int port = 2026;
    private final static String ip = "asa.fawlty.nl";
    private final static String USER_AGENT = "Mozilla/5.0";
    
	public ClientRunner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int i = 0;
		while(i<10){
		try {
            URL url = new URL("http://" + ip + ":" + port + "/data");
			//URL url = new URL("https://selfsolve.apple.com/wcResults.do");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            
            //HTTP request header
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            
            
            String urlParameters = "These are random";
            
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            
            int responseCode = con.getResponseCode();
            System.out.println("Sending 'Get' to : " + url);
            System.out.println("Response code " + responseCode);
            
            
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                System.out.println(strTemp);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        i++;	
        }

	}

}
