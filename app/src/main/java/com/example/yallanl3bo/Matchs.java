package com.example.yallanl3bo;

public class Matchs {
    String Stade;
    String Prix ;
    String Heure;
    String NbrePlace;
    String Duree;
    String PlaceMax;
    String SelectedDate;


    public Matchs(String stade, String prix, String heure, String nbrePlace,String duree,String placemax, String selecteddate) {
        Stade = stade;
        Prix = prix;
        Heure = heure;
        NbrePlace = nbrePlace;
        Duree=duree;
        PlaceMax=placemax;
        SelectedDate=selecteddate;
    }

    public String getStade() {
        return Stade;
    }

    public String getPrix() {
        return Prix;
    }

    public String getHeure() {
        return Heure;
    }

    public String getNbrePlace() {
        return NbrePlace;
    }
    public String getDuree() {
        return Duree;
    }
    public String getPlaceMax() {
        return PlaceMax;
    }
    public String getSelectedDate() {
        return SelectedDate;
    }
}
