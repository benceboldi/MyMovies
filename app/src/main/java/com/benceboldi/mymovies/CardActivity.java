package com.benceboldi.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CardActivity extends AppCompatActivity {

    private float addedDur;
    private ImageView imageView;
    TextView titleView, descView, directorView, durationView, genreView, languageView;
    Button btnAdd;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        imageView = findViewById(R.id.imageActId);
        titleView = findViewById(R.id.titleActId);
        descView = findViewById(R.id.descActId);
        directorView = findViewById(R.id.directorId);
        durationView = findViewById(R.id.durationId);
        genreView = findViewById(R.id.genreId);
        languageView = findViewById(R.id.languageId);
        btnAdd = findViewById(R.id.btnAdd);

        reference = FirebaseDatabase.getInstance().getReference();

        //MovieKey a rálépett filmnek az azonosítója
        String MovieKey = getIntent().getStringExtra("MovieKey");

        reference.child(MovieKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Ha léteznek adatok az adatbázisban, akkor kiszedjük az összes szükséges mezőt, majd beállítjuk őket
                if (dataSnapshot.exists()){
                    String img = dataSnapshot.child("img").getValue().toString();
                    String title = dataSnapshot.child("title").getValue().toString();
                    String description = dataSnapshot.child("description").getValue().toString();
                    String director = dataSnapshot.child("director").getValue().toString();
                    String duration = dataSnapshot.child("duration").getValue().toString();
                    String genre = dataSnapshot.child("genre").getValue().toString();
                    String language = dataSnapshot.child("language").getValue().toString();

                    addedDur = Integer.parseInt(duration);

                    // res/values/strings.xml-ben találhatóak a flavor textek
                    Picasso.get().load(img).into(imageView);
                    titleView.setText(title);
                    descView.setText(description);
                    directorView.setText(getString(R.string.directorS, director));
                    durationView.setText(getString(R.string.durationS, duration));
                    genreView.setText(getString(R.string.genreS, genre));
                    languageView.setText(getString(R.string.languageS, language));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Hozzáadjuk az adott film idejét a userTime-hoz, mikor rálépünk az Add gombra
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.userTime += addedDur;
                Toast.makeText(CardActivity.this, "Movie added, check your profile for stats!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
