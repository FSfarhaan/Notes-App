package com.example.notes;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    ImageView btnBack, btnConfirm;
    EditText notesTitle, notesContent;
    TextView noteCreatedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        DbHelper db = new DbHelper(EditActivity.this);

        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        Intent fromMain = getIntent();
        String passedTitle = fromMain.getStringExtra("title");
        String passedContent = fromMain.getStringExtra("content");
        String passedAuthority = fromMain.getStringExtra("authority");
        String passedDate = fromMain.getStringExtra("currentDate");


        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);
        notesTitle = findViewById(R.id.notesTitle);
        notesContent = findViewById(R.id.notesContent);
        noteCreatedOn = findViewById(R.id.noteCreatedOn);

        if(passedAuthority == null) noteCreatedOn.setVisibility(View.GONE);
        else {
            noteCreatedOn.setVisibility(View.VISIBLE);
            noteCreatedOn.setText(passedDate);
        }

        notesTitle.setText(passedTitle);
        notesContent.setText(passedContent);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = notesTitle.getText().toString();
                String notesText = notesContent.getText().toString();

                if(titleText == null || titleText.equals("") || notesText == null || notesText.equals(""))
                    Toast.makeText(EditActivity.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                else{
                    // For Creating a new note
                    if(passedAuthority == null) {
                        String todayDate = getDate("Created on");
                        // String modifiedDate = getDate("Modified on");
                        long modifiedDate = System.currentTimeMillis();

                        db.insertNotes(titleText, notesText, todayDate, modifiedDate);

                        Toast.makeText(EditActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else if(titleText.equals(passedTitle) && notesText.equals(passedContent)){
                        startActivity(new Intent(EditActivity.this, MainActivity.class));
                    }
                    // For Updating an existing note
                    else if("update".equals(passedAuthority)) {
                        long modifiedDate = System.currentTimeMillis();

                        int rowsAffected = db.updateNoteRow(passedTitle, titleText, notesText, modifiedDate);
                        Toast.makeText(EditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(intent);
                    finish();
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(EditActivity.this, MainActivity.class));
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public String getDate(String status) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String time = String.format("%02d:%02d", hour, minute);
        String todayDate = status + " " + dayOfMonth + "/" + month + "/" + year + " | " + time;

        return todayDate;
    }
}