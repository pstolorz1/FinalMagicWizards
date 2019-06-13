package com.example.tomek.magicwizards;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
/**  Klasa reprezentujaca poziomy sztucznej inteligencji
 */
public class AI extends AppCompatActivity {
    static public boolean flag;
    TextView view_data;

    /** \brief Latwy poziom AI
     *
     */
    Integer easy()
    {
        int x = (int)(Math.random() * 49 + 1); /**< Wartosci, ktore moze losowac komputer w zaleznosci od poziomu trudnosci*/
        return(x);
    }

    /** \brief Trudny poziom AI
     *
     */
    Integer hard()
    {
        int x = (int)(Math.random() * 49 + 50); /**< Wartosci, ktore moze losowac komputer w zaleznosci od poziomu trudnosci*/
        return(x);
    }

    Integer ez(int x){return x;}

}
