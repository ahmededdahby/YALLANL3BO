package com.example.yallanl3bo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.example.yallanl3bo.models.matchItem;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;



public class Accueil extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView aucunMatch;

    DatabaseReference db;
    AdapterMatch adapterMatch;
    ArrayList<matchItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        recyclerView=findViewById(R.id.recyclerViewMatchs);
        db = FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
       // aucunMatch = findViewById(R.id.matchIntrouvabletxt);

        db.orderByChild("Date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String id = dataSnapshot.getKey();
                    matchItem matchItem = dataSnapshot.getValue(matchItem.class);
                    matchItem.setId(id);
                    list.add(matchItem);
                }
              /*  if (list.size()==0){
                    aucunMatch.setText("Aucun match disponible");
                    Log.d("listnul","list vide");
                }
                else {
                    aucunMatch.setText("Liste des matches disponibles :");
                    aucunMatch.setTextSize(30);
                }*/



                adapterMatch.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapterMatch = new AdapterMatch(this,list);
        recyclerView.setAdapter(adapterMatch);





    }


}

