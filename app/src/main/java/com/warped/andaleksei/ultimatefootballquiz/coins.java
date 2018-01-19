package com.warped.andaleksei.ultimatefootballquiz;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.warped.andaleksei.ultimatefootballquiz.Utils.PreferenceUtils;

public class coins extends AppCompatActivity implements RewardedVideoAdListener {
    private AdView mAdView;
    stringAdapter myStringAdapter;
    boolean mUserClickedAd = false;
    boolean mAdLoadFailed = false;
    TextView rateApp, watchVideo;
    private dataBase mDb;
    private RewardedVideoAd mRewardedVideoAd;
    private LinearLayout mRateAppLinearLayour;
    private LinearLayout mWatchAdLinearLayout;
    private ProgressBar mLoadingAdProgressBar;
    private  Toast mToast;
    private void showText(String text){
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        mToast.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);
        mDb = new dataBase(this);
        myStringAdapter = new stringAdapter();

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        mRateAppLinearLayour = (LinearLayout)findViewById(R.id.ll_rate_app);
        mWatchAdLinearLayout = (LinearLayout)findViewById(R.id.ll_watch_ad);

        // if app is already rated then hide this option
        if (PreferenceUtils.isRatedAtPlayStore(this))
            mRateAppLinearLayour.setVisibility(View.INVISIBLE);


        // load reward ad
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd(getString(R.string.reward_ad_id),
                new AdRequest.Builder().build());

        mLoadingAdProgressBar = (ProgressBar)findViewById(R.id.loading_ad_pb);

        rateApp = (TextView) findViewById(R.id.rate);
        rateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGooglePlayIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName()));
                if (startGooglePlayIntent.resolveActivity(getPackageManager()) != null){
                    PreferenceUtils.setRatedAtPlayStore(coins.this, true);
                    mRateAppLinearLayour.setVisibility(View.INVISIBLE);
                    mDb.updateVariable("coins",mDb.getVariableValue("coins")+500);
                    startActivity(startGooglePlayIntent);
                }
                else
                    Toast.makeText(coins.this,myStringAdapter.getString(coins.this,"error_rate_app"),Toast.LENGTH_SHORT).show();
            }
        });
        rateApp.setText(myStringAdapter.getString(this, "rateApp"));
        rateApp.setTypeface(custom_font);

        watchVideo = (TextView) findViewById(R.id.video);
        watchVideo.setText(myStringAdapter.getString(this, "video"));
        watchVideo.setTypeface(custom_font);
        // set onClick action for add button
        watchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserClickedAd = true;

                if (mAdLoadFailed)
                    showText(myStringAdapter.getString(coins.this,"error_loading_ad"));
                else if (mRewardedVideoAd.isLoaded())
                    mRewardedVideoAd.show();
                else{
                    // wait until ad is loaded
                    // (in this case ad is shown from onRewardedVideoAdLoaded)
                    showText(myStringAdapter.getString(coins.this,"wait_until_ad_loads"));
                    mLoadingAdProgressBar.setVisibility(View.VISIBLE);
                }
                //Toast.makeText(coins.this,"Wait...",Toast.LENGTH_SHORT);
            }
        });



    }

    @Override
    public void onRewardedVideoAdLoaded() {
       // if ad is loaded and user clicked an ad
        // then shows it and hides progress bar
        if (mUserClickedAd){
            mRewardedVideoAd.show();
            mLoadingAdProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        // resets clicked flag state
        mUserClickedAd = false;
        // and tries to load next ad
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        // give user reward
        mDb.updateVariable("coins",mDb.getVariableValue("coins")+250);

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        mAdLoadFailed = true;
        if (mUserClickedAd)
            showText(myStringAdapter.getString(this,"error_loading_ad"));
    }
}
