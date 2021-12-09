package com.example.yallanl3bo;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    TextInputLayout email,password;
    CardView login;
    FirebaseAuth firebaseAuth;
    String Email ,Password;
    TextView Inscription ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.loginbutton);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Inscription=findViewById(R.id.inscriptiontxt);
        firebaseAuth= FirebaseAuth.getInstance();
        // click sur s'inscrire :
        Inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_inscription_page);
            }
        });
        // Click sur se connecter
        login.setOnClickListener(v -> {
            if(!emailValidation() | !passwordalidation()  ){
                return;
            } else {
                Email = Objects.requireNonNull(email.getEditText()).getText().toString();
                Password = Objects.requireNonNull(password.getEditText()).getText().toString();
                firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        if(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()))
                        {
                           startActivity(new Intent(MainActivity.this, Acceuil.class));
                            finish();
                        }
                    }else {
                        email.setError("Veuillez vérifier votre adresse e-mail");
                    }
                }).addOnFailureListener(e -> Log.d("Cet",e.getMessage()));
            }
        });

    }

    // EMAIL
    boolean emailValidation(){
        String Email = Objects.requireNonNull(email.getEditText()).getText().toString();
        if(Email.isEmpty()){
            email.setError("Veuillez entrez votre email");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    // PASSWORD
    boolean passwordalidation(){
        String Password = Objects.requireNonNull(password.getEditText()).getText().toString();
        if(Password.isEmpty()){
            password.setError("Veuillez entrez votre mot de passe ");
            return false;
        } else {
            password.setError(null);
            return true;
        }

    }


}










