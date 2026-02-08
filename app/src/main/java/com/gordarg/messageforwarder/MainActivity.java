package com.gordarg.messageforwarder;

import androidx.appcompat.app.AppCompatActivity;

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
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        b = findViewById(R.id.add);
        etTo = findViewById(R.id.etTo);
        etFrom = findViewById(R.id.etFrom);

        b.setOnClickListener(v -> {
            mydb.insertForwarder(
                    etFrom.getText().toString(),
                    etTo.getText().toString()
            );
            Toast.makeText(this, "آیتم اضافه شد", Toast.LENGTH_SHORT).show();
            ReloadList();
        });

        ReloadList();
    }

    private void ReloadList() {
        ArrayList<Forwarder> arrayList = mydb.getAllForwarders();
        MainActivityAdapter arrayAdapter = new MainActivityAdapter(this, arrayList);
        lv = findViewById(R.id.main_list);
        lv.setAdapter(arrayAdapter);
    }
}