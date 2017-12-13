package com.example.andaleksei.ultimatefootballquiz;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class hints extends AppCompatActivity {

    private dataBase database;

    private String hintPhrase1;
    private String hintPhrase2;
    private final String coins = "coins";

    TextView hint1;
    TextView hint2;
    TextView twCoins;

    LinearLayout containerHint1;
    LinearLayout containerHint2;

    private int addedChars;
    private int numberOfCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        hintPhrase1 = getResources().getString(R.string.hint1);
        hintPhrase2 = getResources().getString(R.string.hint2);

        database = new dataBase(this);

        numberOfCoins = database.getVariableValue(coins);

        addedChars = database.getVariableValue("add char");

        final int addChar = getIntent().getIntExtra("add char", -1);

        final int numberOfChars = getIntent().getIntExtra("number of chars", -1);

        twCoins = (TextView) findViewById(R.id.coins);

        twCoins.setText(String.valueOf(numberOfCoins));

        hint1 = (TextView) findViewById(R.id.hint1);

        containerHint1 = (LinearLayout) findViewById(R.id.containerHint1);

        hint1.setText(hintPhrase1 + addChar);

        if (numberOfCoins < addChar) {
            hint1.setTextColor(Color.parseColor("#455A64"));
            containerHint1.setBackgroundResource(R.drawable.textview_frame_dark);
        } else {
            hint1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (numberOfCoins >= addChar) {
                        addedChars++;

                        database.updateVariable("add char", addedChars);

                        numberOfCoins -= addChar;
                        database.updateVariable(coins, numberOfCoins);

                        twCoins.setText(String.valueOf(numberOfCoins));
                    }

                    if (addedChars == numberOfChars || numberOfCoins < addChar) {
                        hint1.setOnClickListener(null);
                        hint1.setTextColor(Color.parseColor("#455A64"));
                        containerHint1.setBackgroundResource(R.drawable.textview_frame_dark);

                        hint2.setOnClickListener(null);
                        hint2.setTextColor(Color.parseColor("#455A64"));
                        containerHint2.setBackgroundResource(R.drawable.textview_frame_dark);
                    }

                    finish();
                }
            });
        }

        hint2 = (TextView) findViewById(R.id.hint2);

        containerHint2 = (LinearLayout) findViewById(R.id.containerHint2);

        hint2.setText(hintPhrase2 + addChar * numberOfChars);

        if (numberOfCoins < addChar * numberOfChars) {
            hint2.setTextColor(Color.parseColor("#455A64"));
            containerHint2.setBackgroundResource(R.drawable.textview_frame_dark);
        } else {
            hint2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numberOfCoins >= numberOfChars * addChar) {
                        database.updateVariable("add chars", 1);

                        numberOfCoins -= numberOfChars * addChar;
                        database.updateVariable(coins, numberOfCoins);
                    }

                    hint1.setOnClickListener(null);
                    hint1.setTextColor(Color.parseColor("#455A64"));
                    containerHint1.setBackgroundResource(R.drawable.textview_frame_dark);

                    hint2.setOnClickListener(null);
                    hint2.setTextColor(Color.parseColor("#455A64"));
                    containerHint2.setBackgroundResource(R.drawable.textview_frame_dark);

                    finish();
                }
            });
        }

    }
}
