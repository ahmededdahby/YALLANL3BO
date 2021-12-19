package com.example.yallanl3bo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

import com.example.yallanl3bo.models.matchItem;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Match extends AppCompatActivity {
    TextView selectedDateText;
    String UserEmail;
    TextInputEditText nomstade,prix,heure,nbreplace,duree,placemax;
    Button submitbutton, datePickerBtn;
    DatabaseReference matchDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creatematch);
        //DBelements
        matchDBRef= FirebaseDatabase.getInstance("https://yalla-nl3bo-default-rtdb.europe-west1.firebasedatabase.app").getReference("matchs");
        nomstade = (TextInputEditText)findViewById(R.id.nomstade_text);
        prix =(TextInputEditText)findViewById(R.id.prix_text);
        heure =(TextInputEditText)findViewById(R.id.heure_text);
        nbreplace =(TextInputEditText)findViewById(R.id.nbreplace_text);
        duree= findViewById(R.id.duree_text);
        placemax =findViewById(R.id.placemax_text);
        selectedDateText=findViewById(R.id.selected_date);
        submitbutton=findViewById(R.id.submitbutton);
        //datepicker
        datePickerBtn=findViewById(R.id.date_picker);
        Calendar calendar=Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        long today=MaterialDatePicker.todayInUtcMilliseconds();
        MaterialDatePicker.Builder builder=MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Saisissez la date du match ");
        builder.setSelection(today);
        builder.setTheme(R.style.MaterialCalendarTheme);
        CalendarConstraints.Builder constraintBuilder= new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());
        builder.setCalendarConstraints(constraintBuilder.build());
        MaterialDatePicker<Long> materialDatePicker= builder.build();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
        } else {

        }



        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateFormat formaterDate = new SimpleDateFormat("dd-MM-yy");
                DateFormat format2Date = new SimpleDateFormat("MMM dd,yyyy");




                String Stade= nomstade.getText().toString();
                int Prix= Integer.parseInt(prix.getText().toString());
                int NbrePlace= Integer.parseInt(nbreplace.getText().toString());
                String Heure= heure.getText().toString();
                int Duree= Integer.parseInt(duree.getText().toString());
                int PlaceMax= Integer.parseInt(placemax.getText().toString());




                try {
                    Date d = format2Date.parse(selectedDateText.getText().toString());
                    String dd = formaterDate.format(d);
                    matchItem matchs = new matchItem("abc@gmail.com",dd+" "+Heure,NbrePlace,PlaceMax, "Football", Stade,Prix, Duree);
                    matchDBRef.push().setValue(matchs);
                    Toast.makeText(getApplicationContext(),"Match created",Toast.LENGTH_SHORT).show();


                } catch (ParseException e) {
                    Log.d("erreur","erreur");
                    e.printStackTrace();
                }



            }
        });


        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");




            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                selectedDateText.setText( materialDatePicker.getHeaderText());
                Log.d("datepick",materialDatePicker.getHeaderText());



            }
        });

    }

}