package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.andaleksei.ultimatefootballquiz.R.id.returnButton;

public class popupWindowSinglePlayMode extends AppCompatActivity {

    private Intent currentIntent;

    private dataBase database;

    private final String COINS = "coins";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window_single_play_mode);

        currentIntent = getIntent();
        database = new dataBase(this);

        database.updateVariable(COINS, database.getVariableValue(COINS) + 25);

        TextView level = (TextView) findViewById(R.id.level);

        int completedItem = currentIntent.getIntExtra("choosen item", -1) - 1;
        int tableId = currentIntent.getIntExtra("game mode", -1);

        level.setText("level\n" + completedItem);

        Button continueButton = (Button) findViewById(R.id.next);

        String table = database.getTableName(tableId);

        if (completedItem < database.getLastUnlockedItem(table)) {
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent singlePlayGame = new Intent(popupWindowSinglePlayMode.this,
                            com.example.andaleksei.ultimatefootballquiz.singlePlayGame.class);

                    singlePlayGame.putExtra("game mode", currentIntent.getIntExtra("game mode", -1));
                    singlePlayGame
                            .putExtra("choosen item", currentIntent.getIntExtra("choosen item", -1));

                    popupWindowSinglePlayMode.this.finish();

                    startActivity(singlePlayGame);
                }
            });
        } else {
            continueButton.setVisibility(Button.INVISIBLE);
        }

        Button returnButton = (Button) findViewById(R.id.returnPopup);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindowSinglePlayMode.this.finish();

            }
        });
    }
}
