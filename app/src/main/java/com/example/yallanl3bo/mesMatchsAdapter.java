package com.example.yallanl3bo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallanl3bo.models.matchItem;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class mesMatchsAdapter extends RecyclerView.Adapter<mesMatchsAdapter.MyViewHolder2>{
    Context context;
    ArrayList<matchItem> list;
    mesMatchsAdapter adapter;


    public mesMatchsAdapter(Context context,ArrayList<matchItem> list) {
        this.list = list;
        this.context=context;
        this.adapter=this;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mesmatchsadapter,parent, false) ;
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        DateFormat formatDate = new SimpleDateFormat("dd-MM-yy");
        DateFormat formatheure = new SimpleDateFormat("HH:mm");
        DateFormat formatgen = new SimpleDateFormat("dd-MM-yy HH:mm");
        matchItem model1 = list.get(position);

        Date d = null;
        if (model1.getDate() != null) {
            try {
                d = formatgen.parse(model1.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.dateM.setText(formatDate.format(d));
            holder.heure.setText(formatheure.format(d));
        };
        holder.cat.setText(model1.getCategory());
        holder.placesRes.setText(model1.getPlacesReservees() + "/");
        holder.placesMax.setText(model1.getPlacesMax()+ " ");
        holder.terrain.setText(model1.getTerrain());
        holder.prix.setText(model1.getPrix()+"DH");
        holder.duree.setText(model1.getDuree()+"H");
        holder.btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Cet action est irreversible");
                builder.setMessage("Voulez-vous vraiment supprimer ce match ?");
                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = model1.getId();
                        FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs").child(id).removeValue() ;
                        list.remove(id);
                        adapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context.getApplicationContext(), "Annulé", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView heure;
        TextView dateM;
        TextView cat;
        TextView placesRes;
        TextView placesMax;
        TextView terrain;
        TextView prix;
        TextView duree;
        Button btnSupprimer;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            heure = itemView.findViewById(R.id.heuremonMatch);
            dateM = itemView.findViewById(R.id.datemonMatch);
            cat = itemView.findViewById(R.id.mySportCategory);
            placesMax = itemView.findViewById(R.id.mesplacesMax);
            placesRes = itemView.findViewById(R.id.mesplacesRes);
            terrain = itemView.findViewById(R.id.monTerrain);
            prix = itemView.findViewById(R.id.monprix);
            duree = itemView.findViewById(R.id.maDuree);
            btnSupprimer=itemView.findViewById(R.id.btnSupprimer);
        }
    }
}
