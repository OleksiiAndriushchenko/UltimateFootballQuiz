package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class singlePlayMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_mode);

        final dataBase database = new dataBase(this);

        LinearLayout playersButton = (LinearLayout) findViewById(R.id.players);
        playersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int data = database.updateVariable("playMode", 1);
                Log.v("MODE", "" + data);
                Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayMenu.class);

                startActivity(singlePlayMenuIntent);
            }
        });

        LinearLayout  clubsButton = (LinearLayout) findViewById(R.id.clubs);
        clubsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.updateVariable("playMode", 2);
                Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayMenu.class);

                startActivity(singlePlayMenuIntent);
            }
        });
    }
}
