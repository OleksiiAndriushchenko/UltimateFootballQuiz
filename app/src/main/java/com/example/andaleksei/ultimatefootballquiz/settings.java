package com.example.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class settings extends AppCompatActivity {

    private void setFont() {

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        TextView rights = (TextView) findViewById(R.id.rights);
        TextView policy = (TextView) findViewById(R.id.policy);

        rights.setTypeface(custom_font);
        policy.setTypeface(custom_font);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setFont();

        Spinner spinner = (Spinner) findViewById(R.id.language);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, android.R.layout.simple_spinner_item);

        String [] tempArray = getResources().getStringArray(R.array.languages);

        List<String> languages = Arrays.asList(tempArray);

        MyArrayAdapter mySpinnerAdapter = new MyArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                languages);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(mySpinnerAdapter);

    }

    private class MyArrayAdapter extends ArrayAdapter {

        List<String> languages;

        public MyArrayAdapter(Context context, int textViewResourceId,  List<String> languages) {
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
            v.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
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
            v.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            v.setTextSize(28);

            return v;
        }

    }

}
