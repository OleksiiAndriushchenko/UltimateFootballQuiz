package com.example.andaleksei.ultimatefootballquiz;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class coins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        TextView rateApp = (TextView) findViewById(R.id.rate);

        rateApp.setTypeface(custom_font);

        TextView watchVideo = (TextView) findViewById(R.id.video);

        watchVideo.setTypeface(custom_font);
    }
}
