package com.warped.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class playModes extends AppCompatActivity {

    private dataBase database;

    private stringAdapter myStringAdapter;

    TextView singlePlay;
    TextView playWithFriend;

    private final String COINS = "coins";

    private void setText() {
        singlePlay.setText(myStringAdapter.getString(this, "single_play"));

        playWithFriend.setText(myStringAdapter.getString(this, "play_with_friend"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_modes);

        database = new dataBase(this);

        myStringAdapter = new stringAdapter();

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        singlePlay = (TextView)findViewById(R.id.single_play);

        singlePlay.setTypeface(custom_font);

        singlePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayModeIntent = new Intent(playModes.this, singlePlayMode.class);

                startActivity(singlePlayModeIntent);
            }
        });

        playWithFriend = (TextView) findViewById(R.id.play_with_friend);

        playWithFriend.setTypeface(custom_font);

        playWithFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (database.getNumberOfCompletedItems(1) >= 30) {
                    Intent playWithFriendModeIntent = new Intent(playModes.this, playWithFriendMode.class);

                    startActivity(playWithFriendModeIntent);
                } else {
                    Intent popupwindowIntent = new Intent(playModes.this, popUpWindow.class);

                    popupwindowIntent.putExtra("variant", 2);

                    startActivity(popupwindowIntent);
                    overridePendingTransition(R.anim.popup_animation_in, R.anim.popup_animation_out);
                }

            }
        });

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(playModes.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(playModes.this, coins.class);

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
