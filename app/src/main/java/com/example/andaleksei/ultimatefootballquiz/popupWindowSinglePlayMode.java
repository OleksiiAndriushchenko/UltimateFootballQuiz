package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class popupWindowSinglePlayMode extends AppCompatActivity {

    private Intent currentIntent;

    private dataBase database;

    private stringAdapter myStringAdapter;

    private Timer timer;

    private final String COINS = "coins";

    private void setFont(final int addCoins) {

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        TextView tv = (TextView) findViewById(R.id.level);

        tv.setTypeface(custom_font);

        tv = (TextView) findViewById(R.id.phrase);

        tv.setText(myStringAdapter.getString(this, "keep"));

        tv.setTypeface(custom_font);

        if (addCoins == 1) {
            tv = (TextView) findViewById(R.id.addCoins);

            String coins = "+25 " + myStringAdapter.getString(this, "coins");
            tv.setText(coins );

            tv.setTypeface(custom_font);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup_window_single_play_mode);

        currentIntent = getIntent();

        database = new dataBase(this);

        myStringAdapter = new stringAdapter();

        TextView level = (TextView) findViewById(R.id.level);
        TextView coins = (TextView) findViewById(R.id.coins);

        final int completedItem = currentIntent.getIntExtra("choosen item", -1) - 1;
        final int tableId = currentIntent.getIntExtra("game mode", -1);
        final int addCoins = currentIntent.getIntExtra("add coins", -1);

        setFont(addCoins);

        level.setText(myStringAdapter.getString(this, "level") + "\n" + completedItem);

        final String table = database.getTableName(tableId);

        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {

                if (completedItem < database.getLastUnlockedItem(table)) {
                    Intent singlePlayGame = new Intent(popupWindowSinglePlayMode.this,
                            com.example.andaleksei.ultimatefootballquiz.singlePlayGame.class);

                    singlePlayGame.putExtra(
                            "game mode",
                             currentIntent.getIntExtra("game mode", -1));
                    singlePlayGame.putExtra(
                            "choosen item",
                             currentIntent.getIntExtra("choosen item", -1));

                    startActivity(singlePlayGame);
                }

                finish();
            }
        }, 2000);


    }
}