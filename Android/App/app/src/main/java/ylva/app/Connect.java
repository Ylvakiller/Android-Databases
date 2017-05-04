package ylva.app;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Ylva on 18-8-2016.
 *
 */
public class Connect  {

    protected final static int port = 2026;
    protected final static String ip = "82.72.107.157";
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
    public static String getPublicKey(){
        try {
            URL url = new URL("http://" + ip + ":" + port + "/data");
            AsyncParam params = new AsyncParam(url, "Key", "Admin", "NoPass");
            AsyncConnect con = new AsyncConnect();
            return con.execute(params).get();
        }catch(Exception ex){
            Log.d("Ylva","Error|"+ex.getMessage());
        }
        return "NULL";
    }

    /**
     * This method is used to write something to the key file, it is just for testing, should not be used once the app is in use
     * @param data the data to be written
     */
    private static void writeToFile(String data) {
        //Log.d("Ylva", getExternalStorageState());
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d("Ylva", "Found storage " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
            try {
                File folder= new File(MainActivity.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),"Key");
                if (!folder.exists()){
                    folder.mkdirs();
                    Log.d("Ylva", "Created the Key folder");
                }
                File file = new File(folder.getAbsolutePath() + "/Key.txt");
                if (!file.exists()) {
                    Log.d("Ylva", "Creating file at " + file.getAbsolutePath());
                    file.createNewFile();
                }else{
                    Log.d("Ylva", "file already exists");
                }
                FileOutputStream output = new FileOutputStream(file);
                output.write(data.getBytes());
                output.close();
            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }else{
            Log.d("Ylva", "Did not find any storage :O");
        }

    }
}
