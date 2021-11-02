package com.mutawalli.uts.scanit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    finish();
                    Intent m = new Intent(com.mutawalli.uts.scanit.SplashScreen.this, MainActivity.class);
                    startActivityForResult(m,0);
                }
            }
        };
        timer.start();
    }
}