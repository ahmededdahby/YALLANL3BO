package com.example.yallanl3bo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallanl3bo.models.matchItem;

import java.util.ArrayList;

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

        holder.heure.setText(model.getHeure()+":"+model.getMinutes()+"0");
        holder.cat.setText(model.getCategory());
        holder.placesRes.setText(model.getPlacesReservees() + "/");
        holder.placesMax.setText(model.getPlacesMax()+ " ");
        holder.terrain.setText(model.getTerrain());
        holder.prix.setText(model.getPrix()+"DH");
        holder.duree.setText(model.getDuree()+"H");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView heure;
        TextView cat;
        TextView placesRes;
        TextView placesMax;
        TextView terrain;
        TextView prix;
        TextView duree;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            heure = itemView.findViewById(R.id.heureMatch);
            cat = itemView.findViewById(R.id.sportCategory);
            placesMax = itemView.findViewById(R.id.placesMax);
            placesRes = itemView.findViewById(R.id.placesRes);
            terrain = itemView.findViewById(R.id.terrain);
            prix = itemView.findViewById(R.id.prix);
            duree = itemView.findViewById(R.id.duree);
        }
    }
}
