package com.example.andaleksei.ultimatefootballquiz;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class hints extends AppCompatActivity {

    private dataBase database;

    private final String hintPhrase1 = "Remove a needless characters";
    private final String hintPhrase2 = "Remove all needless characters";
    private final String hintPhrase3 = "Hint one letter";
    private final String hintPhrase4 = "Hint the whole word";

    TextView hint1;
    TextView hint2;
    TextView hint3;
    TextView hint4;

    private int removedChars = 0;

    private int addedChars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        database = new dataBase(this);

        final int addChar = getIntent().getIntExtra("add char", -1);

        int removeChar = getIntent().getIntExtra("remove char", -1);

        final int numberOfChars = getIntent().getIntExtra("number of chars", -1);


        hint1 = (TextView) findViewById(R.id.hint1);

        hint1.setText(hintPhrase1 + " - " + removeChar);

        hint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removedChars++;

                database.updateVariable("remove char", removedChars);

                if (removedChars == numberOfChars) {
                    hint1.setOnClickListener(null);
                    hint2.setOnClickListener(null);
                }
            }
        });

        hint2 = (TextView) findViewById(R.id.hint2);

        hint2.setText(hintPhrase2 + " - " + removeChar * (16 - numberOfChars));

        hint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.updateVariable("remove chars", 1);

                hint1.setOnClickListener(null);
                hint2.setOnClickListener(null);
            }
        });

        hint3 = (TextView) findViewById(R.id.hint3);

        hint3.setText(hintPhrase3 + " - " + addChar);

        hint3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedChars++;

                database.updateVariable("add char", addedChars);

                if (addedChars == numberOfChars) {
                    hint1.setOnClickListener(null);
                    hint2.setOnClickListener(null);
                    hint3.setOnClickListener(null);
                    hint4.setOnClickListener(null);
                }
            }
        });

        hint4 = (TextView) findViewById(R.id.hint4);

        hint4.setText(hintPhrase4 + " - " + addChar * numberOfChars);

        hint4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.updateVariable("add chars", 1);

                hint1.setOnClickListener(null);
                hint2.setOnClickListener(null);
                hint3.setOnClickListener(null);
                hint4.setOnClickListener(null);
            }
        });
    }
}
