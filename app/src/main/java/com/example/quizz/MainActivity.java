package com.example.quizz;

import static com.example.quizz.Topic.path_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    public static ArrayList<ModelClass> listOfQuestions;
    public DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfQuestions = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference(path_data);
        System.out.println("Lấy dữ liệu 2.");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelClass modelclass = dataSnapshot.getValue(ModelClass.class);
                    listOfQuestions.add(modelclass);
                    System.out.println("aaaaaaaaaaaaaaaaa");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

//        listOfQuestions.add(new ModelClass("1 + 1 = ?", "1", "2", "5", "10", "2"));
//        listOfQuestions.add(new ModelClass("What is the highest mountain in the world.", "Everest", "Phanxiphan", "An-det", "Mariana", "Everest"));
//        listOfQuestions.add(new ModelClass("Uoc tinh dan so the gioi vao khoang bao nhieu vao nam 2050?", "7 ti", "8 ti", "10 ti", "9 ti", "8 ti"));
//        listOfQuestions.add(new ModelClass("Loai nao sau day la nuoc uong khong co ga?", "Cocacola", "Sting", "C2", "Pepsi", "C2"));

        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void StartGame(View view) {
        progressBar.setVisibility(View.VISIBLE);
        MediaPlayer clickSound = MediaPlayer.create(MainActivity.this, R.raw.click);
        clickSound.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Topic.class);
                startActivity(intent);
                clickSound.stop();
            }
        }, 1500);
    }
}