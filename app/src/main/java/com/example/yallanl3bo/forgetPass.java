package com.example.yallanl3bo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPass extends AppCompatActivity {
    TextInputLayout Email;
    CardView reset;
    FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        Email= findViewById(R.id.emailpassword);
        reset= findViewById(R.id.resetButton);
        firebaseauth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }


        });
    }

    private void resetPassword() {
        String email= Email.getEditText().getText().toString();
        if(email.isEmpty()){
            Email.setError("Veuillez entrez votre e-mail");
            return;
        }
        firebaseauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(forgetPass.this, "Verifiez votre email pour reinitialiser votre mot de passe ", Toast.LENGTH_LONG).show();
                    setContentView(R.layout.activity_main);
                }
                else {
                    Toast.makeText(forgetPass.this, "Veuillez reessayer ! ", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}