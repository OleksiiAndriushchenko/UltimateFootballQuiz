package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.andaleksei.ultimatefootballquiz.R.id.playerResult;

public class popupWindowPlayWithFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window_play_with_friend);

        boolean winner = getIntent().getBooleanExtra("winner", true);

        int numberOfQuestions = getIntent().getIntExtra("number of questions", -1);

        int playerRightAnswers = getIntent().getIntExtra("player right answers", -1);
        int opponentRightAnswers = getIntent().getIntExtra("opponent right answers", -1);

        TextView winnerTextview, loserTextview;

        if (winner) {
            winnerTextview = (TextView) findViewById(playerResult);
            loserTextview = (TextView) findViewById(R.id.opponentResult);
        } else {
            winnerTextview = (TextView) findViewById(R.id.opponentResult);
            loserTextview = (TextView) findViewById(playerResult);
        }


        winnerTextview.setBackgroundColor(Color.parseColor("#2E7D32"));
        loserTextview.setBackgroundColor(Color.parseColor("#C62828"));

        String winnerStr = "", loserStr = "";

        winnerStr += "you win!\n";
        loserStr += "you loose!\n";

        if (winner) {
            winnerStr = winnerStr + "right answers : " + String.valueOf(playerRightAnswers);
            winnerStr = winnerStr + " out of 10";

            loserStr = loserStr + "right answers : " + Integer.toString(opponentRightAnswers);
            loserStr = loserStr + " out of 10";
        } else {
            loserStr = loserStr + "right answers : " + String.valueOf(playerRightAnswers);
            loserStr = loserStr + " out of 10";

            winnerStr = winnerStr + "right answers : " + Integer.toString(opponentRightAnswers);
            winnerStr = winnerStr + " out of 10";
        }

        winnerTextview  .setText(winnerStr);
        loserTextview.setText(loserStr);

        Button returnButton = (Button) findViewById(R.id.returnButton);
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent playWithFriendGame = new Intent(popupWindowPlayWithFriend.this, playWithFriendGame.class);

                finish();

                startActivity(playWithFriendGame);

            }
        });
    }
}
