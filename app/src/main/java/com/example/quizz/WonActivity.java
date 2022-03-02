package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class WonActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView resultText;
    int correct, wrong;
    LinearLayout btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        resultText = findViewById(R.id.result_text);
        btnShare = findViewById(R.id.btn_share);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);

        resultText.setText(new StringBuilder().append(correct).append("/").append((int) (wrong + correct)).toString());

        circularProgressBar.setProgress(correct);
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(WonActivity.this, MainActivity.class);
        startActivity(intent);
    }
}