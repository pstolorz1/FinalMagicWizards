package com.example.tomek.magicwizards;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
public class AI extends AppCompatActivity {
    static public boolean flag;
    TextView view_data;
    Integer easy()
    {
        int x = (int)(Math.random() * 49 + 1);
        return(x);
    }

    Integer hard()
    {
        int x = (int)(Math.random() * 99 + 1);
        return(x);
    }

    Integer ez(int x){return x;}

}
