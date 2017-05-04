package ylva.app;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ylva on 04/05/2017.
 */
public class PublicKey extends AsyncTask {
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
            wr.writeBytes("PublicKey");
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Log.d("Ylva",("Sending 'POST' to : " + url));
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
}
