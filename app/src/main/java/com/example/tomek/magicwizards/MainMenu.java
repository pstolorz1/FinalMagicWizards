package com.example.tomek.magicwizards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    private Button pressPlay;
    private Button pressCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        pressPlay = (Button) findViewById(R.id.button2);
        pressPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                openMainMenu();
            }
        });

        pressCredits = (Button) findViewById(R.id.button);
        pressCredits.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                openCredits();
            }
        });
    }
    //Funkcja przenoszaca z jednego activity do drugiego
    public void openMainMenu(){
        Intent intent = new Intent(this, ConnectMenu.class);
        startActivity(intent);
    }
    public void openCredits(){
        Intent intent = new Intent(this, CreditsScene.class);
        startActivity(intent);
    }
}
