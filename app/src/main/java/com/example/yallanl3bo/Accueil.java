package com.example.yallanl3bo;

import static androidx.paging.PagedList.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import com.example.yallanl3bo.models.matchItem;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class Accueil extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestorePagingAdapter adapter;

    DatabaseReference db;
    AdapterMatch adapterMatch;
    ArrayList<matchItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);

        datePickerTimeline.setInitialDate(2021, 11, 21);
// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                //Do Something
            }
        });

recyclerView=findViewById(R.id.recyclerViewMatchs);
        db = FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();

        Log.d("recycler","debut");

        db.orderByChild("Date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String id = dataSnapshot.getKey();
                    matchItem matchItem = dataSnapshot.getValue(matchItem.class);

                    matchItem.setId(id);
                    list.add(matchItem);


                }



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

