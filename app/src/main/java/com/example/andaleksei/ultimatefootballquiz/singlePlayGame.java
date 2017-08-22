package com.example.andaleksei.ultimatefootballquiz;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.andaleksei.ultimatefootballquiz.R.id.answer;

public class singlePlayGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_game);

        LinearLayout ll = (LinearLayout) findViewById(R.id.answer);

        String footballer = "azpilicueta";

        for (int i = 0; i < footballer.length(); ++i) {
            TextView tw1 = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size),
                    getResources().getDimensionPixelSize(R.dimen.character_size));
            params.setMargins(8, 0, 8, 0);
            tw1.setLayoutParams(params);
            String ch = "";
            ch += footballer.charAt(i);
            tw1.setText(ch);
            tw1.setTextColor(Color.WHITE);
            tw1.setBackgroundColor(Color.BLACK);
            tw1.setGravity(Gravity.CENTER);
            ll.addView(tw1);
        }

        LinearLayout firstRow = (LinearLayout) findViewById(R.id.first_row);
        LinearLayout secondRow = (LinearLayout) findViewById(R.id.second_row);

        for (int i = 0; i < 9; i++) {
            TextView tw1 = new TextView(this), tw2 = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size_choice),
                    getResources().getDimensionPixelSize(R.dimen.character_size_choice));
            params.setMargins(8, 8, 8, 8);
            tw1.setLayoutParams(params);
            tw2.setLayoutParams(params);
            tw1.setText("A");
            tw2.setText("B");
            tw1.setTextColor(Color.WHITE);
            tw2.setTextColor(Color.WHITE);
            tw1.setBackgroundColor(Color.BLACK);
            tw2.setBackgroundColor(Color.BLACK);
            tw1.setGravity(Gravity.CENTER);
            tw2.setGravity(Gravity.CENTER);
            firstRow.addView(tw1);
            secondRow.addView(tw2);
        }
    }
}
