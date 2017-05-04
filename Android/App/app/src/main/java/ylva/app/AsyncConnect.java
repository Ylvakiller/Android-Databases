package ylva.app;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.IllegalBlockSizeException;

/**
 * Created by Ylva on 18-8-2016.
 *
 */

/**
 * This class will do the actaul communication with the webserver, using the parameters in AsyncParam
 * This is meant for database connection only, not for getting the key
 */
public class AsyncConnect extends AsyncTask<AsyncParam, Void, String> {
    @Override
    protected String doInBackground(AsyncParam... parameters) {
        String reply = "";
        Log.d("Ylva","Async task started");
        try {
            URL url = parameters[0].url;
            //URL url = new URL("https://selfsolve.apple.com/wcResults.do");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //HTTP request header
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", Connect.USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


            String urlParameters = parameters[0].message;
            Log.i("Ylva", "urlParameters :" + urlParameters);
            urlParameters = encrypt(urlParameters);
            Log.d("Ylva","Attempting to decrypt url param" + decrypt(urlParameters));
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Log.d("Ylva",("Sending 'Get' to : " + url));
            Log.d("Ylva",("Response code " + responseCode));


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                Log.d("Ylva",((strTemp)));
                reply = strTemp;
            }
            br.close();
        } catch (Exception ex) {
            Log.d("Ylva","Error|"+ex.getMessage());
        }

        Long Status = new Long(0);
        return reply;
    }

    protected void onProgressUpdate() {
    }

    protected void onPostExecute() {
    }

    public String encrypt(String str) {

        String encrypted = null;
        try {
            //encrypted = Encryption.encrypt("", str);
        } catch (Exception e) {
            Log.d("Ylva","Error|"+e.getMessage());
        }
        Log.i("Ylva", "Ecnrypted :" + encrypted);

        return encrypted;
    }
    public String decrypt(String str) {

        String decrypt = null;
        try {
           // decrypt = Encryption.decrypt("", str);
        } catch (Exception e) {
            Log.d("Ylva","Error|"+e.getMessage());
        }
        Log.i("Ylva", "decrypt :" + decrypt);

        return decrypt;
    }
}