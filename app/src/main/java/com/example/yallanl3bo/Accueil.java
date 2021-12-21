package com.example.yallanl3bo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.yallanl3bo.models.matchItem;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;



public class Accueil extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView aucunMatch;
BottomNavigationView bottomNavigationView;
    DatabaseReference db;
    AdapterMatch adapterMatch;
    ArrayList<matchItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);
// Set a Start date (Default, 1 Jan 1970)
        datePickerTimeline.setInitialDate(2021 , 11, 21);
// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                // Do Something
            }

        });
BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
bottomNavigationView.setSelectedItemId(R.id.accueil );
bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mesmatchs:
                startActivity(new Intent(getApplicationContext(),MesMatchs.class));
                overridePendingTransition(0,0);
                return true;
            case R.id.accueil:

                return true;
            case R.id.monprofil:
                startActivity(new Intent(getApplicationContext(),Profile.class));
                overridePendingTransition(0,0);
                return true;
        }
        return false;
    }
});
        recyclerView=findViewById(R.id.recyclerViewMatchs);
        db = FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
       // aucunMatch = findViewById(R.id.matchIntrouvabletxt);

        db.orderByChild("date").addValueEventListener(new ValueEventListener() {
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

