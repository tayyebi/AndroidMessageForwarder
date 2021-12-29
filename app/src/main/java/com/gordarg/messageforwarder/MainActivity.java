package com.gordarg.messageforwarder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gordarg.messageforwarder.data.DBHelper;
import com.gordarg.messageforwarder.model.Forwarder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private Button b;
    EditText etFrom, etTo;
    DBHelper mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // wait for sms (alternative to manifest)
//        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
//        filter.setPriority(9999);
//        registerReceiver(new SmsReceiver(),filter);

        // call the database
        mydb = new DBHelper(this);

        // find items on the view
        b = findViewById(R.id.add);
        etTo = findViewById(R.id.etTo);
        etFrom = findViewById(R.id.etFrom);

        // When clicked on the 'add' button
        b.setOnClickListener(v -> {
            mydb.insertForwarder(
                    etFrom.getText().toString(),
                    etTo.getText().toString()
            );
            Toast.makeText(this, "آیتم اضافه شد", Toast.LENGTH_SHORT).show();
            ReloadList();
        });

        // Reload the main list
        ReloadList();

         startService(new Intent(this, SmsService.class));
    }

    private void ReloadList(){
        // fetch data from database
        ArrayList<Forwarder> arrayList = mydb.getAllForwarders();

        // insert into list
        MainActivityAdapter arrayAdapter = new MainActivityAdapter(this, arrayList);
        lv = (ListView) findViewById(R.id.main_list);
        lv.setAdapter(arrayAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, SmsService.class));
    }
}