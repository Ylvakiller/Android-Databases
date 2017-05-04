package ylva.app;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Ylva on 04/05/2017.
 */

public class PublicKeyGetter extends AsyncTask {
    public static PublicKey serverKey;

    @Override
    protected String doInBackground(Object[] objects) {
        String reply = "";
        Log.d("Ylva","Async task started");
        try {
            URL url = new URL("http://" + Connect.ip + ":" + Connect.port + "/data");
            //URL url = new URL("https://selfsolve.apple.com/wcResults.do");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //HTTP request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", Connect.USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");



            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("PublicKeyGetter");
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Log.d("Ylva",("Sending 'POST' to : " + url));
            Log.d("Ylva",("Response code " + responseCode));


            InputStream br = (con.getInputStream());
            byte[] encKey = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead=br.read(encKey))!= -1){
                output.write(encKey, 0, bytesRead);
            }

            encKey = output.toByteArray();
            br.close();

            //byte[]temp= Base64.decode(encKey);
            Log.d("Ylva", "Attempting to convert the following into key");
            Log.d("Ylva", encKey.toString() + "\n");
            PublicKeyGetter.serverKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encKey));
            Log.d("Ylva", serverKey.toString());
            Log.d("Ylva", "Saved a new public key");
            Log.d("Ylva", "Testing a message");
            test();


        } catch (Exception ex) {
            Log.d("Ylva","Error|"+ex.getMessage());
        }

        Long Status = new Long(0);
        return reply;
    }
    private static void test(){
        try {
            URL url = new URL("http://" + Connect.ip + ":" + Connect.port + "/data");

            //URL url = new URL("https://selfsolve.apple.com/wcResults.do");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //HTTP request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", Connect.USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            byte[] toSend = Encryption.encrypt(serverKey, "Test".getBytes());
            Log.d("Ylva", Integer.toString(toSend.length));
            Log.d("Ylva", "Encrypted test \n" + new String(toSend, "ISO-8859-1"));
            Log.d("Ylva", "Base 64 encodeded test: " + Base64.encodeToString(toSend, Base64.DEFAULT));
            Log.d("Ylva", "Base64length " +  Base64.encodeToString(toSend, Base64.DEFAULT).length());
            wr.write(Base64.encodeToString(toSend, Base64.DEFAULT).getBytes());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Log.d("Ylva", ("Sending 'POST' to : " + url));
            Log.d("Ylva", ("Response code " + responseCode));


            InputStream br = (con.getInputStream());
            byte[] encKey = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = br.read(encKey)) != -1) {
                output.write(encKey, 0, bytesRead);
            }

            encKey = output.toByteArray();
            br.close();
            Log.d("Ylva", "response/n" + new String(encKey,"ISO-8859-1" ));
        }catch (Exception e){

        }
    }
}
