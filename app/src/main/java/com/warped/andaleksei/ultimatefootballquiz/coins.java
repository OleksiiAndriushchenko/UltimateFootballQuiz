package com.warped.andaleksei.ultimatefootballquiz;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class coins extends AppCompatActivity {

    stringAdapter myStringAdapter;

    TextView rateApp, watchVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);

        myStringAdapter = new stringAdapter();

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        rateApp = (TextView) findViewById(R.id.rate);

        rateApp.setText(myStringAdapter.getString(this, "rateApp"));

        rateApp.setTypeface(custom_font);

        watchVideo = (TextView) findViewById(R.id.video);

        watchVideo.setText(myStringAdapter.getString(this, "video"));

        watchVideo.setTypeface(custom_font);
    }
}
