package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private dataBase database;

    private  stringAdapter myStringAdapter;

    private final String COINS = "coins";

    private void setText() {
        TextView mainMenu = (TextView) findViewById(R.id.main_menu);

        mainMenu.setText(myStringAdapter.getString(this, "play"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new dataBase(this);

        myStringAdapter = new stringAdapter();

        setText();

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

        coins.setText("" + database.getVariableValue(COINS));

    }

    @Override
    protected void onResume() {
        super.onResume();

        setText();

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }
}
