package com.mutawalli.uts.scanit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mBack = (Button) findViewById(R.id.about_btn_kembali);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void sendemail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,new String[] { "191111041@mhs.stiki.ac.id" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Program");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void openfb(View view) {
        Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/MasSakti477"));
        startActivity(go);
    }

    public void openig(View view) {
        Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mas.sakti_/"));
        startActivity(go);
    }
}