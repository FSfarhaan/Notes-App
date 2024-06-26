package com.example.notes;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ArrayList<NotesModel> notesList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    TextView emptyNotes;
    ImageView addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addBtn = findViewById(R.id.addBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyNotes = findViewById(R.id.emptyNotes);

        DbHelper db = new DbHelper(MainActivity.this);
        notesList = db.getNotes();
        if(notesList.isEmpty()) emptyNotes.setVisibility(View.VISIBLE);
        else emptyNotes.setVisibility(View.INVISIBLE);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation(v);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 100);
            }
        });

        adapter = new RecyclerAdapter(MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
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

    public void startAnimation(View v){
        v.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(50)
                .setInterpolator(new DecelerateInterpolator())
                .withEndAction(() -> {
                    v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(50)
                            .setInterpolator(new DecelerateInterpolator())
                            .start();
                }).start();
    }
}
