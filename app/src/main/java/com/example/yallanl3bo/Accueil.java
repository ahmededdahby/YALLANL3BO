package com.example.yallanl3bo;

import static androidx.paging.PagedList.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yallanl3bo.models.matchItem;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Accueil extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestorePagingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewMatchs);
        //rechercher elements de la bd

        Query query = firebaseFirestore.collection("matchs");

        Config config = new Config.Builder()
                .setInitialLoadSizeHint(5)
                .setPageSize(3)
                .build();

        //Creation d'un adapter
        FirestorePagingOptions<matchItem> options = new FirestorePagingOptions.Builder<matchItem>()
                .setQuery(query,config,matchItem.class)
                .build();
        adapter = new FirestorePagingAdapter<matchItem, matchViewHolder>(options) {
            @NonNull
            @Override
            public matchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_matchs,parent,false);
                return new matchViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull matchViewHolder holder, int i, @NonNull matchItem model) {

                holder.heure.setText(model.getHeure());
                holder.cat.setText(model.getCategory());
                // holder.places.setText(model.getPlaces() + "");
                holder.terrain.setText(model.getTerrain());
                holder.prix.setText(model.getPrix());
                holder.duree.setText(model.getDuree());




            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);
                switch (state){
                    case LOADING_INITIAL:
                        Log.d("Loaded log","initial");
                        break;
                    case FINISHED:
                        Log.d("Loaded log","loaded:all");
                        break;
                    case LOADED:
                        Log.d("Loaded log","loaded:"+getItemCount());
                        break;
                    case ERROR:
                        Log.d("Loaded log","ERROR");
                        break;
                }
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Log.d("count","ada");









    }


    private class matchViewHolder extends RecyclerView.ViewHolder {
        private TextView heure;
        private TextView cat;
        // private TextView places;
        private TextView terrain;
        private TextView prix;
        private TextView duree;


        public matchViewHolder(@NonNull View itemView) {
            super(itemView);
            heure = itemView.findViewById(R.id.heureMatch);
            cat = itemView.findViewById(R.id.sportCategory);
            // places = itemView.findViewById(R.id.places);
            terrain = itemView.findViewById(R.id.terrain);
            prix = itemView.findViewById(R.id.prix);
            duree = itemView.findViewById(R.id.duree);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.stopListening();
    }










}

