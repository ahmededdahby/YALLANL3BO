package com.example.yallanl3bo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

        DateFormat formatDate = new SimpleDateFormat("dd-MM-yy");
        DateFormat formatheure = new SimpleDateFormat("HH:mm");

        DateFormat formatgen = new SimpleDateFormat("dd-MM-yy HH:mm");

        matchItem model = list.get(position);
        holder.setIsRecyclable(false);


        Date now = new Date();
        if (model.getDate() != null) {
            Log.d("datemat",model.getDate()+"");

            Date itemD = null;
            try {
                itemD = formatgen.parse(model.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (itemD.before(now)) {
                String id = model.getId();

                FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs").child(id).removeValue() ;





            }
        }






        Date d = null;

      if (model.getDate() != null) {

          try {
              d = formatgen.parse(model.getDate());

          } catch (ParseException e) {
              e.printStackTrace();
          }


          holder.dateM.setText(formatDate.format(d));
          holder.heure.setText(formatheure.format(d));
      };


        holder.cat.setText(model.getCategory());
        holder.placesRes.setText(model.getPlacesReservees() + "/");
        holder.placesMax.setText(model.getPlacesMax()+ " ");
        holder.terrain.setText(model.getTerrain());
        holder.prix.setText(model.getPrix()+"DH");
        holder.duree.setText(model.getDuree()+"H");
        if(model.getPlacesReservees()>=model.getPlacesMax()){
            holder.btnRejoindre.setEnabled(false);
        }


            holder.btnRejoindre.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Log.d("email",user.getEmail());

                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.placesRes.getContext());
                    builder.setTitle("Voulez-vous vraiment rejoindre ce match?");
                    builder.setMessage("Vous vous engagez à etre present à l'heure prévue!!!");

                    builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String id = model.getId();
                            Log.d("id", id);
                            Map<String, Object> map = new HashMap<>();
                            map.put("PlacesReservees", model.getPlacesReservees() + 1);
                            model.setPlacesReservees(model.getPlacesReservees() + 1);
                            FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("matchs").child(id).updateChildren(map);
                            notifyItemChanged(position);
                            Toast.makeText(context.getApplicationContext(), "BON MATCH !", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context.getApplicationContext(), "Annulé", Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                        }
                    });
                    if (model.getPlacesReservees() < model.getPlacesMax()) {
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }



                }
                //user deconnecté
               else {
                    AlertDialog.Builder build = new AlertDialog.Builder(holder.placesRes.getContext());
                    build.setTitle("Connectez-vous d'abord!");
                    build.setMessage(" Vous devez etre connecté pour rejoindre un match ");
                    build.setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    build.setNegativeButton("Continuer vers le site", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = build.create();
                    dialog.show();

                }

            }  });
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
