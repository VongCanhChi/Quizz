package com.example.quizz;

import static com.example.quizz.MainActivity.listOfQuestions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ContentLoadingProgressBar;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class DashboardActivity extends AppCompatActivity {
    final int AnswerTime = 7;
    CountDownTimer countDownTimer;
    int timerValue = AnswerTime;
    ContentLoadingProgressBar progressBar;


    public ArrayList<ModelClass> allQuetionsList;

    public ModelClass modelClass;
    int index = 0;
    TextView card_quetion, optiona, optionb, optionc, optiond;
    CardView cardOA, cardOB, cardOC, cardOD;

    int correctCount = 0;
    int wrongCount = 0;
    LinearLayout nextBtn;

    int delayTimeForAnswer = 1000;
    MediaPlayer correctSound;
    MediaPlayer wrongSound;
    MediaPlayer winSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Hooks();
        index = 0;


//        listOfQuestions.add(new ModelClass("1 + 1 = ?", "1", "2", "5", "10", "2"));
//        listOfQuestions.add(new ModelClass("What is the highest mountain in the world.", "Everest", "Phanxiphan", "An-det", "Mariana", "Everest"));
////

        System.out.println(listOfQuestions.size());

        allQuetionsList = (ArrayList<ModelClass>) listOfQuestions.clone();
        System.out.println(allQuetionsList.size());
        Collections.shuffle(allQuetionsList);
        modelClass = allQuetionsList.get(index);

        setAllData();

        correctSound = MediaPlayer.create(DashboardActivity.this, R.raw.correct);
        wrongSound = MediaPlayer.create(DashboardActivity.this, R.raw.wrong_5);
        winSound = MediaPlayer.create(DashboardActivity.this, R.raw.win_game);

        countDownTimer = new CountDownTimer(7000,1000) {
            @Override
            public void onTick(long l) {
                timerValue = timerValue - 1;
                progressBar.setProgress(timerValue);
                System.out.println(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(DashboardActivity.this);//, R.style.Dialoge);
                dialog.setContentView(R.layout.time_out_dialog);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.findViewById(R.id.btn_try_again).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        }.start();
    }

    private void setAllData() {
        card_quetion.setText(modelClass.getQuetion());
        optiona.setText(modelClass.getoA());
        optionb.setText(modelClass.getoB());
        optionc.setText(modelClass.getoC ());
        optiond.setText(modelClass.getoD());
    }

    private void Hooks() {
        progressBar = findViewById(R.id.quiz_timer);
        card_quetion = findViewById(R.id.card_quetion);

        optiona = findViewById(R.id.card_option_a);
        optionb = findViewById(R.id.card_option_b);
        optionc = findViewById(R.id.card_option_c);
        optiond = findViewById(R.id.card_option_d);

        cardOA=findViewById (R.id.cardA);
        cardOB=findViewById(R.id.cardB);
        cardOC=findViewById(R.id.cardC);
        cardOD=findViewById (R.id.cardD);

        nextBtn = findViewById(R.id.next_btn);
    }

    public void Correct() {
        correctSound.start();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;
                if (index < listOfQuestions.size() - 1) {
                    index++;
                    modelClass = listOfQuestions.get(index);
                    setAllData();
                } else {
                    GameWon();
                }

                resetColor();
                countDownTimer.start();
                timerValue = AnswerTime;
                nextBtn.setOnClickListener(null);
                enableButton();
            }
        });
    }

    public void Wrong(CardView option) {
        wrongSound.start();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongCount++;
                if (index < listOfQuestions.size() - 1) {
                    index++;
                    modelClass = listOfQuestions.get(index);
                    setAllData();
                } else {
                    GameWon();
                }
                resetColor();
                countDownTimer.start();
                timerValue = AnswerTime;
                nextBtn.setOnClickListener(null);
                enableButton();
            }
        });
    }

    private void GameWon() {
        Intent intent = new Intent(DashboardActivity.this, WonActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);
        winSound.start();
        startActivity(intent);
    }

    public void enableButton() {
        cardOA.setClickable(true);
        cardOB.setClickable(true);
        cardOC.setClickable(true);
        cardOD.setClickable(true);
    }
    public void disableButton() {
        cardOA.setClickable(false);
        cardOB.setClickable(false);
        cardOC.setClickable(false);
        cardOD.setClickable(false);
    }
    public void resetColor() {
        cardOA.setBackgroundColor(getResources().getColor(R.color.white));
        cardOB.setBackgroundColor(getResources().getColor(R.color.white));
        cardOC.setBackgroundColor(getResources().getColor(R.color.white));
        cardOD.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void OptionAClick(View view) {
        disableButton();
        countDownTimer.cancel();
        cardOA.setBackgroundColor(getResources().getColor(R.color.orange));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(modelClass.getoA().equals(modelClass.getAns())){
                    cardOA.setBackgroundColor(getResources().getColor(R.color.green));
                    Correct();
                }else {
                    cardOA.setBackgroundColor(getResources().getColor(R.color.red));
                    Wrong(cardOA);
                }
            }
        }, delayTimeForAnswer);

    }
    public void OptionBClick(View view) {
        disableButton();
        //nextBtn.setClickable(true);
        countDownTimer.cancel();
        cardOB.setBackgroundColor(getResources().getColor(R.color.orange));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(modelClass.getoB().equals(modelClass.getAns())){
                    cardOB.setBackgroundColor(getResources().getColor(R.color.green));
                    Correct();
                }else {
                    cardOB.setBackgroundColor(getResources().getColor(R.color.red));
                    Wrong(cardOB);
                }
            }
        }, delayTimeForAnswer);

    }
    public void OptionCClick(View view) {
        disableButton();
        countDownTimer.cancel();
        cardOC.setBackgroundColor(getResources().getColor(R.color.orange));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(modelClass.getoC().equals(modelClass.getAns())){
                    cardOC.setBackgroundColor(getResources().getColor(R.color.green));
                    Correct();
                }else {
                    cardOC.setBackgroundColor(getResources().getColor(R.color.red));
                    Wrong(cardOC);
                }
            }
        }, delayTimeForAnswer);

    }
    public void OptionDClick(View view) {
        disableButton();
        countDownTimer.cancel();
        cardOD.setBackgroundColor(getResources().getColor(R.color.orange));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(modelClass.getoD().equals(modelClass.getAns())){
                    cardOD.setBackgroundColor(getResources().getColor(R.color.green));
                    Correct();
                }else {
                    cardOD.setBackgroundColor(getResources().getColor(R.color.red));
                    Wrong(cardOD);
                }
            }
        }, delayTimeForAnswer);

    }

    public void backToMenu(View view) {
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
    }
}