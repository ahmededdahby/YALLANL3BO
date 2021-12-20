package com.example.yallanl3bo;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    TextInputLayout email,password;
    CardView login;
    FirebaseAuth firebaseAuth;
    String Email ,Password;
    TextView Inscription , forgotPass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.loginbutton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Inscription = findViewById(R.id.inscriptiontxt);
        forgotPass = findViewById(R.id.forgetpass);
        firebaseAuth = FirebaseAuth.getInstance();
        // Click sur se connecter
        login.setOnClickListener(v -> {
            if (!emailValidation() | !passwordalidation()) {
                return;
            } else {
                Email = Objects.requireNonNull(email.getEditText()).getText().toString();
                Password = Objects.requireNonNull(password.getEditText()).getText().toString();
                firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified())) {
                            // startActivity(new Intent(MainActivity.this, Accueil.class));
                            startActivity(new Intent(MainActivity.this, Accueil.class));
                            finish();
                        }
                    } else {
                        email.setError("Veuillez vérifier votre adresse e-mail");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Cet", e.getMessage());
                        if (e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                            email.setError("Cet edresse e-mail n'existe pas ");
                        } else if (e.getMessage().equals("The email address is badly formatted")) {
                            email.setError("Verifier le format de votre edresse e-mail ");
                        }
                        if (e.getMessage().equals("The password il invalid or the user does not have a password")) {
                            password.setError("Mot de passe incorrect");
                        }
                    }
                });
            }
        });
    }
    // Clic sur s'inscrire
    public void signIn(View view) {
        Intent intent = new Intent(this, inscription_page.class);
            startActivity(intent);
    }
    //Clic sur mot de passe oublié
    public void ForgotPassword(View view){
        Intent intent = new Intent(this, forgetPass.class);
        startActivity(intent);
    }


    // EMAIL
    boolean emailValidation(){
        String Email = Objects.requireNonNull(email.getEditText()).getText().toString();
        if(Email.isEmpty()){
            Log.d("this","setEROOR");
          email.setError("Veuillez entrez votre email");

            Log.d("thiis","setEROOR");
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










