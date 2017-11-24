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

    private final String hintPhrase1 = "Hint one letter";
    private final String hintPhrase2 = "Hint the whole word";

    TextView hint1;
    TextView hint2;

    LinearLayout containerHint1;
    LinearLayout containerHint2;

    private int addedChars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        database = new dataBase(this);

        final int addChar = getIntent().getIntExtra("add char", -1);

        final int numberOfChars = getIntent().getIntExtra("number of chars", -1);

        hint1 = (TextView) findViewById(R.id.hint1);
        containerHint1 = (LinearLayout) findViewById(R.id.containerHint1);

        hint1.setText(hintPhrase1 + " - " + addChar);

        hint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedChars++;

                database.updateVariable("add char", addedChars);

                if (addedChars == numberOfChars) {
                    hint1.setOnClickListener(null);
                    hint1.setTextColor(Color.parseColor("#455A64"));
                    containerHint1.setBackgroundResource(R.drawable.textview_frame_dark);

                    hint2.setOnClickListener(null);
                    hint2.setTextColor(Color.parseColor("#455A64"));
                    containerHint2.setBackgroundResource(R.drawable.textview_frame_dark);
                }
            }
        });

        hint2 = (TextView) findViewById(R.id.hint2);
        containerHint2 = (LinearLayout) findViewById(R.id.containerHint2);

        hint2.setText(hintPhrase2 + " - " + addChar * numberOfChars);

        hint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.updateVariable("add chars", 1);

                hint1.setOnClickListener(null);
                hint1.setTextColor(Color.parseColor("#455A64"));
                containerHint1.setBackgroundResource(R.drawable.textview_frame_dark);

                hint2.setOnClickListener(null);
                hint2.setTextColor(Color.parseColor("#455A64"));
                containerHint2.setBackgroundResource(R.drawable.textview_frame_dark);
            }
        });

    }
}
