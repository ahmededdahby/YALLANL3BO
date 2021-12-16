package com.example.yallanl3bo.models;

import java.util.Date;

public class matchItem {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String Date;
    private int PlacesReservees;
    private int PlacesMax;
    private String Category;
    private String Terrain;
    private int Prix;
    private int Duree;



    public matchItem() {
    }

    public void setTerrain(String terrain) {
        Terrain = terrain;
    }

    public void setPrix(int prix) {
        Prix = prix;
    }
    public void setPlacesMax(int placesMax) {
        PlacesMax = placesMax;
    }



    public void setPlacesReservees(int placesRes) {
        PlacesReservees = placesRes;
    }


    public void setDate(String date) {
        this.Date = date;
    }

    public void setDuree(int duree) {
        Duree = duree;
    }

    public matchItem(String id,String date,int min, int PlacesRes,int PlacesMax, String Category, String Terrain, int Prix, int Duree){
        this.id=id;
        this.Date = date;
        this.PlacesReservees = PlacesRes;
        this.PlacesMax = PlacesMax;
        this.Category=Category;
        this.Prix=Prix;
        this.Terrain=Terrain;
        this.Duree=Duree;

    }

    public void setCategory(String category) {
        Category = category;
    }


    public String getDate() {
        return Date;
    }
    public int getPlacesReservees() {
        return PlacesReservees;
    }
    public int getPlacesMax() {
        return PlacesMax;
    }
    public String getCategory() {
        return Category;
    }

    public String getTerrain() {
        return Terrain;
    }

    public int getPrix() {
        return Prix;
    }

    public int getDuree() {
        return Duree;
    }
}
