package com.warped.andaleksei.ultimatefootballquiz;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.warped.andaleksei.ultimatefootballquiz.Utils.PreferenceUtils;

import static com.warped.andaleksei.ultimatefootballquiz.R.id.playerResult;

public class popupWindowPlayWithFriend extends AppCompatActivity {

    private stringAdapter myStringAdapter;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window_play_with_friend);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_play_with_friend_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        myStringAdapter = new stringAdapter();

        boolean winner = getIntent().getBooleanExtra("winner", true);

        int numberOfQuestions = getIntent().getIntExtra("number of questions", -1);

        int playerRightAnswers = getIntent().getIntExtra("player right answers", -1);
        int opponentRightAnswers = getIntent().getIntExtra("opponent right answers", -1);

        TextView winnerTextview, loserTextview;

        if (winner) {
            winnerTextview = (TextView) findViewById(playerResult);
            loserTextview = (TextView) findViewById(R.id.opponentResult);

            RelativeLayout window = (RelativeLayout) findViewById(R.id.allWindow);

            window.setBackgroundResource(R.drawable.result_grad_rotated);
        } else {

            winnerTextview = (TextView) findViewById(R.id.opponentResult);
            loserTextview = (TextView) findViewById(playerResult);

            RelativeLayout window = (RelativeLayout) findViewById(R.id.allWindow);

            window.setBackgroundResource(R.drawable.result_grad);
        }

        String winnerStr = myStringAdapter.getString(this, "won");
        String loserStr = myStringAdapter.getString(this, "lost");

        winnerTextview.setText(winnerStr);
        loserTextview.setText(loserStr);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        winnerTextview.setTypeface(custom_font);
        loserTextview.setTypeface(custom_font);
    }

    @Override
    public void onBackPressed() {
        if(PreferenceUtils.getClickedCount(this) >= 35) {
            PreferenceUtils.setClickedCount(this,0);
            if (mInterstitialAd.isLoaded())
                mInterstitialAd.show();
            finish();
//            else
//                Toast.makeText(this,"Ad is not loaded",Toast.LENGTH_SHORT);
        }
        else {
            super.onBackPressed();
        }
    }
}

