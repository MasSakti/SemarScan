package com.mutawalli.semar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.mutawalli.semar.R;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;

    MyDatabaseHelper myDB;
    ArrayList<String> qrc_id, qrc_name;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);

        myDB = new MyDatabaseHelper(History.this);
        qrc_id = new ArrayList<>();
        qrc_name = new ArrayList<>();

        displayData();

        customAdapter = new CustomAdapter(History.this, qrc_id, qrc_name);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));
    }

    void displayData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                qrc_id.add(cursor.getString(0));
                qrc_name.add(cursor.getString(1));
            }
        }
    }
}