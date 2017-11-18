package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.andaleksei.ultimatefootballquiz.R.id.playButton;

public class playModes extends AppCompatActivity {

    private dataBase database;

    private final String COINS = "coins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_modes);

        database = new dataBase(this);

        TextView singlePlayButton = (TextView)findViewById(R.id.single_play);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/SEASRN__.ttf");

        singlePlayButton.setTypeface(custom_font);
        singlePlayButton.setShadowLayer(
                10f, // radius
                5.0f, // dx
                5.0f, // dy
                Color.parseColor("#FF3D803D") // shadow color
        );

        TextView playWithFriendButton = (TextView) findViewById(R.id.play_with_friend);

        singlePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayModeIntent = new Intent(playModes.this, singlePlayMode.class);

                startActivity(singlePlayModeIntent);
            }
        });

        playWithFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playWithFriendModeIntent = new Intent(playModes.this, playWithFriendMode.class);

                startActivity(playWithFriendModeIntent);
            }
        });

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }
}
