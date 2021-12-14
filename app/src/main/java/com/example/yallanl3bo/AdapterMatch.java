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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdapterMatch extends RecyclerView.Adapter<AdapterMatch.MyViewHolder> {

   Context context;

    public AdapterMatch(Context context,ArrayList<matchItem> list) {
        this.context = context;
        this.list=list;
    }

    ArrayList<matchItem> list;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.adapter_matchs,parent, false) ;
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        matchItem model = list.get(position);

        DateFormat formatDate = new SimpleDateFormat("dd-MM-YY");
        DateFormat formatheure = new SimpleDateFormat("HH:mm");

        DateFormat formatgen = new SimpleDateFormat("dd-MM-YY HH:mm");

      if (model.getDate() != null) {

          Date d = null;
          try {
              d = formatgen.parse(model.getDate());
          } catch (ParseException e) {
              e.printStackTrace();
          }

          holder.dateM.setText(formatDate.format(d));
          holder.heure.setText(formatheure.format(d));
      }



        holder.cat.setText(model.getCategory());
        holder.placesRes.setText(model.getPlacesReservees() + "/");
        holder.placesMax.setText(model.getPlacesMax()+ " ");
        holder.terrain.setText(model.getTerrain());
        holder.prix.setText(model.getPrix()+"DH");
        holder.duree.setText(model.getDuree()+"H");
        holder.btnRejoindre.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(holder.placesRes.getContext());
                builder.setTitle("Voulez-vous vraiment rejoindre ce match?");
                builder.setMessage("Vous vous engagez à eetre present à l'heure prévue!!!");

                builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("PlacesReservees",model.getPlacesReservees()+1);
                        Log.d("map",map.toString());
                        Log.d("position", String.valueOf(holder.getBindingAdapterPosition()));


                        FirebaseDatabase.getInstance("https://yalla-nla3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs").child(String.valueOf(holder.getBindingAdapterPosition())).updateChildren(map);






                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.cat.getContext(),"Annulé",Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }
                });
                if(model.getPlacesReservees()<model.getPlacesMax()){
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
               else {
                    Toast.makeText(holder.cat.getContext(),"Match Complet",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView heure;
        TextView dateM;
        TextView cat;
        TextView placesRes;
        TextView placesMax;
        TextView terrain;
        TextView prix;
        TextView duree;
        Button btnRejoindre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            heure = itemView.findViewById(R.id.heureMatch);
            dateM = itemView.findViewById(R.id.dateMatch);
            cat = itemView.findViewById(R.id.sportCategory);
            placesMax = itemView.findViewById(R.id.placesMax);
            placesRes = itemView.findViewById(R.id.placesRes);
          terrain = itemView.findViewById(R.id.terrain);
            prix = itemView.findViewById(R.id.prix);
            duree = itemView.findViewById(R.id.duree);btnRejoindre=itemView.findViewById(R.id.btnRejoindre);
        }
    }
}