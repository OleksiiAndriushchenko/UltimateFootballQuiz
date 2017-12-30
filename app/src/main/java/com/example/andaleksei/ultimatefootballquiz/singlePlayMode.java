package com.example.andaleksei.ultimatefootballquiz;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class singlePlayMode extends AppCompatActivity {

    private dataBase database;

    stringAdapter myStringAdapter;

    private TextView players, clubs, transfers, legends;

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

    private void setFont() {

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        players.setTypeface(custom_font);

        clubs.setTypeface(custom_font);

        transfers.setTypeface(custom_font);

        legends.setTypeface(custom_font);
    }
    
    private void setText() {
        players.setText(myStringAdapter.getString(this, "players"));

        clubs.setText(myStringAdapter.getString(this, "clubs"));

        transfers.setText(myStringAdapter.getString(this, "transfers"));

        legends.setText(myStringAdapter.getString(this, "legends"));
    }

    private void setPhotoToLegends() {
        LinearLayout legendContainer = (LinearLayout) findViewById(R.id.legend);
        ImageView legendPhoto = (ImageView) findViewById(R.id.legendPhoto);

        if (database.getLastUnlockedItem("legendTable") >= 1) {
            legendPhoto.setImageResource(R.drawable.henry);

            legendContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent singlePlayMenuIntent = new Intent(singlePlayMode.this, singlePlayMenu.class);

                    singlePlayMenuIntent.putExtra("game mode", 4);

                    startActivity(singlePlayMenuIntent);
                }
            });
        } else {
            legendPhoto.setImageResource(R.drawable.lock);

            legendContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent popupwindowIntent = new Intent(singlePlayMode.this, popUpWindow.class);

                    popupwindowIntent.putExtra("variant", 1);

                    startActivity(popupwindowIntent);
                    overridePendingTransition(R.anim.popup_animation_in, R.anim.popup_animation_out);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_mode);

        database = new dataBase(this);

        myStringAdapter = new stringAdapter();

        players = (TextView) findViewById(R.id.textViewFootballers);
        clubs = (TextView) findViewById(R.id.textViewClubs);
        transfers = (TextView) findViewById(R.id.textViewTransfers);
        legends = (TextView) findViewById(R.id.textViewLegends);

        updateProgressBars();

        setText();

        setFont();

        setPhotoToLegends();

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

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(singlePlayMode.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(singlePlayMode.this, coins.class);

                startActivity(coinsIntent);
            }
        });

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateProgressBars();

        setText();

        setPhotoToLegends();

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));
    }
}
