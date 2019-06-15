package com.example.tomek.magicwizards;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendingTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_test);
        Button b = findViewById(R.id.testSending);
        // definicja bazy danych
        final DatabaseReference fbDb = FirebaseDatabase.getInstance().getReference();

        // klikniecie przycisku testowego
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // bierze klucz wygenerowany automatycznie przez baze , jesli nie ma tablicy o nazwie "ciosy tworzy ją"
                String key = fbDb.child("ciosy").push().getKey();
                // tworzenie nwoego obiektu
                TestObject to = new TestObject(key, 99);
                // ddoawanie teo obiektu do bazy pod klcuzem wygenerowanym wczesniej
                fbDb.child("ciosy").child(key).setValue(to);

            }
        });

        //nasluchuje czy wartosci w tablicy "ciosy" zostaly zmienione (a wlasciwie czy zostala dodana nowa wartosc)
        fbDb.child("ciosy").addValueEventListener(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // getChildrenCount zwraca ilosc wpisow w tablicy "ciosy"
                long test = dataSnapshot.getChildrenCount();
                //cos jakby lista rekordow z tablicy
                Iterable<DataSnapshot> dejta = dataSnapshot.getChildren();
                String temp = "";
                // petla ktora iteruje opo kazdym rekordzie z bazy
                for(DataSnapshot d : dejta)
                {
                    // wrzucanie do obiektu t rekordu z bazy
                    TestObject t = d.getValue(TestObject.class);
                    // obiekt jest juz gotowy , tu tylko dodaje pole val do stringa zeby go wyswietlic
                    temp += "READ: " + t.val + " |\n";
                }
                TextView t = findViewById(R.id.readDataText);
                //wyswietlanie lcizby rekordow i cih wartosci
                t.setText("Liczba obiektów = " + test + "\n" + temp);
            }
            // w przypadku bledu wyswietla toasta
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(),"Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
