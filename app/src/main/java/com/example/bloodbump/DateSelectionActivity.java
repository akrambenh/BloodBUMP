package com.example.bloodbump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class DateSelectionActivity extends AppCompatActivity {
    private TextView venueText;
    private TextView day1 ,day2, day3, day4, day5, day6, day7;
    private TextView morning1, morning2, morning3, morning4, morning5, morning6, morning7;
    private TextView evening1, evening2, evening3, evening4, evening5, evening6, evening7;
    private TextView wholeBloodText, plateletText;
    private EditText bloodQuantityText;
    private ImageView upArrow, downArrow;
    private Button bookButton, proceedButton;
    private FirebaseAuth userAuth;
    private FirebaseDatabase userDB;
    private DatabaseReference reference;
    private String RequestDate;
    private  String RequestTime;
    private String bloodgroup = null;
    private String predecessor_activity = null;
    private String venue = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateselection);
        venueText = findViewById(R.id.venueText);
        //
        Intent intent = getIntent();
        venue = intent.getStringExtra("venue");
        predecessor_activity = intent.getStringExtra("predecessor_activity");
        venueText.setText(venue);
        //
        bookButton = (Button) findViewById(R.id.bookButton);
        bookButton.setOnClickListener(this::Book);
        // Declaring Views
        /*dayButton1 = findViewById(R.id.dayButton1);
        dayButton2 = findViewById(R.id.dayButton2);
        dayButton3 = findViewById(R.id.dayButton3);
        dayButton4 = findViewById(R.id.dayButton4);
        dayButton5 = findViewById(R.id.dayButton5);
        dayButton6 = findViewById(R.id.dayButton6);
        dayButton7 = findViewById(R.id.dayButton7);
        // Setting Their OnClickers
        dayButton1.setOnClickListener(this::onClick);
        dayButton2.setOnClickListener(this::onClick);
        dayButton3.setOnClickListener(this::onClick);
        dayButton4.setOnClickListener(this::onClick);
        dayButton5.setOnClickListener(this::onClick);
        dayButton6.setOnClickListener(this::onClick);
        dayButton7.setOnClickListener(this::onClick);*/
        //
        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        day5 = findViewById(R.id.day5);
        day6 = findViewById(R.id.day6);
        day7 = findViewById(R.id.day7);
        //
        morning1 = findViewById(R.id.morning1);
        morning2 = findViewById(R.id.morning2);
        morning3 = findViewById(R.id.morning3);
        morning4 = findViewById(R.id.morning4);
        morning5 = findViewById(R.id.morning5);
        morning6 = findViewById(R.id.morning6);
        morning7 = findViewById(R.id.morning7);
        // Creating Their OnClickers
        morning1.setOnClickListener(this::onClick);
        morning2.setOnClickListener(this::onClick);
        morning3.setOnClickListener(this::onClick);
        morning4.setOnClickListener(this::onClick);
        morning5.setOnClickListener(this::onClick);
        morning6.setOnClickListener(this::onClick);
        morning7.setOnClickListener(this::onClick);
        //
        evening1 = findViewById(R.id.evening1);
        evening2 = findViewById(R.id.evening2);
        evening3 = findViewById(R.id.evening3);
        evening4 = findViewById(R.id.evening4);
        evening5 = findViewById(R.id.evening5);
        evening6 = findViewById(R.id.evening6);
        evening7 = findViewById(R.id.evening7);
        // Setting Their OnClickers
        evening1.setOnClickListener(this::onClick);
        evening2.setOnClickListener(this::onClick);
        evening3.setOnClickListener(this::onClick);
        evening4.setOnClickListener(this::onClick);
        evening5.setOnClickListener(this::onClick);
        evening6.setOnClickListener(this::onClick);
        evening7.setOnClickListener(this::onClick);
        //

        //
        //
        userAuth = FirebaseAuth.getInstance();
        userDB = FirebaseDatabase.getInstance();
        reference = userDB.getReference("Schedule");
        reference.child(venue).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot data = task.getResult();
                        long iter = data.getChildrenCount();
                        int iteration = (int) iter;
                        String[] Days = new String[iteration];
                        String[] morning = new String[iteration];
                        String[] evening  = new String[iteration];
                        final Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                        for(int i = 0; i < iteration; i++){
                            Days[i] = iterator.next().getKey();
                            morning[i] = String.valueOf(data.child(Days[i]).child("morning").getValue());
                            evening[i] = String.valueOf(data.child(Days[i]).child("evening").getValue());
                        }
                        day1.setText(Days[0]);
                        day2.setText(Days[1]);
                        day3.setText(Days[2]);
                        day4.setText(Days[3]);
                        day5.setText(Days[4]);
                        day6.setText(Days[5]);
                        day7.setText(Days[6]);
                        //
                        morning1.setText(morning[0]);
                        morning2.setText(morning[1]);
                        morning3.setText(morning[2]);
                        morning4.setText(morning[3]);
                        morning5.setText(morning[4]);
                        morning6.setText(morning[5]);
                        morning7.setText(morning[6]);
                        //
                        evening1.setText(evening[0]);
                        evening2.setText(evening[1]);
                        evening3.setText(evening[2]);
                        evening4.setText(evening[3]);
                        evening5.setText(evening[4]);
                        evening6.setText(evening[5]);
                        evening7.setText(evening[6]);
                    }
                }else
                    Toast.makeText(DateSelectionActivity.this, venue + " Has No Schedule", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void onClick(View view){
       switch(view.getId()){
           case R.id.morning1:
                if(morning1.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setClickable(true);
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning1.setBackground(getDrawable(R.drawable.field_selected));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day1.getText().toString();
                    RequestTime = morning1.getText().toString();
                }
                break;
           case R.id.morning2:
                if(morning2.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning2.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day2.getText().toString().trim();
                    RequestTime = morning2.getText().toString().trim();
                }
                break;
           case R.id.morning3:
                if(morning3.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning3.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day3.getText().toString().trim();
                    RequestTime = morning3.getText().toString().trim();
                }
                break;
           case R.id.morning4:
                if(morning4.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning4.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day4.getText().toString().trim();
                    RequestTime = morning4.getText().toString().trim();
                }
                break;
           case R.id.morning5:
                if(morning5.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning5.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day5.getText().toString().trim();
                    RequestTime = morning5.getText().toString().trim();
                }
                break;
           case R.id.morning6:
                if(morning6.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning6.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning7.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day6.getText().toString().trim();
                    RequestTime = morning6.getText().toString().trim();
                }
                break;
           case R.id.morning7:
                if(morning7.getText().toString().trim().isEmpty()){
                    bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                    bookButton.setClickable(false);
                }else {
                    bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                    morning7.setBackground(getDrawable(R.drawable.field_selected));
                    evening1.setBackground(getDrawable(R.drawable.field));
                    evening2.setBackground(getDrawable(R.drawable.field));
                    evening3.setBackground(getDrawable(R.drawable.field));
                    evening4.setBackground(getDrawable(R.drawable.field));
                    evening5.setBackground(getDrawable(R.drawable.field));
                    evening6.setBackground(getDrawable(R.drawable.field));
                    evening7.setBackground(getDrawable(R.drawable.field));
                    morning1.setBackground(getDrawable(R.drawable.field));
                    morning2.setBackground(getDrawable(R.drawable.field));
                    morning3.setBackground(getDrawable(R.drawable.field));
                    morning4.setBackground(getDrawable(R.drawable.field));
                    morning5.setBackground(getDrawable(R.drawable.field));
                    morning6.setBackground(getDrawable(R.drawable.field));
                    RequestDate = day7.getText().toString().trim();
                    RequestTime = morning7.getText().toString().trim();
                }
                break;
           case R.id.evening1:
               if(evening1.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setClickable(true);
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening1.setBackground(getDrawable(R.drawable.field_selected));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day1.getText().toString().trim();
                   RequestTime = morning1.getText().toString().trim();
               }
               break;
           case R.id.evening2:
               if(evening2.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening2.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day2.getText().toString().trim();
                   RequestTime = evening2.getText().toString().trim();
               }
               break;
           case R.id.evening3:
               if(evening3.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening3.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day3.getText().toString().trim();
                   RequestTime = evening3.getText().toString().trim();
               }
               break;
           case R.id.evening4:
               if(evening4.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening4.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day4.getText().toString().trim();
                   RequestTime = evening4.getText().toString().trim();
               }
               break;
           case R.id.evening5:
               if(evening5.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening5.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day5.getText().toString().trim();
                   RequestTime = evening5.getText().toString().trim();
               }
               break;
           case R.id.evening6:
               if(evening6.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening6.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening7.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day6.getText().toString().trim();
                   RequestTime = evening6.getText().toString().trim();
               }
               break;
           case R.id.evening7:
               if(evening7.getText().toString().trim().isEmpty()){
                   bookButton.setBackground(getDrawable(R.drawable.large_grey_button));
                   bookButton.setClickable(false);
               }else {
                   bookButton.setBackground(getDrawable(R.drawable.large_red_button));
                   evening7.setBackground(getDrawable(R.drawable.field_selected));
                   evening1.setBackground(getDrawable(R.drawable.field));
                   evening2.setBackground(getDrawable(R.drawable.field));
                   evening3.setBackground(getDrawable(R.drawable.field));
                   evening4.setBackground(getDrawable(R.drawable.field));
                   evening5.setBackground(getDrawable(R.drawable.field));
                   evening6.setBackground(getDrawable(R.drawable.field));
                   morning1.setBackground(getDrawable(R.drawable.field));
                   morning2.setBackground(getDrawable(R.drawable.field));
                   morning3.setBackground(getDrawable(R.drawable.field));
                   morning4.setBackground(getDrawable(R.drawable.field));
                   morning5.setBackground(getDrawable(R.drawable.field));
                   morning6.setBackground(getDrawable(R.drawable.field));
                   morning7.setBackground(getDrawable(R.drawable.field));
                   RequestDate = day7.getText().toString().trim();
                   RequestTime = evening7.getText().toString().trim();
               }
               break;
        }
    }

    public void Book(View view) {
        Dialog dialog = new Dialog(DateSelectionActivity.this);
        dialog.setContentView(R.layout.dialog1_layout);
        dialog.getWindow();
        bloodQuantityText = dialog.findViewById(R.id.bloodQuantityText);
        wholeBloodText = dialog.findViewById(R.id.wholeBloodText);
        plateletText = dialog.findViewById(R.id.plateletText);
        proceedButton = dialog.findViewById(R.id.proceedButton);
        dialog.show();
        HashMap<String, String> requestMap = new HashMap<>();
        //int quantity = Integer.parseInt(bloodQuantityText.getText().toString());
        wholeBloodText.setOnClickListener(v -> {
            wholeBloodText.setBackground(getResources().getDrawable(R.drawable.field_selected));
            plateletText.setBackground(getResources().getDrawable(R.drawable.field));
            bloodQuantityText.setClickable(true);
            proceedButton.setClickable(true);
            requestMap.put("type", "Whole Blood");
        });
        plateletText.setOnClickListener(v -> {
            plateletText.setBackground(getResources().getDrawable(R.drawable.field_selected));
            wholeBloodText.setBackground(getResources().getDrawable(R.drawable.field));
            bloodQuantityText.setClickable(true);
            proceedButton.setClickable(true);
            requestMap.put("type", "Platelet");
        });
        proceedButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(bloodQuantityText.getText().toString());
            requestMap.put("quantity", String.valueOf(quantity));
            if (requestMap.get("type").isEmpty()) {
                Toast.makeText(this, "You Need To Select Type", Toast.LENGTH_SHORT).show();
            } else if (quantity < 460) {
                Toast.makeText(this, "Low Volume \n Try 460 or Above", Toast.LENGTH_SHORT).show();
            } else if (quantity > 1000) {
                Toast.makeText(this, "High Volume \n Try 1000 or Below", Toast.LENGTH_SHORT).show();
            } else {
                reference = userDB.getReference("Schedule");
                reference.child(venueText.getText().toString()).child(RequestDate).get().addOnCompleteListener(task -> {
                    if (task.getResult().exists()) {
                        DataSnapshot data = task.getResult();
                        int max = Integer.parseInt(data.child("max").getValue().toString());
                        if (max < 1) {
                            Toast.makeText(DateSelectionActivity.this, "You Can't Book On This Day", Toast.LENGTH_SHORT).show();
                        } else {
                            String UID = userAuth.getCurrentUser().getUid();
                            reference = userDB.getReference("User");
                            reference.child(UID).get().addOnCompleteListener(task13 -> {
                                if (task13.getResult().exists()) {
                                    DataSnapshot data1 = task13.getResult();

                                    String first_name = String.valueOf(data1.child("first_name").getValue());
                                    String last_name = String.valueOf(data1.child("last_name").getValue());
                                    String dob = String.valueOf(data1.child("date of birth").getValue());
                                    String donor_type = String.valueOf(data1.child("donor type").getValue());
                                    String sex = String.valueOf(data1.child("sex").getValue());

                                    reference = userDB.getReference("Blood");
                                    reference.child(UID).get().addOnCompleteListener(task12 -> {
                                        if (task12.getResult().exists()) {
                                            DataSnapshot item = task12.getResult();
                                            bloodgroup = String.valueOf(item.child("blood").getValue());
                                            String fullname = first_name + " " + last_name;
                                            // Putting Data Into HashMap to be written in database
                                            requestMap.put("Fullname", fullname);
                                            requestMap.put("Medical Establishment", venue);
                                            requestMap.put("Blood Group", bloodgroup);
                                            requestMap.put("Date Of Birth", dob);
                                            requestMap.put("Donor Type", donor_type);
                                            requestMap.put("Sex", sex);
                                            requestMap.put("Donation Date", RequestDate);
                                            requestMap.put("Donation Time", RequestTime);
                                            requestMap.put("Status", "Waiting");
                                            reference = userDB.getReference("Request");

                                            reference.child(UID).setValue(requestMap).addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    Toast.makeText(DateSelectionActivity.this, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(DateSelectionActivity.this, "The System Is Checking Your Health Report", Toast.LENGTH_SHORT).show();
                                                    // Writing Code To handle Health Report Conditions
                                                    checkHealthCondition(UID, fullname, requestMap);
                                                } else
                                                    Toast.makeText(DateSelectionActivity.this, "Failed To Send Request", Toast.LENGTH_SHORT).show();
                                            });
                                        }
                                    });

                                }
                            });
                        }
                    } else
                        Toast.makeText(DateSelectionActivity.this, "Date Doesn't exist", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
    private void checkHealthCondition(String uid, String fullname, HashMap<String, String> requestMap) {
        reference = userDB.getReference("HealthReport");
        HashMap<String, Boolean> illness = new HashMap<>();
        HashMap<String, Integer> pressure = new HashMap<>();
        reference.child(uid).get().addOnCompleteListener(task -> {
            if (task.getResult().exists()) {
                DataSnapshot data = task.getResult();
                String date = String.valueOf(data.child("Date").getValue());
                //checking if health report is older than 15 days
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dtformat = new SimpleDateFormat("dd/MM/yyyy");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalDate now = LocalDate.now();
                    try {
                        Date hpDate = dtformat.parse(date);
                        //LocalDate end = LocalDate.of(hpDate.getYear(), hpDate.getMonth(), hpDate.getDay()).plusDays(15);
                        int day = hpDate.getDay();
                        int month = hpDate.getMonth();
                        int year = hpDate.getYear();
                        LocalDate end = LocalDate.of(year, month, day).plusDays(15);
                        Toast.makeText(DateSelectionActivity.this, end.toString(), Toast.LENGTH_SHORT).show();
                        if(end.compareTo(now) >= 0){
                            Toast.makeText(DateSelectionActivity.this, "Date is valid", Toast.LENGTH_SHORT).show();
                        }else if(end.compareTo(now) < 0){
                            Toast.makeText(DateSelectionActivity.this, "Date is invalid", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                boolean hiv = Boolean.parseBoolean(data.child("HIV").getValue().toString());
                boolean malaria = Boolean.parseBoolean(data.child("malaria").getValue().toString());
                illness.put("HIV", hiv);
                illness.put("Malaria", malaria);
                int systolic = Integer.parseInt(data.child("systolic").getValue().toString());
                int diastolic = Integer.parseInt(data.child("diastolic").getValue().toString());
                pressure.put("Systolic", systolic);
                pressure.put("Diastolic", diastolic);
                // Testing
                if(illness.get("HIV") == true){
                    Toast.makeText(DateSelectionActivity.this, "Can't Book The Donation \n  Due To Having HIV", Toast.LENGTH_SHORT).show();
                }else if(illness.get("Malaria") == true){
                    Toast.makeText(DateSelectionActivity.this, "Can't Book The Donation \n Due to Having Malaria", Toast.LENGTH_SHORT).show();
                }else if(pressure.get("Systolic") >= 180){
                    Toast.makeText(DateSelectionActivity.this, "Your Systolic Pressure Is Too High", Toast.LENGTH_SHORT).show();

                }else if(pressure.get("Diastolic") >= 100) {
                    Toast.makeText(DateSelectionActivity.this, "Your Diastolic Pressure Is Too High", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DateSelectionActivity.this, "Donation Is Booked", Toast.LENGTH_SHORT).show();
                    //acceptAppointment(requestMap);

                }
            }
});

        }

    private void acceptAppointment(HashMap<String, String> requestMap) {
        String UID = userAuth.getCurrentUser().getUid();
        reference = userDB.getReference("Appointment");
        requestMap.remove("Status");
        reference.child(UID).setValue(requestMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DateSelectionActivity.this, "Donation Is Booked", Toast.LENGTH_SHORT).show();
                    // Removing The Request From The Database
                    reference = userDB.getReference("Request");
                    reference.child(UID).removeValue();
                    // Direct to appointment display Activity
                }
            }
        });
    }


    public void getDonationType(View view) {

        }

public void backToHome(View view) {
        if(predecessor_activity.equals("AppointmentActivity")){
            startActivity(new Intent(DateSelectionActivity.this, SelectVenueActivity.class));
        }else if(predecessor_activity.equals("SearchVenueActivity")){
            startActivity(new Intent(DateSelectionActivity.this, SearchVenueActivity.class));
        }
        }
}