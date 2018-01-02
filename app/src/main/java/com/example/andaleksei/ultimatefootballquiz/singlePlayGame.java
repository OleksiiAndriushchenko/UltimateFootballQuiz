package com.example.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class singlePlayGame extends AppCompatActivity {

    private int num = 0;
    private int space;
    private int wordLength;

    private int gameMode;
    private int itemIndex;

    private int charactersPerRow = 8;

    private String objectName;
    private String tableName;

    private final String COINS = "coins";

    private final String hint1 = "add char";
    private final String hint2 = "add chars";

    private item currentObject;

    private dataBase database;

    private ImageView image;
    private ImageView previousClub;

    List<TextView> answer;
    int answerNum[];

    TextView firstR[] = new TextView[charactersPerRow];
    TextView secondR[] = new TextView[charactersPerRow];

    private View.OnClickListener onCharacterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;

            if (num < wordLength && !clickedView.getText().equals("")) {

                int position = firstEmptyCharachter();

                answer.get(position).setText(clickedView.getText());

                clickedView.setText("");

                int i = 0;

                do {
                    if (i < charactersPerRow) {
                        if (clickedView.equals(firstR[i])) {
                            answerNum[position] = i;
                        }
                    } else {
                        if (clickedView.equals(secondR[i - charactersPerRow])) {
                            answerNum[position] = i;
                        }
                    }
                } while (++i < charactersPerRow * 2);

                num++;

            }
        }
    };

    private View.OnClickListener onCharacterAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;

            int position = positionOfCharachter(clickedView);
            CharSequence text = answer.get(position).getText();

            if (num > 0 && !text.equals("")) {
                if (answerNum[position] < charactersPerRow) {
                    firstR[answerNum[position]].setText(clickedView.getText());
                } else {
                    secondR[answerNum[position] - charactersPerRow].setText(clickedView.getText());
                }

                clickedView.setText("");

                num--;
            }
        }
    };

    private View.OnClickListener onClubClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView clickedView = (ImageView) v;

            previousClub.setBackgroundColor(Color.TRANSPARENT);
            previousClub = clickedView;

            clickedView.setBackgroundResource(R.drawable.club_background);

            LinearLayout clubsRow = (LinearLayout) findViewById(R.id.clubs);

            for (int i = 0; i < clubsRow.getChildCount(); i++) {
                if (clickedView.equals(clubsRow.getChildAt(i))) {
                    int clubId = getResources().getIdentifier(currentObject.getClubName(i / 2), "drawable", getPackageName());
                    image.setImageResource(clubId);

                    TextView years = (TextView) findViewById(R.id.years);
                    years.setText(currentObject.getYears(i / 2));
                }
            }
        }
    };

    private int firstEmptyCharachter() {
        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i).getText().equals(""))
                return i;
        }

        return -1;
    }

    private int positionOfCharachter(TextView clickedView) {
        for (int i = 0; i < answer.size(); i++) {
            if (clickedView.equals(answer.get(i))) {
                return i;
            }
        }

        return -1;
    }

    private void randomizeArray(ArrayList<Character> arrayToRandom) {
        Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            int num1 = random.nextInt(arrayToRandom.size());
            int num2 = random.nextInt(arrayToRandom.size());
            Character object = arrayToRandom.get(num);
            arrayToRandom.set(num, arrayToRandom.get(num2));
            arrayToRandom.set(num2, object);
        }
    }

    private void checkAnswer() {

        String userInput = "";

        for (int i = 0; i < num; i++) {
            userInput += answer.get(i).getText().toString();
        }

        if (objectName.equals(userInput)) {

            Intent popupWindow = new Intent(
                    singlePlayGame.this, popupWindowSinglePlayMode.class);

            if (currentObject.getCompleted() == 0) {
                currentObject.setCompleted(1);

                database.updateVariable(COINS, database.getVariableValue(COINS) + 25);

                popupWindow.putExtra("add coins", 1);

                database.setCompleted(tableName, currentObject);

                if (database.reachThreshold(gameMode)){
                    int quantity = gameMode == 4 ? 5 : 10;
                    database.unlockNextItem(tableName, quantity);
                }

                if (database.canOpenLegends())
                    database.unlockNextItem("legendTable", 5);
            } else
                popupWindow.putExtra("add coins", 0);

            popupWindow.putExtra("choosen item", itemIndex + 1);
            popupWindow.putExtra("game mode", gameMode);

            this.finish();

            startActivity(popupWindow);

        } else {
            startAnimation();
        }

    }

    private int getResourceId(String resourceName) {
        return getResources().getIdentifier(resourceName, "drawable", getPackageName());
    }

    private void startAnimation() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_vibro);

        for (int i = 0; i < answer.size(); i++) {
            answer.get(i).startAnimation(animation);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_game);

        database = new dataBase(this);

        database.updateVariable(hint1, 0);
        database.updateVariable(hint2, 0);

        Intent intent = getIntent();

        gameMode = intent.getIntExtra("game mode", -1);

        tableName = database.getTableName(gameMode);

        // data - index of item
        itemIndex = intent.getIntExtra("choosen item", -1);

        currentObject = database.getData(itemIndex, tableName);
        objectName = currentObject.getName().toUpperCase();

        LinearLayout clubsRow = (LinearLayout) findViewById(R.id.clubs);

        // if mode is transfer then we should parse string
        if (tableName.compareTo("transferTable") == 0) {
            objectName = currentObject.divideString().toUpperCase();

            int numberOfClubs = currentObject.getNumberOfClubs();

            int clubSize = getResources().getDimensionPixelSize(R.dimen.club_size);
            int arrowSize = getResources().getDimensionPixelSize(R.dimen.arrow_size);

            LinearLayout.LayoutParams layoutParamsClub = new LinearLayout.LayoutParams(clubSize, clubSize);
            layoutParamsClub.setMargins(8, 0, 8, 0);
            if (currentObject.getNumberOfClubs() > 3) layoutParamsClub.weight = 1;

            LinearLayout.LayoutParams layoutParamsArrow = new LinearLayout.LayoutParams(arrowSize, arrowSize);
            if (currentObject.getNumberOfClubs() > 3) layoutParamsArrow.weight = 1;

            for (int i = 0; i < numberOfClubs; i++) {
                if (i > 0) {
                    ImageView arrow = new ImageView(this);
                    int arrowId = getResources().getIdentifier("arrow", "drawable", getPackageName());
                    arrow.setImageResource(arrowId);
                    arrow.setLayoutParams(layoutParamsArrow);
                    clubsRow.addView(arrow);
                }

                ImageView club = new ImageView(this);
                String clubName = currentObject.getClubName(i);
                int clubID = getResources().getIdentifier(clubName , "drawable", getPackageName());
                club.setImageResource(clubID);
                club.setLayoutParams(layoutParamsClub);
                club.setPadding(4, 4, 4, 4);
                club.setOnClickListener(onClubClickListener);
                club.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                clubsRow.addView(club);

                if (i == 0) {
                    club.setBackgroundResource(R.drawable.club_background);
                    previousClub = club;
                }
            }
        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) clubsRow.getLayoutParams();
            params.weight = 0.0f;
            clubsRow.setLayoutParams(params);
        }


        // if name consists of 2 words
        if (objectName.contains("_")) {
            wordLength = objectName.length() - 1;
            objectName = objectName.replace("_", " ");
            space = objectName.indexOf(' ');

            answerNum = new int[wordLength];
        } else {
            wordLength = objectName.length();
            answerNum = new int[wordLength];
        }

        answer = new ArrayList<TextView>();

        image = (ImageView) findViewById(R.id.imageView);
        TextView years = (TextView) findViewById(R.id.years);
        int resID;

        if (gameMode != 3) {

            if (gameMode == 2) {
                resID = getResourceId(currentObject.getName() + "_edited");

                if (resID == 0)
                    resID = getResourceId(currentObject.getName());

            } else resID = getResourceId(currentObject.getName());

            years.setHeight(0);
        } else {
            resID = getResourceId(currentObject.getClubName(0));

            years.setHeight(R.dimen.character_size);
            years.setText(currentObject.getYears(0));
        }

        image.setImageResource(resID);
        if (gameMode == 1 || gameMode == 4) {
            image.setBackgroundColor(Color.parseColor("#FDD835"));
        }

        LinearLayout ll1 = (LinearLayout) findViewById(R.id.answer1);
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.answer2);

        LinearLayout.LayoutParams params;

        TextView tempTextview;

        for (int i = 0; i < wordLength; ++i) {

            tempTextview = new TextView(this);

            params = new LinearLayout.LayoutParams(
                    getResources().getDimensionPixelSize(R.dimen.character_size),
                    getResources().getDimensionPixelSize(R.dimen.character_size));

            params.setMargins(8, 0, 8, 0);

            if (objectName.contains(" ")) {
                if (i < space) {
                    params.weight = (space > 6) ? 1 : 0;
                } else params.weight = objectName.length() - space > 6 ? 1 : 0;
            } else params.weight = objectName.length() > 6 ? 1 : 0;

            tempTextview.setLayoutParams(params);
            tempTextview.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.character_size));
            tempTextview.setText("");
            tempTextview.setTextSize(20f);
            tempTextview.setTextColor(Color.WHITE);
            tempTextview.setBackgroundColor(Color.BLACK);
            tempTextview.setGravity(Gravity.CENTER);

            if (objectName.contains(" ")) {
                if (i < space) {
                    ll1.addView(tempTextview);
                } else {
                    ll2.addView(tempTextview);
                }
            } else ll1.addView(tempTextview);

            tempTextview.setOnClickListener(onCharacterAnswerClickListener);

            answer.add(tempTextview);
        }

        LinearLayout firstRow = (LinearLayout) findViewById(R.id.first_row);
        LinearLayout secondRow = (LinearLayout) findViewById(R.id.second_row);


        params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size_choice),
                getResources().getDimensionPixelSize(R.dimen.character_size_choice));

        params.weight = 1;

        ArrayList<Character> foot = new ArrayList<Character>();

        objectName = objectName.replace(" ", "");

        for (int i = 0; i < 2 * charactersPerRow; ++i) {
            if (i < wordLength) {
                foot.add(objectName.charAt(i));
            } else {
                Random random = new Random();
                int number = 97 + random.nextInt(26);
                foot.add((char)number);
            }
        }

        randomizeArray(foot);

        for (int i = 0; i < charactersPerRow; i++) {
            firstR[i] = new TextView(this);
            secondR[i] = new TextView(this);

            if (i == 0) {
                params.setMargins(0, 8, 8, 8);
            } else if (i == charactersPerRow + 1) {
                params.setMargins(8, 8, 0, 8);
            } else params.setMargins(8, 8, 8, 8);

            firstR[i].setLayoutParams(params);
            secondR[i].setLayoutParams(params);

            firstR[i].setText(foot.get(i).toString().toUpperCase());
            secondR[i].setText(foot.get(i + charactersPerRow).toString().toUpperCase());

            firstR[i].setTextSize(20f);
            secondR[i].setTextSize(20f);

            firstR[i].setTextColor(Color.WHITE);
            secondR[i].setTextColor(Color.WHITE);

            firstR[i].setBackgroundColor(Color.BLACK);
            secondR[i].setBackgroundColor(Color.BLACK);

            firstR[i].setGravity(Gravity.CENTER);
            secondR[i].setGravity(Gravity.CENTER);

            firstRow.addView(firstR[i]);
            secondRow.addView(secondR[i]);

            firstR[i].setOnClickListener(onCharacterClickListener);
            secondR[i].setOnClickListener(onCharacterClickListener);
        }

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));

        ImageView hint = (ImageView) findViewById(R.id.hints);

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hintsIntent = new Intent(singlePlayGame.this, hints.class);

                hintsIntent.putExtra("add char", 10);
                hintsIntent.putExtra("number of chars", objectName.length());

                startActivity(hintsIntent);
            }
        });

        ImageView settings = (ImageView) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(singlePlayGame.this, settings.class);

                startActivity(settingsIntent);
            }
        });

        LinearLayout coinsContainer = (LinearLayout) findViewById(R.id.coinsContainer);

        coinsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coinsIntent = new Intent(singlePlayGame.this, coins.class);

                startActivity(coinsIntent);
            }
        });

        ImageView ok = (ImageView) findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

    }

    private void clearAnswer() {
        for (int i = wordLength - 1; i >= 0; i--) {
            Log.v("TAG", "" + i + " " + num + " " + wordLength);

            if (!answer.get(i).getText().equals("")) {
                int index = answerNum[i];

                if (index < charactersPerRow) {
                    firstR[index].setText(answer.get(i).getText());
                } else {
                    secondR[index - charactersPerRow].setText(answer.get(i).getText());
                }

                answer.get(i).setText("");
            }
        }

        num = 0;
    }

    private void makeHint1(int charsToAdd) {
        String tempName = objectName;

        for (int i = 0; i < charsToAdd; i++) {
            char currentChar = tempName.charAt(i);

            for (int j = 0; j < charactersPerRow * 2; j++) {
                if (j < charactersPerRow) {
                    if (firstR[j].getText().length() > 0 &&
                            firstR[j].getText().charAt(0) == currentChar) {
                        answer.get(num).setText(firstR[j].getText().toString());
                        answerNum[i] = j;
                        firstR[j].setText("");
                        num++;
                        break;
                    }
                } else {
                    if (secondR[j - charactersPerRow].getText().length() > 0 &&
                            secondR[j - charactersPerRow].getText().charAt(0) == currentChar) {
                        answer.get(num).setText(secondR[j - charactersPerRow].getText().toString());
                        answerNum[i] = j;
                        secondR[j - charactersPerRow].setText("");
                        num++;
                        break;
                    }
                }
            }
        }
    }

    private void makeHint2() {

        String tempName = objectName;

        for (int i = 0; i < tempName.length(); i++) {
            char currentChar = tempName.charAt(i);

            for (int j = 0; j < charactersPerRow * 2; j++) {
                if (j < charactersPerRow) {
                    if (firstR[j].getText().length() > 0 &&
                            firstR[j].getText().charAt(0) == currentChar) {
                        answer.get(i).setText(firstR[j].getText());
                        answerNum[i] = j;
                        firstR[j].setText("");
                        num++;
                        break;
                    }
                } else {
                    if (secondR[j - charactersPerRow].getText().length() > 0 &&
                            secondR[j - charactersPerRow].getText().charAt(0) == currentChar) {
                        answer.get(i).setText(secondR[j - charactersPerRow].getText());
                        answerNum[i] = j;
                        secondR[j - charactersPerRow].setText("");
                        num++;
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView coins = (TextView) findViewById(R.id.coins);

        coins.setText("" + database.getVariableValue(COINS));

        clearAnswer();

        if (database.getVariableValue(hint1) != 0)
            makeHint1(database.getVariableValue(hint1));

        if (database.getVariableValue(hint2) != 0)
            makeHint2();
    }
}
