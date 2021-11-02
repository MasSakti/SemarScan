package com.mutawalli.uts.scanit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    private CardView scan,gene,about,help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan = findViewById(R.id.menu_scan);
        gene = findViewById(R.id.menu_generate);
        about = findViewById(R.id.menu_about);
        help = findViewById(R.id.menu_help);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.mutawalli.uts.scanit.MainActivity.this, Scan.class);
                startActivity(intent);
            }
        });

        gene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.mutawalli.uts.scanit.MainActivity.this, Generate.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.mutawalli.uts.scanit.MainActivity.this, About.class);
                startActivity(intent);
            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.mutawalli.uts.scanit.MainActivity.this, Help.class);
                startActivity(intent);
            }
        });
    }
}