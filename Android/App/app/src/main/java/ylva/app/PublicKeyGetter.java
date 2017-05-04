package ylva.app;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
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
            byte[] encKey = new byte[br.available()];
            br.read(encKey);
            br.close();


            Log.d("Ylva", "Attempting to convert the following into key");
            //Log.d("Ylva", Base64.decode(encKey, Base64.NO_WRAP));
            PublicKeyGetter.serverKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(encKey, Base64.NO_WRAP)));
            Log.d("Ylva", "Saved a new public key");

        } catch (Exception ex) {
            Log.d("Ylva","Error|"+ex.getMessage());
        }

        Long Status = new Long(0);
        return reply;
    }
}
