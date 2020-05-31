package com.benceboldi.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<Movies> options;
    private FirebaseRecyclerAdapter<Movies, MyHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<Movies>().setQuery(reference, Movies.class).build();
        adapter = new FirebaseRecyclerAdapter<Movies, MyHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull Movies model) {
                holder.mTitle.setText(model.getTitle());
                Picasso.get().load(model.getImg()).into(holder.mImageView);
                holder.mDes.setText(model.getDescription());
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


    }
}
