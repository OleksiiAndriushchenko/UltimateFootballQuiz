package com.warped.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private dataBase database;

    private stringAdapter myStringAdapter;

    private final String COINS = "coins";

    private void setText() {
        TextView mainMenu = (TextView) findViewById(R.id.main_menu);

        mainMenu.setText(myStringAdapter.getString(this, "play"));

        TextView loading = (TextView) findViewById(R.id.loadingTextview);

        loading.setText("Load database");

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        loading.setTypeface(custom_font);
    }

    private void setVisibility() {
        RelativeLayout loading = (RelativeLayout) findViewById(R.id.loading);

        loading.setVisibility(View.INVISIBLE);

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setVisibility(View.VISIBLE);

        LinearLayout settingsContainer = (LinearLayout) findViewById(R.id.settingsContainer);

        settingsContainer.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myStringAdapter = new stringAdapter();

        setText();

        database = new dataBase(this);

        setVisibility();

        TextView playButton = (TextView)findViewById(R.id.main_menu);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        playButton.setTypeface(custom_font);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playModesIntent = new Intent(MainActivity.this, playModes.class);

                startActivity(playModesIntent);
            }
        });

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(MainActivity.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(MainActivity.this, coins.class);

                startActivity(coinsIntent);
            }
        });

        TextView coins = (TextView) findViewById(R.id.coins);

        String text = String.valueOf(
                database.getVariableValue(getResources().getString(R.string.coins_en)));

        coins.setText(text);

    }

    @Override
    protected void onResume() {
        super.onResume();

        setText();

        TextView coins = (TextView) findViewById(R.id.coins);

        String text = String.valueOf(
                database.getVariableValue(getResources().getString(R.string.coins_en)));

        coins.setText(text);
    }
}
