package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.andaleksei.ultimatefootballquiz.R.id.playerResult;

public class playWithFriendGame extends AppCompatActivity {

    private dataBase database;

    private PopupWindow result;

    private ArrayList<item> allFootballers;
    private ArrayList<String> playerChoosenFootballers;
    private ArrayList<String> opponentChoosenFootballers;

    private int firstIterator = 0;
    private int secondIterator = 0;

    private int playerRightAnswers = 0;
    private int opponentRightAnswers = 0;

    private final String tableName = "footballerTable";
    private final String drawable = "drawable";

    private ImageView playerPhoto;
    private ImageView opponentPhoto;

    private List<TextView> playerOptions;
    private List<TextView> opponentOptions;

    private int playerRightVariant;
    private int opponentRightVariant;

    private int getFootballerId(String footballer) {
        for (int i = 0; i < allFootballers.size(); i++) {
            if (footballer.compareTo(allFootballers.get(i).getName()) == 0) {
                return i;
            }
        }
        return -1;
    }

    private void setAnswers(boolean player, String rightAnswer) {
        List<TextView> options = player ? playerOptions : opponentOptions;
        String footballerName = player ? playerChoosenFootballers.get(firstIterator) :
                opponentChoosenFootballers.get(secondIterator);
        fillPlayerOptions(options, rightAnswer);
    }

    private void fillPlayerOptions(List<TextView> options, String rightAnswer) {

        Random random = new Random();

        int rightVariant = random.nextInt(3);

        List<Integer> usedNumbers = new ArrayList<Integer>();

        usedNumbers.add(getFootballerId(rightAnswer));

        for (int i = 0; i < 4; i++) {
            String footballerName;

            if (i == rightVariant) {
                footballerName = rightAnswer;
                options.get(i).setText(footballerName.replace("_", " "));
            } else {
                int randomFootballer;

                int size = database.getLastUnlockedItem(tableName);

                do {
                    randomFootballer = random.nextInt(size);
                } while (usedNumbers.contains(randomFootballer));

                usedNumbers.add(randomFootballer);

                footballerName = allFootballers.get(randomFootballer).getName();
                options.get(i).setText(footballerName.replace("_", " "));
            }
        }

    }

    private ArrayList<String> chooseFootballers(int quantity) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < allFootballers.size(); i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        ArrayList<String> choosenFootballers = new ArrayList<String>();

        for (int i = 0; i < quantity; i++) {
            int index = numbers.get(i);
            item footballer = allFootballers.get(index);
            choosenFootballers.add(footballer.getName());
        }

