package com.example.tomek.magicwizards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MenuCredits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
