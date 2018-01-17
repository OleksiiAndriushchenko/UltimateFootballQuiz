package com.warped.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class playWithFriendGame extends AppCompatActivity {

    private dataBase database;

    private ArrayList<item> allFootballers;
    private ArrayList<String> playerChoosenFootballers;
    private ArrayList<String> opponentChoosenFootballers;

    private int quantity;

    private int firstIterator = 0;
    private int secondIterator = 0;

    private int playerRightAnswers = 0;
    private int opponentRightAnswers = 0;

    private boolean firstFaster;

    private final String tableName = "footballerTable";
    private final String drawable = "drawable";

    private ImageView playerPhoto;
    private ImageView opponentPhoto;

    private List<TextView> playerOptions;
    private List<TextView> opponentOptions;

    myCountDownTimer timer;
    long timeRemaining;

    boolean destroyed = false;

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
                options.get(i).setText(
                        footballerName.replace("_", " ").toUpperCase());
            } else {
                int randomFootballer;

                int size = allFootballers.size();

                do {
                    randomFootballer = random.nextInt(size - 1);
                } while (usedNumbers.contains(randomFootballer));

                footballerName = allFootballers.get(randomFootballer).getName();

                usedNumbers.add(getFootballerId(footballerName));

                options.get(i).setText(
                        footballerName.replace("_", " ").toUpperCase());
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
            choosenFootballers.add(footballer.getName().toUpperCase());
        }

        return choosenFootballers;
    }

    private void initialConditions() {
        int resId1 = getResources().getIdentifier(playerChoosenFootballers
                .get(firstIterator).toLowerCase(), drawable, getPackageName());
        int resId2 = getResources().getIdentifier(opponentChoosenFootballers
                .get(secondIterator).toLowerCase(), drawable, getPackageName());

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
            int resID = getResources().getIdentifier(
                    playerChoosenFootballers.get(firstIterator).toLowerCase(),
                    "drawable",
                    getPackageName());
            playerPhoto.setImageResource(resID);

            setAnswers(player, playerChoosenFootballers.get(firstIterator));
        } else {
            int resID = getResources().getIdentifier(
                    opponentChoosenFootballers.get(secondIterator).toLowerCase(),
                    "drawable",
                        getPackageName());
            opponentPhoto.setImageResource(resID);

            setAnswers(player, opponentChoosenFootballers.get(secondIterator));
        }
    }

    private View.OnClickListener onPlayerAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView choosenAnswer = (TextView) v;
            String answer = choosenAnswer.getText().toString();

            String rightFootballerName = playerChoosenFootballers.get(firstIterator)
                    .replace("_", " ");

            if (firstIterator < quantity) {
                firstIterator++;
                if (firstIterator == quantity) unSetOnClickListeners(playerOptions);
            }

            if (answer.compareTo(rightFootballerName) == 0)
                playerRightAnswers++;

            if (firstIterator < quantity) {
                nextPhoto(true);
            } else {
                if (secondIterator == quantity) showResult();
                else {
                    playerPhoto.setImageResource(R.drawable.tick);
                    firstFaster = true;
                }
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

            if (secondIterator < quantity) {
                secondIterator++;
                if (secondIterator == quantity) unSetOnClickListeners(opponentOptions);
            }

            if (answer.compareTo(rightFootballerName) == 0)
                opponentRightAnswers++;

            if (secondIterator < quantity) {
                nextPhoto(false);
            } else {
                if (firstIterator == quantity) showResult();
                else {
                    opponentPhoto.setImageResource(R.drawable.tick);
                    firstFaster = false;
                }
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

        timer.cancel();

        Intent popupWindow = new Intent(playWithFriendGame.this, popupWindowPlayWithFriend.class);

        boolean winner;

        if (playerRightAnswers > opponentRightAnswers) winner = true;
        else winner = false;

        popupWindow.putExtra("winner", winner);

        popupWindow.putExtra("number of questions", quantity);

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

        quantity = database.getVariableValue("players");

        allFootballers = new ArrayList<item>();

        for (int i = 1; i <= size; i++) {
            if (database.getCompletedState(i, "footballerTable") == 1) {
                allFootballers.add(database.getData(i, "footballerTable"));
            }
        }

        playerChoosenFootballers = chooseFootballers(quantity);
        opponentChoosenFootballers = new ArrayList<String>(playerChoosenFootballers);

        Collections.shuffle(opponentChoosenFootballers);

        initialConditions();

        myCountDownTimer timerBeforeGame = new myCountDownTimer(
                3000,
                500,
                1);

        timerBeforeGame.start();

        timeRemaining = (long)database.getVariableValue("time") * 1000;
        timer = new myCountDownTimer(
                timeRemaining,
                1000,
                2);

        LinearLayout window = (LinearLayout) findViewById(R.id.allWindow);

        window.setVisibility(View.GONE);

        setOnClickListeners();
    }

    @Override
    protected void onPause() {

        super.onPause();

        timer.cancel();

        destroyed = true;

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (destroyed) {
            timer = new myCountDownTimer(
                    timeRemaining,
                    1000,
                    2);

            timer.start();

            destroyed = false;
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        timer.cancel();

    }

    private void setTimer() {
        timer.start();
    }

    private class myCountDownTimer extends CountDownTimer {

        int timer;

        public myCountDownTimer(long millisInFuture, long countDownInterval, int varTimer) {
            super(millisInFuture, countDownInterval);
            timer = varTimer;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            timeRemaining = millisUntilFinished;

            long seconds = millisUntilFinished / 1000;

            if (timer == 1) {
                TextView countDownUp = (TextView) findViewById(R.id.countdownUp);

                TextView countDownDown = (TextView) findViewById(R.id.countdownDown);

                countDownUp.setText(String.valueOf(seconds));
                countDownDown.setText(String.valueOf(seconds));
            } else {
                TextView playerTimer = (TextView) findViewById(R.id.timerPlayer);

                TextView opponentTimer = (TextView) findViewById(R.id.timerOpponent);

                playerTimer.setText(String.valueOf(seconds));

                opponentTimer.setText(String.valueOf(seconds));
            }

        }

        @Override
        public void onFinish() {

            if (timer == 1) {
                LinearLayout window = (LinearLayout) findViewById(R.id.allWindow);

                setTimer();

                window.setVisibility(View.VISIBLE);

                TextView countDownUp = (TextView) findViewById(R.id.countdownUp);

                TextView countDownDown = (TextView) findViewById(R.id.countdownDown);

                countDownUp.setVisibility(View.GONE);
                countDownDown.setVisibility(View.GONE);
            } else {
                Intent popupWindow = new Intent(playWithFriendGame.this, popupWindowPlayWithFriend.class);

                boolean winner = (playerRightAnswers > opponentRightAnswers ||
                                  playerRightAnswers == opponentRightAnswers && firstFaster);

                popupWindow.putExtra("winner", winner);

                finish();

                startActivity(popupWindow);
            }

        }
    }
}