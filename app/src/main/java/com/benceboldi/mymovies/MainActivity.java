package com.benceboldi.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //TODO perszisztens userTime
    public static float userTime = 0;
    private DatabaseReference reference;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<Movies> options;
    private FirebaseRecyclerAdapter<Movies, MyHolder> adapter;
    TextView userTimeView;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference();

        myDialog = new Dialog(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);

        options = new FirebaseRecyclerOptions.Builder<Movies>().setQuery(reference, Movies.class).build();
        adapter = new FirebaseRecyclerAdapter<Movies, MyHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyHolder holder, final int position, @NonNull Movies model) {

                //Hozzárendeljük az adatokat a recyclerView-ba a model és a holder segítségével
                holder.mTitle.setText(model.getTitle());
                Picasso.get().load(model.getImg()).into(holder.mImageView);
                holder.mDes.setText(model.getDescription());

                //Minden egyes filmre, ha rálépünk, átmegyünk a cardActivityre, ahol több adatot mutatunk
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, CardActivity.class);
                        //Megszerezzük a rálépett filmnek az azonosítóját, ez a MovieKey
                        intent.putExtra("MovieKey", getRef(position).getKey());
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                return new MyHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

        //Feldobja az activity_poput-ot, ha rálépünk a FAB-ra, majd kiszámolja a filmnézéssel töltött időt
        myDialog.setContentView(R.layout.activity_popup);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float days, hours;
                days = userTime / 60 / 24;
                hours = userTime / 60;
                String fDays = String.format(Locale.ENGLISH,"%.2f", days);
                String fHours = String.format(Locale.ENGLISH,"%.2f", hours);

                userTimeView = myDialog.findViewById(R.id.userTimeId);
                userTimeView.setText(getString(R.string.time, fHours, fDays));
                myDialog.show();
            }
        });

    }
}
