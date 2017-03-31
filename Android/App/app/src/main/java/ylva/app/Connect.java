package ylva.app;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Ylva on 18-8-2016.
 *
 */
public class Connect  {

    private final static int port = 2026;
    private final static String ip = "asa.fawlty.nl";
    protected final static String USER_AGENT = "Mozilla/5.0";


    public static void testServer(){

        Log.d("Ylva","Testing server");
        try {
            URL url = new URL("http://" + ip + ":" + port + "/data");
            AsyncParam params = new AsyncParam(url, "Test", "Admin", "NoPass");
            AsyncConnect con = new AsyncConnect();
            con.execute(params);
        }catch(MalformedURLException ex){
            Log.d("Ylva","Error|"+ex.getMessage());
        }



    }

    public static String getDate(){
        try {
            URL url = new URL("http://" + ip + ":" + port + "/data");
            AsyncParam params = new AsyncParam(url, "GET:dbDate", "Admin", "NoPass");
            AsyncConnect con = new AsyncConnect();
            return con.execute(params).get();
        }catch(Exception ex){
            Log.d("Ylva","Error|"+ex.getMessage());
        }
        return "NULL";
    }
}
