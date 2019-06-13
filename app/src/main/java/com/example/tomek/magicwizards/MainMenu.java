package com.example.tomek.magicwizards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//!  Klasa obslugujaca menu
/*!
 *
 */
public class MainMenu extends AppCompatActivity {

    //!  guzik do wlaczenia gry
    private Button pressPlay; /**< guzik do wlaczenia gry*/
    //!  guzik do pokazania tworcow gry
    private Button pressCredits; /**< guzik do pokazania tworcow gry*/

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
    //!  Funkcja przenoszaca z jednego activity do drugiego
     /*
     */
    public void openMainMenu(){
        Intent intent = new Intent(this, ConnectMenu.class);
        startActivity(intent);
    }
    //!  Funkcja przenoszaca z jednego activity do drugiego
     /*
     */
    public void openCredits(){
        Intent intent = new Intent(this, CreditsScene.class);
        startActivity(intent);
    }
}
