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
        /*try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;*/
        //int temp = (int)str;
        Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
        String encrypted = encryption.encryptOrNull(str);
        Log.i("Ylva", "Ecnrypted :" + encrypted);

        return encrypted;
    }
}