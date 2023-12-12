package com.example.notes;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {
    Button btnBack, btnConfirm;
    EditText notesTitle, notesContent;

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

        btnBack = findViewById(R.id.btnBack);
        btnConfirm = findViewById(R.id.btnConfirm);
        notesTitle = findViewById(R.id.notesTitle);
        notesContent = findViewById(R.id.notesContent);

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
                    if(passedAuthority == null) {
                        db.insertNotes(titleText, notesText);
                        Toast.makeText(EditActivity.this, "Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if("update".equals(passedAuthority)) {
                            Toast.makeText(EditActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            int rowsAffected = db.updateNoteRow(passedTitle, titleText, notesText);
                        }
                    }
                    startActivity(intent);
                    finish();
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this)
                        .setTitle("Leave the app")
                        .setMessage("Are you sure about this?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            finish();
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {

                        });
                builder.show();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}