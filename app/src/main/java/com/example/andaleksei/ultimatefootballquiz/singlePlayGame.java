package com.example.andaleksei.ultimatefootballquiz;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

import static android.widget.Toast.makeText;
import static com.example.andaleksei.ultimatefootballquiz.R.id.answer;

public class singlePlayGame extends AppCompatActivity {

    private int num = 0;

    private String footballer = "sanchez";

    TextView answer[] = new TextView[footballer.length()];
    int answerNum[] = new int[footballer.length()];

    TextView firstR[] = new TextView[9];
    TextView secondR[] = new TextView[9];

    private View.OnClickListener onCharacterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;

            if (num < footballer.length()) {
                clickedView.setTextColor(Color.BLACK);
                answer[num].setText(clickedView.getText());
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
            }
        }
    };

    private View.OnClickListener onCharacterAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;
            if (num > 0 && clickedView.equals(answer[num - 1])) {
                Toast.makeText(getApplicationContext(), "in if statement", Toast.LENGTH_SHORT).show();
                clickedView.setText("");
                if (answerNum[num - 1] < 9) {
                    firstR[answerNum[num - 1]].setTextColor(Color.WHITE);
                } else {
                    secondR[answerNum[num - 10]].setTextColor(Color.WHITE);
                }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_game);

        LinearLayout ll = (LinearLayout) findViewById(R.id.answer);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size),
                getResources().getDimensionPixelSize(R.dimen.character_size));

        for (int i = 0; i < footballer.length(); ++i) {
            answer[i] = new TextView(this);
            if (i  == 0) {
                params.setMargins(0, 0, 8, 0);
            } else if (i == footballer.length() - 1) {
                params.setMargins(8, 0, 0, 0);
            } else params.setMargins(8, 0, 8, 0);
            answer[i].setLayoutParams(params);
            answer[i].setText("");
            answer[i].setTextColor(Color.WHITE);
            answer[i].setBackgroundColor(Color.BLACK);
            answer[i].setGravity(Gravity.CENTER);
            ll.addView(answer[i]);
            answer[i].setOnClickListener(onCharacterAnswerClickListener);
        }

        LinearLayout firstRow = (LinearLayout) findViewById(R.id.first_row);
        LinearLayout secondRow = (LinearLayout) findViewById(R.id.second_row);


        params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size_choice),
                getResources().getDimensionPixelSize(R.dimen.character_size_choice));

        ArrayList<Character> foot = new ArrayList<Character>();

        for (int i = 0; i < 18; ++i) {
            if (i < footballer.length()) {
                foot.add(footballer.charAt(i));
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
