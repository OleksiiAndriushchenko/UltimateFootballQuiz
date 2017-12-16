package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class hints extends AppCompatActivity {

    private dataBase database;

    private stringAdapter myStringAdapter;

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

    private int addChar;
    private int numberOfChars;

    private void setText() {
        hintPhrase1 = myStringAdapter.getString(this, "hint1");
        hintPhrase2 = myStringAdapter.getString(this, "hint2");

        numberOfCoins = database.getVariableValue(coins);

        addedChars = database.getVariableValue("add char");

        twCoins.setText(String.valueOf(numberOfCoins));

        addChar = getIntent().getIntExtra("add char", -1);

        numberOfChars = getIntent().getIntExtra("number of chars", -1);

        hint1.setText(hintPhrase1 + addChar);

        hint2.setText(hintPhrase2 + addChar * numberOfChars);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        database = new dataBase(this);

        myStringAdapter = new stringAdapter();

        twCoins = (TextView) findViewById(R.id.coins);

        hint1 = (TextView) findViewById(R.id.hint1);

        containerHint1 = (LinearLayout) findViewById(R.id.containerHint1);

        hint2 = (TextView) findViewById(R.id.hint2);

        containerHint2 = (LinearLayout) findViewById(R.id.containerHint2);

        setText();

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

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(hints.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(hints.this, coins.class);

                startActivity(coinsIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        setText();

        TextView coins = (TextView) findViewById(R.id.coins);

        String numberOfCoins = String.valueOf(
                database.getVariableValue(getResources().getString(R.string.coins)));

        coins.setText(numberOfCoins);
    }
}
