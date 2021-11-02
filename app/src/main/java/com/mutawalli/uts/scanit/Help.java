package com.mutawalli.uts.scanit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {
    private Button mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);



        mBack = (Button) findViewById(R.id.about_btn_kembali);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView demo = findViewById(R.id.txt_tutor);
        String text1 = "Masih bingung dengan penggunaan aplikasi dan fitur yang ada? Lihat video demonya DISINI";
        SpannableString txt1 = new SpannableString(text1);
        ClickableSpan cc1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent go = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/UoOVJQmc6MA"));
                startActivity(go);
            }
        };
        txt1.setSpan(cc1, 81,87, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        demo.setText(txt1);
        demo.setMovementMethod(LinkMovementMethod.getInstance());

        TextView feed = findViewById(R.id.txt_feedback);
        String text2 = "Kirimkan feedback agar aplikasi ini dapat berkembang DISINI";
        SpannableString txt2 = new SpannableString(text2);
        ClickableSpan cc2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(Help.this, About.class));
            }
        };
        txt2.setSpan(cc2, 53,59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        feed.setText(txt2);
        feed.setMovementMethod(LinkMovementMethod.getInstance());
    }
}