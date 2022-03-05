package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WonActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView resultText;
    int correct, wrong;
    LinearLayout btnPlayAgain, btnViewScore;
    EditText inputName;

    String score;
    String name;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);
        mContext = this;

        circularProgressBar = findViewById(R.id.circularProgressBar);
        resultText = findViewById(R.id.result_text);
        btnPlayAgain = findViewById(R.id.btn_share);
        btnViewScore = findViewById(R.id.btn_view_score);

        correct = getIntent().getIntExtra("correct", 0);
        wrong = getIntent().getIntExtra("wrong", 0);

        resultText.setText(new StringBuilder().append(correct).append("/").append((int) (wrong + correct)).toString());

        circularProgressBar.setProgress(correct);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new Dialog(WonActivity.this);//, R.style.Dialoge);
                dialog.setContentView(R.layout.store_high_score_dialog);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                inputName = dialog.findViewById(R.id.et_player_name);
                dialog.findViewById(R.id.btn_save_score).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = inputName.getText().toString();
                        score = String.format(correct + "/" + ((int) wrong + (int) correct));
                        String textOut = name + "@@" + score;
                        //saveScoreTofile(name, score);
                        writeToFile("high_score_quizz.txt", textOut);
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        }, 600);

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // View list score
        btnViewScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(WonActivity.this);
                dialog.setContentView(R.layout.list_player_score);
                ListView scrollView = dialog.findViewById(R.id.sv_list_score);

                String resultChar = readFromFile("high_score_quizz.txt");
                String[] listResultText= resultChar.split("@@@");
                ArrayList<String> listName = new ArrayList<>();
                ArrayList<String> listscore = new ArrayList<>();

                System.out.println(resultChar);
                System.out.println(listResultText[0]);

                for (String a : listResultText){
                    String[] b = a.split("@@");
                    listName.add(b[0]);
                    listscore.add("Điểm: " + b[1]);

//                    System.out.println(a);
//                    System.out.println("b[0]" + b[0]);
//                    System.out.println("b[1]" + b[1]);
                }

                System.out.println(listName);

                String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                        "Linux", "OS/2" };
                MyListAdapter adapter=new MyListAdapter(WonActivity.this, listName.toArray(new String[0]),
                        listscore.toArray(new String[0]));

                scrollView.setAdapter(adapter);

//                System.out.println("Size of list player score: " + listPlayerScore.size());
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,R.layout.list_player_score, R.id.tv_score_adapter,values);
//                scrollView.setAdapter(adapter);

                dialog.findViewById(R.id.btn_back_to_score_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                    }
                });
                dialog.show();
            }
        });
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(WonActivity.this, MainActivity.class);
        startActivity(intent);
    }

//    public void saveScoreTofile(String name, String score)
//    {
//        FileOutputStream fos;
//        try {
//            fos = openFileOutput("high_score_quizz", Context.MODE_PRIVATE);
//            //default mode is PRIVATE, can be APPEND etc.
//            String data = name + " " + score;
//            fos.write(data.getBytes());
//            fos.close();
//            Toast.makeText(getApplicationContext(),"Save successful", Toast.LENGTH_LONG).show();
//
//        } catch (IOException e) {e.printStackTrace();}
//    }

    public void writeToFile(String fileName, String content){
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName), true);
            writer.write(content.getBytes());
            writer.write("@@@".getBytes());
            writer.close();
            Toast.makeText(getApplicationContext(), "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(String fileName){
        File path = getApplicationContext().getFilesDir();
        String resultChar = "";
        try {
            FileInputStream fin = new FileInputStream(new File(path, fileName));
            int i = 0;
            while ((i = fin.read()) != -1) {
                resultChar = resultChar + String.valueOf((char) i);
            }
            fin.close();
            Toast.makeText(getApplicationContext(), "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultChar;
    }

//    public ArrayList<String> loadDataFromFile()
//    {
//        String filename= "high_score_quizz.txt";
//        ArrayList<String> stringBuffers = new ArrayList<>();
//        try {
//            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
//            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
//                    openFileInput(filename)));
//            String inputString;
//            //Reading data line by line and storing it into the stringbuffer
//            while ((inputString = inputReader.readLine()) != null) {
//                stringBuffers.add(inputString);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return stringBuffers;
//    }
    public class MyListAdapter extends ArrayAdapter<String> {

        private final WonActivity context;
        private final String[] namesss;
        private final String[] scoreee;

        public MyListAdapter(WonActivity context, String[] namesss, String[] scoreee) {
            super(context, R.layout.text_view_score_adappter, namesss);
            // TODO Auto-generated constructor stub

            this.context=context;
            this.namesss = namesss;
            this.scoreee = scoreee;

        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.text_view_score_adappter, null,true);

            TextView titleText = (TextView) rowView.findViewById(R.id.title);
            TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

            titleText.setText(namesss[position]);
            subtitleText.setText(scoreee[position]);

            return rowView;
        }
    }
}