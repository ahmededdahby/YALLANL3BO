package com.example.yallanl3bo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class inscription_page extends AppCompatActivity {
    TextInputLayout EmailInput,PasswordInput,PasswordconfirmInput,nomInput,prenomInput,villeInput,phoneInput;
    CardView InscriptionCard;
    FirebaseAuth firebaseAuth;
    String Email ,Password,nom,prenom,phone,ville;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_page);
        //initialisation
        nomInput = findViewById(R.id.nom);
        prenomInput = findViewById(R.id.prenom);
        villeInput = findViewById(R.id.ville);
        phoneInput = findViewById(R.id.Phone);
        EmailInput = findViewById(R.id.EmailInput);
        PasswordInput = findViewById(R.id.PasswordInput);
        PasswordconfirmInput = findViewById(R.id.PasswordconfirmInput);
        InscriptionCard = findViewById(R.id.InscriptionCard);
        Log.d("onClick","oonclick");

        //firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // click sur boutton
        InscriptionCard.setOnClickListener(v -> {
            if(!emailValidation() | !passwordalidation() | !passwordConfirmValidation() ){
                return;
            } else {
                Email = Objects.requireNonNull(EmailInput.getEditText()).getText().toString();
                Password = PasswordInput.getEditText().getText().toString();
                nom = nomInput.getEditText().getText().toString();
                prenom = prenomInput.getEditText().getText().toString();
                ville = villeInput.getEditText().getText().toString();
                phone= phoneInput.getEditText().getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(nom, prenom, ville, phone,Email);

                            FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(inscription_page.this, "Vous etes desormais inscrit ! Verifiez votre email ", Toast.LENGTH_LONG).show();
                                        setContentView(R.layout.activity_main);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Cet email" , e.getMessage());
                                    EmailInput.setError("Cette adresse e-mail est déja utilisée ");
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Cet" , e.getMessage());
                        PasswordInput.setError("votre mot de passe doit contenir au moins 6 caracteres");
                    }
                });
            }
        });
    }

    // EMAIL
    boolean emailValidation(){
        String email = Objects.requireNonNull(EmailInput.getEditText()).getText().toString();
        if(email.isEmpty()){
            EmailInput.setError("Veuillez entrez votre email");

            return false;
        } else {
            EmailInput.setError(null);
            return true;
        }
    }
    // PASSWORD
    boolean passwordalidation(){
        String password = Objects.requireNonNull(PasswordInput.getEditText()).getText().toString();
        if(password.isEmpty()){
            PasswordInput.setError("Veuillez entrez votre mot de passe ");
            return false;
        } else {
            PasswordInput.setError(null);
            return true;
        }
    }
    // PASSWORD CONFIRMATION
    boolean passwordConfirmValidation (){
        String password= Objects.requireNonNull(PasswordInput.getEditText()).getText().toString();
        String confpassword = Objects.requireNonNull(PasswordconfirmInput.getEditText()).getText().toString();
        if (confpassword.isEmpty()){
            PasswordconfirmInput.setError("Veuillez retapez votre mot de passe a nouveau");
            return false;
        }
        else if (!confpassword.equals(password)){
            PasswordconfirmInput.setError("Ces mots de passe ne correspondent pas.Veuillez réessayer.");
            return false ;
        }
        else {
            PasswordconfirmInput.setError(null);
            return true ;
        }


    }



}
