package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class singlePlayGame extends AppCompatActivity {

    private int num = 0;
    private int space;
    private int wordLength;
    private String footballPlayer;
    private dataBase database;

    private ImageView image;
    TextView answer[];
    int answerNum[];

    TextView firstR[] = new TextView[9];
    TextView secondR[] = new TextView[9];

    private View.OnClickListener onCharacterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;
            if (num < wordLength && !clickedView.getText().toString().equals("")) {
                answer[num].setText(clickedView.getText());
                clickedView.setText("");
                int i = 0;
                do {
                    if (i < 9) {
                        if (clickedView.equals(firstR[i])) {
                            answerNum[num] = i;
                        }
                    } else {
                        if (clickedView.equals(secondR[i - 9])) {
                            answerNum[num] = i;
                        }
                    }
                } while (++i < 18);
                num++;
                if (num == wordLength) {
                    checkAnswer();
                }
            }
        }
    };

    private View.OnClickListener onCharacterAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;
            if (num > 0 && clickedView.equals(answer[num - 1])) {
                if (answerNum[num - 1] < 9) {
                    firstR[answerNum[num - 1]].setText(clickedView.getText());
                } else {
                    secondR[answerNum[num - 1] - 9].setText(clickedView.getText());
                }
                clickedView.setText("");
                num--;
            }
        }
    };

    private void randomizeArray(ArrayList<Character> arrayToRandom) {
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            int num1 = random.nextInt(arrayToRandom.size());
            int num2 = random.nextInt(arrayToRandom.size());
            Character object = arrayToRandom.get(num);
            arrayToRandom.set(num, arrayToRandom.get(num2));
            arrayToRandom.set(num2, object);
        }
    }

    private void checkAnswer() {
        String userInput = "";
        for (int i = 0; i < num; i++) {
            userInput += answer[i].getText().toString();
        }
        if (footballPlayer.equals(userInput)) {
            Toast.makeText(getApplicationContext(), "Congratulation, you are right!!!",
                    Toast.LENGTH_SHORT).show();
            if (database.getVariableData("footballer") == database.getNextFootballerId()) {
                database.unlockNextFootballer();
                database.updateVariable("footballer", database.getNextFootballerId());
            } else {
                database.updateVariable("footballer", database.getVariableData("footballer") + 1);
            }
            Intent singlePlayGameIntent = new Intent(singlePlayGame.this, singlePlayGame.class);
            this.finish();
            startActivity(singlePlayGameIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Unfortunately, you are wrong!!!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_game);

        Toast.makeText(getApplicationContext(), "Start", Toast.LENGTH_SHORT).show();

        database = new dataBase(this);

        int data = database.getVariableData("footballer");
        Toast.makeText(getApplicationContext(), "data = " + data, Toast.LENGTH_SHORT).show();

        footballer fb = database.getData(data);
        footballPlayer = fb.getName();

        Toast.makeText(getApplicationContext(), "name" + fb.getName(), Toast.LENGTH_SHORT).show();

        if (footballPlayer.contains("_")) {
            wordLength = footballPlayer.length() - 1;
            footballPlayer = footballPlayer.replace("_", " ");
            space = footballPlayer.indexOf(' ');

            answer = new TextView[wordLength];
            answerNum = new int[wordLength];
        } else {
            wordLength = footballPlayer.length();
            answer = new TextView[wordLength];
            answerNum = new int[wordLength];
        }

        image = (ImageView) findViewById(R.id.imageView);
        int resID = getResources().getIdentifier(fb.getName() , "drawable", getPackageName());
        image.setImageResource(resID);

        LinearLayout ll1 = (LinearLayout) findViewById(R.id.answer1);
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.answer2);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size),
                getResources().getDimensionPixelSize(R.dimen.character_size));

        for (int i = 0; i < wordLength; ++i) {
            answer[i] = new TextView(this);
            if (i  == 0) {
                params.setMargins(0, 0, 8, 0);
            } else if (i == wordLength - 1) {
                params.setMargins(8, 0, 0, 0);
            } else params.setMargins(8, 0, 8, 0);
            answer[i].setLayoutParams(params);
            answer[i].setText("");
            answer[i].setTextColor(Color.WHITE);
            answer[i].setBackgroundColor(Color.BLACK);
            answer[i].setGravity(Gravity.CENTER);
            if (footballPlayer.contains(" ")) {
                if (i < space) {
                    ll1.addView(answer[i]);
                } else {
                    ll2.addView(answer[i]);
                }
            } else ll1.addView(answer[i]);
            answer[i].setOnClickListener(onCharacterAnswerClickListener);
        }

        LinearLayout firstRow = (LinearLayout) findViewById(R.id.first_row);
        LinearLayout secondRow = (LinearLayout) findViewById(R.id.second_row);


        params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size_choice),
                getResources().getDimensionPixelSize(R.dimen.character_size_choice));

        ArrayList<Character> foot = new ArrayList<Character>();

        footballPlayer = footballPlayer.replace(" ", "");

        for (int i = 0; i < 18; ++i) {
            if (i < wordLength) {
                foot.add(footballPlayer.charAt(i));
            } else {
                Random random = new Random();
                int number = 97 + random.nextInt(26);
                foot.add((char)number);
            }
        }

        randomizeArray(foot);

        for (int i = 0; i < 9; i++) {
            firstR[i] = new TextView(this);
            secondR[i] = new TextView(this);
            if (i == 0) {
                params.setMargins(0, 8, 8, 8);
            } else if (i == 8) {
                params.setMargins(8, 8, 0, 8);
            } else params.setMargins(8, 8, 8, 8);
            firstR[i].setLayoutParams(params);
            secondR[i].setLayoutParams(params);
            firstR[i].setText(new String(new char[] {foot.get(i)} ));
            secondR[i].setText(new String(new char[] {foot.get(i+9)} ));
            firstR[i].setTextColor(Color.WHITE);
            secondR[i].setTextColor(Color.WHITE);
            firstR[i].setBackgroundColor(Color.BLACK);
            secondR[i].setBackgroundColor(Color.BLACK);
            firstR[i].setGravity(Gravity.CENTER);
            secondR[i].setGravity(Gravity.CENTER);
            firstRow.addView(firstR[i]);
            secondRow.addView(secondR[i]);

            firstR[i].setOnClickListener(onCharacterClickListener);
            secondR[i].setOnClickListener(onCharacterClickListener);
        }

    }
}
