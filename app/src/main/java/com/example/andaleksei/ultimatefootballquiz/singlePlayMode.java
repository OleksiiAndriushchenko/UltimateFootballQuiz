package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class singlePlayMode extends AppCompatActivity {

    private dataBase database;

    private final String drawable = "drawable";
    private final String COINS = "coins";

    private void updateProgressBars() {
        dataBase database = new dataBase(this);

        List<ProgressBar> bars = new ArrayList<ProgressBar>();

        bars.add((ProgressBar) findViewById(R.id.progressBarFootballers));
        bars.add((ProgressBar) findViewById(R.id.progressBarClubs));
        bars.add((ProgressBar) findViewById(R.id.progressBarTransfers));
        bars.add((ProgressBar) findViewById(R.id.progressBarLegends));

        for (int i = 0; i < bars.size(); i++) {
            Integer completedItems = database.getNumberOfCompletedItems(i + 1);
            Integer numberOfAllItems = database.getNumberOfElements(i + 1);
            Double result = completedItems.doubleValue() / numberOfAllItems * 100;
            bars.get(i).setProgress(result.intValue());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_mode);

        database = new dataBase(this);

        updateProgressBars();

        LinearLayout playersButton = (LinearLayout) findViewById(R.id.players);
        playersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayMenu.class);

                singlePlayMenuIntent.putExtra("game mode", 1);

                startActivity(singlePlayMenuIntent);
            }
        });

        LinearLayout  clubsButton = (LinearLayout) findViewById(R.id.clubs);
        clubsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayMenu.class);

                singlePlayMenuIntent.putExtra("game mode", 2);

                startActivity(singlePlayMenuIntent);
            }
        });

        LinearLayout  transferButton = (LinearLayout) findViewById(R.id.transfer);
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayMenu.class);

                singlePlayMenuIntent.putExtra("game mode", 3);

                startActivity(singlePlayMenuIntent);
            }
        });

        LinearLayout  legendButton = (LinearLayout) findViewById(R.id.legend);
        legendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayMenu.class);

                singlePlayMenuIntent.putExtra("game mode", 4);

                startActivity(singlePlayMenuIntent);
            }
        });

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateProgressBars();

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }
}
