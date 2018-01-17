package com.warped.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class playWithFriendMode extends AppCompatActivity {

    private dataBase database;

    private stringAdapter myStringAdapter;

    TextView play, options;

    private void setText() {
        play.setText(myStringAdapter.getString(this, "play"));

        options.setText(myStringAdapter.getString(this, "options"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_friend_mode);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        database = new dataBase(this);

        myStringAdapter = new stringAdapter();

        play = (TextView) findViewById(R.id.playButton);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playWithFriendGameIntent = new Intent(playWithFriendMode.this, playWithFriendGame.class);

                startActivity(playWithFriendGameIntent);
            }
        });

        play.setTypeface(custom_font);

        options = (TextView) findViewById(R.id.options);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent optionsIntent = new Intent(playWithFriendMode.this, options.class);

                startActivity(optionsIntent);
            }
        });

        options.setTypeface(custom_font);

        setText();

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

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(getResources().getString(R.string.coins_en)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        setText();

        TextView coins = (TextView) findViewById(R.id.coins);

        String numberOfCoins = String.valueOf(
                database.getVariableValue(getResources().getString(R.string.coins_en)));

        coins.setText(numberOfCoins);
    }
}
