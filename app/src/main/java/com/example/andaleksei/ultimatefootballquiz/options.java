package com.example.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class options extends AppCompatActivity {

    dataBase database;

    stringAdapter myStringAdapter;

    private void setText() {

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        String text = "";

        TextView time = (TextView) findViewById(R.id.time);

        text = myStringAdapter.getString(this, "time") + ":";

        time.setText(text);

        time.setTypeface(custom_font);

        TextView players = (TextView) findViewById(R.id.questions);

        text = myStringAdapter.getString(this, "players") + ":";

        players.setText(text);

        players.setTypeface(custom_font);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        database = new dataBase(this);

        myStringAdapter = new stringAdapter();

        setText();

        Spinner time = (Spinner) findViewById(R.id.timeSpinner);

        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(this,
                R.array.time, android.R.layout.simple_spinner_item);

        String [] timeArray = getResources().getStringArray(R.array.time);

        List<String> timeList = Arrays.asList(timeArray);

        MyArrayAdapter myTimeAdapter = new MyArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                timeList);

        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        time.setAdapter(myTimeAdapter);

        time.setSelection(database.getVariableValue("time") / 10 - 1);

        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                database.updateVariable("time", (position + 1) * 10);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner questions = (Spinner) findViewById(R.id.questionsSpinner);

        ArrayAdapter<CharSequence> questionsAdapter = ArrayAdapter.createFromResource(this,
                R.array.questions, android.R.layout.simple_spinner_item);

        String [] questionsArray = getResources().getStringArray(R.array.questions);

        List<String> questionsList = Arrays.asList(questionsArray);

        MyArrayAdapter myQuestionsAdapter = new MyArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                questionsList);

        questionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        questions.setAdapter(myQuestionsAdapter);

        questions.setSelection(database.getVariableValue("players") / 5 - 2);

        questions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                database.updateVariable("players", (position + 2) * 5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(options.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(options.this, coins.class);

                startActivity(coinsIntent);
            }
        });

        TextView coins = (TextView) findViewById(R.id.coins);

        String text = String.valueOf(
                database.getVariableValue(getResources().getString(R.string.coins_en)));

        coins.setText(text);

    }

    protected void onResume() {
        super.onResume();

        setText();

        TextView coins = (TextView) findViewById(R.id.coins);

        String text = String.valueOf(
                database.getVariableValue(getResources().getString(R.string.coins_en)));

        coins.setText(text);
    }

    private class MyArrayAdapter extends ArrayAdapter {

        public MyArrayAdapter(Context context, int textViewResourceId, List<String> languages) {
            super(context, textViewResourceId, languages);
        }

        public TextView getView(int position, View convertView, ViewGroup parent) {
            Typeface custom_font = Typeface.createFromAsset(getAssets(),
                    getResources().getString(R.string.font));

            TextView v = (TextView) super.getView(position, convertView, parent);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            v.setTypeface(custom_font);
            v.setPadding(24, 24, 24, 24);
            v.setGravity(Gravity.CENTER);
            v.setTextSize(28);

            return v;
        }

        public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
            Typeface custom_font = Typeface.createFromAsset(getAssets(),
                    getResources().getString(R.string.font));

            TextView v = (TextView) super.getView(position, convertView, parent);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            params.setMargins(8, 8, 8, 8);

            v.setTypeface(custom_font);
            v.setPadding(24, 24, 24, 24);
            v.setGravity(Gravity.CENTER);
            v.setTextSize(28);

            return v;
        }

    }
}
