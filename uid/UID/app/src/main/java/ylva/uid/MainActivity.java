package ylva.uid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button firstButton = (Button) findViewById(R.id.firstButton);
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                console("Opening the bluetooth list");
                Intent intent = new Intent(MainActivity.this, Connect_Screen.class);
                startActivity(intent);
            }
        });

        Button webButton = (Button) findViewById(R.id.webButton);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                console("Opening webserver activity");
            }
        });

    }


    private void console(String s){
        Log.d("Ylva",s);
    }
}
