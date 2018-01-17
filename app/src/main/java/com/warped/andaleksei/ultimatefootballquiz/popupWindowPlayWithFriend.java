package com.warped.andaleksei.ultimatefootballquiz;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.warped.andaleksei.ultimatefootballquiz.R.id.playerResult;

public class popupWindowPlayWithFriend extends AppCompatActivity {

    private stringAdapter myStringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window_play_with_friend);

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

        String winnerStr = myStringAdapter.getString(this, "win");
        String loserStr = myStringAdapter.getString(this, "loose");

        winnerTextview.setText(winnerStr);
        loserTextview.setText(loserStr);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        winnerTextview.setTypeface(custom_font);
        loserTextview.setTypeface(custom_font);
    }
}
