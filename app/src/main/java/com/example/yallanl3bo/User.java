package com.example.yallanl3bo;

public class User {
    public User(String nom, String prenom, String ville, String phone,String email) {
        this.nom = nom;
        this.prenom=prenom;
        this.ville=ville;
        this.telephone=phone;
        this.email=email;
    }

    String nom , prenom , ville ,telephone ,email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


}
