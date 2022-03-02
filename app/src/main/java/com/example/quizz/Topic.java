package com.example.quizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import java.util.ArrayList;

public class Topic extends AppCompatActivity {

    public static String path_data = "QuizQuetion";
    MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        LinearLayout ln_quiz = findViewById(R.id.ln_quiz);
        clickSound = MediaPlayer.create(Topic.this, R.raw.click);

        ln_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path_data = "QuizQuetion";
                clickSound.start();
                Intent intent = new Intent(Topic.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout ln_nature = findViewById(R.id.ln_nature);
        ln_nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path_data = "QuizQuetion";
                clickSound.start();
                Intent intent = new Intent(Topic.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout ln_georaphic = findViewById(R.id.ln_geography);
        ln_georaphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path_data = "QuizQuetion";
                clickSound.start();
                Intent intent = new Intent(Topic.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout ln_math = findViewById(R.id.ln_math);
        ln_math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path_data = "QuizQuetion";
                clickSound.start();
                Intent intent = new Intent(Topic.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}