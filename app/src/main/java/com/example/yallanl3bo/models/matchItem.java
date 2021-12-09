package com.example.yallanl3bo.models;

public class matchItem {
    private String Heure;
    private int Places;
    private String Category;
    private String Terrain;
    private String Prix;
    private String Duree;

    public matchItem() {
    }

    public void setTerrain(String terrain) {
        Terrain = terrain;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public void setPlaces(int places) {
        Places = places;
    }

    public void setHeure(String heure) {
        Heure = heure;
    }

    public void setDuree(String duree) {
        Duree = duree;
    }

    public matchItem(String Heure, int Places, String Category, String Terrain, String Prix, String Duree){
        this.Heure = Heure;
        this.Places = Places;
        this.Category=Category;
        this.Prix=Prix;
        this.Terrain=Terrain;
        this.Duree=Duree;

    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getHeure() {
        return Heure;
    }

    public int getPlaces() {
        return Places;
    }

    public String getCategory() {
        return Category;
    }

    public String getTerrain() {
        return Terrain;
    }

    public String getPrix() {
        return Prix;
    }

    public String getDuree() {
        return Duree;
    }
}
