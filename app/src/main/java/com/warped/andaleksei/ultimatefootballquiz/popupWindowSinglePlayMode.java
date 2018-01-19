package com.warped.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Timer;
import java.util.TimerTask;

public class popupWindowSinglePlayMode extends AppCompatActivity {

    private Intent currentIntent;

    private dataBase database;

    private stringAdapter myStringAdapter;

    private Timer timer;

    private final String COINS = "coins";
    private InterstitialAd mInterstitialAd;

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

        // load ads
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        TextView level = (TextView) findViewById(R.id.level);
        TextView coins = (TextView) findViewById(R.id.coins);

        final int completedItem = currentIntent.getIntExtra("choosen item", -1) - 1;
        final int tableId = currentIntent.getIntExtra("game mode", -1);
        final int addCoins = currentIntent.getIntExtra("add coins", -1);

        setFont(addCoins);

        level.setText(myStringAdapter.getString(this, "level") + "\n" + completedItem);

        final String table = database.getTableName(tableId);
        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();
        else
            Toast.makeText(this,"Not loaded yet",Toast.LENGTH_SHORT);
        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {

                if (completedItem < database.getLastUnlockedItem(table)) {
                    Intent singlePlayGame = new Intent(popupWindowSinglePlayMode.this,
                            com.warped.andaleksei.ultimatefootballquiz.singlePlayGame.class);

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
        }, 1500);


    }
}
