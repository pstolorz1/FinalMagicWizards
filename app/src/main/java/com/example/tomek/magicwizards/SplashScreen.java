package com.example.tomek.magicwizards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        CustomGesturesActivity.SetOpenGLCooldown();
        Thread logoTimer = new Thread() {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(),CustomGesturesActivity.class);
                    startActivity(intent);
                    finish();
                }catch(InterruptedException error)
                {
                    error.printStackTrace();
                }
            }


        };
        logoTimer.start();

    }
}
