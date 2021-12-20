package com.example.yallanl3bo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    Uri imageUri;
    String myUri="";
    StorageTask uploadTask;
    StorageReference storagePdp;
    String userID;
    CircleImageView imageProfile;
    ImageButton changePdp;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         mAuth=FirebaseAuth.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        storagePdp = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storagePdp.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(@NonNull Uri uri) {
                Picasso.get().load(uri).into(imageProfile);
            }
        });

        reference= FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
        userID = user.getUid();
        final TextView NomText=findViewById(R.id.nomP);
        final TextView PrenomText= findViewById(R.id.prenomP);
        final TextView VilleText = findViewById(R.id.villeP);
        final TextView phoneText= findViewById(R.id.PhoneP);
        final TextView emailText = findViewById(R.id.emailP);
        imageProfile = findViewById(R.id.profile_image);
         changePdp = findViewById(R.id.changeImage);
     reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             User userProfile = snapshot.getValue(User.class);
             if(userProfile != null){
                 String Nom = userProfile.nom;
                 String Prenom = userProfile.prenom;
                 String Phone = userProfile.telephone;
                 String Ville = userProfile.ville;
                 String Email = userProfile.email;
                 NomText.setText(Nom);
                 PrenomText.setText(Prenom);
                 phoneText.setText(Phone);
                 VilleText.setText(Ville);
                 emailText.setText(Email);


             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
             Toast.makeText(Profile.this, "ERROR", Toast.LENGTH_LONG).show();

         }
     });
     changePdp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //open gallery
             Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             startActivityForResult(openGallery,1000);




         }

     });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
               // imageProfile.setImageURI(imageUri);
                uploadImagetoDB(imageUri);
            }
        }
    }

    private void uploadImagetoDB(Uri imageUri) {
        //image to firebase storage
        StorageReference fileRef = storagePdp.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
              //  Toast.makeText(Profile.this, "Image telechargée", Toast.LENGTH_LONG).show();
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(@NonNull Uri uri) {
                    Picasso.get().load(uri).into(imageProfile);
                }
            });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Veuillez reessayer ", Toast.LENGTH_LONG).show();
            }
        });
    }
}


