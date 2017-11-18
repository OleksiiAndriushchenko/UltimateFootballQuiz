package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.andaleksei.ultimatefootballquiz.R.id.playButton;

public class playWithFriendMode extends AppCompatActivity {

    private dataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_friend_mode);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/SEASRN__.ttf");

        TextView playWithFriendMode = (TextView) findViewById(R.id.playWithFriendMode);

        playWithFriendMode.setTypeface(custom_font);
        playWithFriendMode.setShadowLayer(
                10f, // radius
                5.0f, // dx
                5.0f, // dy
                Color.parseColor("#FF3D803D") // shadow color
        );

        database = new dataBase(this);

        TextView playButton = (TextView) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playWithFriendGameIntent = new Intent(playWithFriendMode.this, playWithFriendGame.class);

                startActivity(playWithFriendGameIntent);
            }
        });

        playButton.setTypeface(custom_font);
        playButton.setShadowLayer(
                10f, // radius
                5.0f, // dx
                5.0f, // dy
                Color.parseColor("#FF3D803D") // shadow color
        );

        TextView howToPlayButton = (TextView) findViewById(R.id.howToPlay);

        howToPlayButton.setTypeface(custom_font);
        howToPlayButton.setShadowLayer(
                10f, // radius
                5.0f, // dx
                5.0f, // dy
                Color.parseColor("#FF3D803D") // shadow color
        );

        TextView settingsButton = (TextView) findViewById(R.id.settings);

        settingsButton.setTypeface(custom_font);
        settingsButton.setShadowLayer(
                10f, // radius
                5.0f, // dx
                5.0f, // dy
                Color.parseColor("#FF3D803D") // shadow color
        );
    }
}
