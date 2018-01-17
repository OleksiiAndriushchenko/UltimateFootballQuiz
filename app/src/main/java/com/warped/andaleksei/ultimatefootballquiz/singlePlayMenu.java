package com.warped.andaleksei.ultimatefootballquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class singlePlayMenu extends Activity {

    private GridView gridView;
    private dataBase database;

    private int gameMode;
    private int currentObjectId;

    private String tableName;

    private final String COINS = "coins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_menu);

        database = new dataBase(this);

        Intent intent = getIntent();
        gameMode = intent.getIntExtra("game mode", 0);

        tableName = database.getTableName(gameMode);

        gridView = (GridView) findViewById(R.id.gridview);

        // last open item
        currentObjectId = database.getLastUnlockedItem(tableName);

        // number of elements in current table
        int size = database.getNumberOfElements(gameMode);

        myAdapter gridAdapter = new myAdapter(singlePlayMenu.this, currentObjectId, size, tableName);

        gridView.setAdapter(gridAdapter);

        int firstUncompletedItem = 0;

        for (int i = 1; i <= database.getLastUnlockedItem(tableName); i++) {
            if (database.getCompletedState(i, tableName) == 0 && firstUncompletedItem == 0)
                firstUncompletedItem = i;
        }

        gridView.smoothScrollToPosition(firstUncompletedItem);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position++;

                if (position <= currentObjectId) {
                    Intent singlePlayGameIntent = new Intent(singlePlayMenu.this, singlePlayGame.class);

                    singlePlayGameIntent.putExtra("game mode", gameMode);
                    singlePlayGameIntent.putExtra("choosen item", position);

                    startActivity(singlePlayGameIntent);
                }
            }
        });

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(singlePlayMenu.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(singlePlayMenu.this, coins.class);

                startActivity(coinsIntent);
            }
        });

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));

    }

    @Override
    protected void onResume() {
        super.onResume();

        int size = database.getNumberOfElements(gameMode);

        gridView = (GridView) findViewById(R.id.gridview);

        currentObjectId = database.getLastUnlockedItem(tableName);

        myAdapter gridAdapter = new myAdapter(singlePlayMenu.this, currentObjectId, size, tableName);

        gridView.setAdapter(gridAdapter);

        int firstUncompletedItem = 0;

        int lastUnlockedItem = database.getLastUnlockedItem(tableName);

        for (int i = 1; i <= lastUnlockedItem; i++) {

            if (i % 10 == 0) {
                int a;
            }

            if (database.getCompletedState(i, tableName) == 0 && firstUncompletedItem == 0)
                firstUncompletedItem = i;
        }

        gridView.smoothScrollToPosition(firstUncompletedItem);

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }
}
