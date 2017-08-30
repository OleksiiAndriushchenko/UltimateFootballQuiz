package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class singlePlayMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_mode);

        LinearLayout singlePlayButton = (LinearLayout) findViewById(R.id.players);
        singlePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayGame.class);

                startActivity(singlePlayMenuIntent);
            }
        });
    }
}
