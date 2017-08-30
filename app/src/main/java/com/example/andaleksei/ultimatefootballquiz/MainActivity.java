package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView playButton = (TextView) findViewById(R.id.main_menu);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playModesIntent = new Intent(MainActivity.this, playModes.class);

                startActivity(playModesIntent);
            }
        });

        TextView upgradeButton = (TextView) findViewById(R.id.upgrade);
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBase database = new dataBase(MainActivity.this);
                database.upgrade();
            }
        });
    }
}