        return choosenFootballers;
    }

    private void initialConditions() {
        int resId1 = getResources().getIdentifier(playerChoosenFootballers
                .get(firstIterator), drawable, getPackageName());
        int resId2 = getResources().getIdentifier(opponentChoosenFootballers
                .get(secondIterator), drawable, getPackageName());

        playerPhoto.setImageResource(resId1);
        opponentPhoto.setImageResource(resId2);

        playerOptions = new ArrayList<TextView>();
        opponentOptions = new ArrayList<TextView>();

        playerOptions.add((TextView) findViewById(R.id.playerOption_1));
        playerOptions.add((TextView) findViewById(R.id.playerOption_2));
        playerOptions.add((TextView) findViewById(R.id.playerOption_3));
        playerOptions.add((TextView) findViewById(R.id.playerOption_4));

        opponentOptions.add((TextView) findViewById(R.id.opponentOption_1));
        opponentOptions.add((TextView) findViewById(R.id.opponentOption_2));
        opponentOptions.add((TextView) findViewById(R.id.opponentOption_3));
        opponentOptions.add((TextView) findViewById(R.id.opponentOption_4));

        setAnswers(true, playerChoosenFootballers.get(firstIterator));
        setAnswers(false, opponentChoosenFootballers.get(secondIterator));

    }

    private void nextPhoto(boolean player) {
        if (player) {
            int resID = getResources().getIdentifier(playerChoosenFootballers.get(firstIterator), "drawable", getPackageName());
            playerPhoto.setImageResource(resID);

            setAnswers(player, playerChoosenFootballers.get(firstIterator));
        } else {
            int resID = getResources().getIdentifier(opponentChoosenFootballers.get(secondIterator), "drawable", getPackageName());
            opponentPhoto.setImageResource(resID);

            setAnswers(player, opponentChoosenFootballers.get(secondIterator));
        }
    }

    private class myCountDownTimer extends CountDownTimer {

        public myCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long seconds = millisUntilFinished / 1000;

            TextView countDownUp = (TextView) findViewById(R.id.countdownUp);

            TextView countDownDown = (TextView) findViewById(R.id.countdownDown);

            countDownUp.setText(String.valueOf(seconds));
            countDownDown.setText(String.valueOf(seconds));
        }

        @Override
        public void onFinish() {
            LinearLayout window = (LinearLayout) findViewById(R.id.allWindow);

            window.setVisibility(View.VISIBLE);

            TextView countDownUp = (TextView) findViewById(R.id.countdownUp);

            TextView countDownDown = (TextView) findViewById(R.id.countdownDown);

            countDownUp.setVisibility(View.GONE);
            countDownDown.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener onPlayerAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView choosenAnswer = (TextView) v;
            String answer = choosenAnswer.getText().toString();

            String rightFootballerName = playerChoosenFootballers.get(firstIterator)
                    .replace("_", " ");

            if (firstIterator < 10) {
                firstIterator++;
                if (firstIterator == 10) unSetOnClickListeners(playerOptions);
            }

            if (answer.compareTo(rightFootballerName) == 0)
                playerRightAnswers++;

            if (firstIterator < 10) {
                nextPhoto(true);
            } else {
                if (secondIterator == 10) showResult();
                else playerPhoto.setImageResource(R.drawable.done);
            }
        }
    };

    private View.OnClickListener onOpponentAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView choosenAnswer = (TextView) v;
            String answer = choosenAnswer.getText().toString();

            String rightFootballerName = opponentChoosenFootballers.get(secondIterator)
                    .replace("_", " ");

            if (secondIterator < 10) {
                secondIterator++;
                if (secondIterator == 10) unSetOnClickListeners(opponentOptions);
            }

            if (answer.compareTo(rightFootballerName) == 0)
                opponentRightAnswers++;

            if (secondIterator < 10) {
                nextPhoto(false);
            } else {
                if (firstIterator == 10) showResult();
                else opponentPhoto.setImageResource(R.drawable.done);
            }
        }
    };

    private void setOnClickListeners() {
        for (int i = 0; i < 4; i++) {
            playerOptions.get(i).setOnClickListener(onPlayerAnswerClickListener);
            opponentOptions.get(i).setOnClickListener(onOpponentAnswerClickListener);
        }
    }

    private void unSetOnClickListeners(List<TextView> options) {
        for (int i = 0; i < 4; i++) {
            options.get(i).setOnClickListener(null);
        }
    }

    private void showResult() {

        Intent popupWindow = new Intent(playWithFriendGame.this, popupWindowPlayWithFriend.class);

        boolean winner;

        if (playerRightAnswers > opponentRightAnswers) winner = true;
        else winner = false;

        popupWindow.putExtra("winner", winner);

        popupWindow.putExtra("number of questions", 10);

        popupWindow.putExtra("player right answers", playerRightAnswers);
        popupWindow.putExtra("opponent right answers", opponentRightAnswers);

        finish();

        startActivity(popupWindow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_friend_game);

        database = new dataBase(this);

        playerPhoto = (ImageView) findViewById(R.id.playerPhoto);
        opponentPhoto = (ImageView) findViewById(R.id.opponentPhoto);

        int size = database.getLastUnlockedItem("footballerTable");

        allFootballers = new ArrayList<item>();

        for (int i = 1; i <= size; i++) {
            allFootballers.add(database.getData(i, "footballerTable"));
        }

        playerChoosenFootballers = chooseFootballers(10);
        opponentChoosenFootballers = new ArrayList<String>(playerChoosenFootballers);
        Collections.shuffle(opponentChoosenFootballers);

        initialConditions();

        myCountDownTimer countDownTimer = new myCountDownTimer(3500, 500);
        countDownTimer.start();

        LinearLayout window = (LinearLayout) findViewById(R.id.allWindow);

        window.setVisibility(View.GONE);

        setOnClickListeners();
    }
}