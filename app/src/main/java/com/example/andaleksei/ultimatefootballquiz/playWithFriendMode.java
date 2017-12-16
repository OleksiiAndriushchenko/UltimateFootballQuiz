package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.andaleksei.ultimatefootballquiz.R.id.playButton;

public class playWithFriendMode extends AppCompatActivity {

    private dataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_friend_mode);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

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

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(playWithFriendMode.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(playWithFriendMode.this, coins.class);

                startActivity(coinsIntent);
            }
        });

        TextView howToPlayButton = (TextView) findViewById(R.id.howToPlay);

        howToPlayButton.setTypeface(custom_font);

        TextView settingsButton = (TextView) findViewById(R.id.options);

        settingsButton.setTypeface(custom_font);

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(getResources().getString(R.string.coins)));
    }
}
