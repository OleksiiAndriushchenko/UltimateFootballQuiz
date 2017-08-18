package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class playModes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_modes);

        TextView singlePlayButton = (TextView) findViewById(R.id.single_play);
        singlePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayModeIntent = new Intent(playModes.this, singlePlayMode.class);

                startActivity(singlePlayModeIntent);
            }
        });

        TextView playWithFriendButton = (TextView) findViewById(R.id.play_with_friend);
        playWithFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playWithFriendModeIntent = new Intent(playModes.this, playWithFriendMode.class);

                startActivity(playWithFriendModeIntent);
            }
        });

        TextView dailyChallengeButton = (TextView) findViewById(R.id.daily_challenge);
        dailyChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dailyChallengeModeIntent = new Intent(playModes.this, DailyChallengeMode.class);

                startActivity(dailyChallengeModeIntent);
            }
        });
    }
}
