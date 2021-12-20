package com.example.yallanl3bo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallanl3bo.models.matchItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MesMatchs extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView aucunMatch;


    DatabaseReference db;
    mesMatchsAdapter mesMatchsAdapter;
    ArrayList<matchItem> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesmatchspage);

        recyclerView=findViewById(R.id.recyclerViewMesMatchs);
        db = FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list2=new ArrayList<>();
        aucunMatch = findViewById(R.id.aucunmatchcreeTxt);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String id = dataSnapshot.getKey();
                    matchItem matchItem = dataSnapshot.getValue(matchItem.class);
                    matchItem.setId(id);
                    Log.d("user",user.getEmail());
                    Log.d("userA",matchItem.getAdmin());
                    if(matchItem.getAdmin().equals( user.getEmail() )){
                        list2.add(matchItem);
                    }
                }
                if (list2.size()==0){
                    aucunMatch.setText("Vous n'avez crée aucun match");
                }
                else {
                    aucunMatch.setText("Vos Matchs crées :");
                    aucunMatch.setTextSize(30);
                }

                mesMatchsAdapter.notifyDataSetChanged();



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        mesMatchsAdapter = new mesMatchsAdapter(this, list2);
        recyclerView.setAdapter(mesMatchsAdapter);

    }
}
