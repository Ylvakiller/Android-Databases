package ylva.uid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class Connect_Screen extends AppCompatActivity {

    private BluetoothAdapter bluetooth;
    private Set<BluetoothDevice> pairedDevices;
    Button btnPaired;
    ListView deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect__screen);
        btnPaired = (Button)findViewById(R.id.button);
        deviceList = (ListView)findViewById(R.id.listView);
        TestBluetooth();

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pairedDevicesList();
            }
        });

        Log.d("Ylva", Build.MANUFACTURER);


    }

    public void TestBluetooth(){
        bluetooth = BluetoothAdapter.getDefaultAdapter();
        if(bluetooth==null){
            //Makes sure that the button is disabled when there is not bluetooth possible on the device
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
            btnPaired.setEnabled(false);
        }else{
            //Check if bluetooth is enabled
            if (!bluetooth.isEnabled()){
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon,1);
            }
        }
    }

    public void pairedDevicesList(){
        pairedDevices = bluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        if (pairedDevices.size()>0){
            for (BluetoothDevice bt : pairedDevices){
                list.add(bt.getName()+"\n"+bt.getAddress());//Store the device name and address
            }
        }else{
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(myClickListener);
        bluetooth.cancelDiscovery();
        Log.d("Ylva", "Bluetooth states are " +bluetooth.getState()+ "|" + bluetooth.getScanMode()+ "|" + bluetooth.isDiscovering());
    }

    private AdapterView.OnItemClickListener myClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView av, View v, int arg2, long arg3){
            Toast.makeText(getApplicationContext(),"I am sorry, I was unable to implement bluetooth connections", Toast.LENGTH_LONG).show();
            String info = ((TextView)v).getText().toString();
            String address = info.substring(info.length()-17);

            Intent intent = new Intent(Connect_Screen.this, MainActivity.class);

            intent.putExtra("test", address);
            startActivity(intent);
        }
    };
}
