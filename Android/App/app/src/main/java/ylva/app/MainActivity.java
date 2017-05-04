package ylva.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Ylva","App started");
        context = this.getApplicationContext();

        super.onCreate(savedInstanceState);
        Log.d("Ylva", "Generating Keypair");
        try {
            KeyPair generatedKeyPair = Encryption.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        Log.d("Ylva", "Getting public key from server");
        PublicKeyGetter key = new PublicKeyGetter();
        Log.d("Ylva", "Obtained Public Key" + key.execute());
        Log.d("Ylva", "Testing key");
        Connect.testServer();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button BtnServerCheck;
        BtnServerCheck = (Button)findViewById(R.id.btnServerCheck);
        BtnServerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect.testServer();
            }
        });
        Button btnDate = (Button)findViewById(R.id.btnDate);
        //btnDate.setText(Connect.getDate());//Set the date on the button in the bottom to always be the last date stored in the database
        btnDate.setText("test");

        //RelativeLayout pb = (RelativeLayout)findViewById(R.id.loadingPanel);
        //pb.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContext(){
        return context;
    }
}
