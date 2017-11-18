package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
    private final String hint1 = "remove char";
    private final String hint2 = "remove chars";
    private final String hint3 = "add char";
    private final String hint4 = "add chars";

    private item currentObject;

    private dataBase database;

    private ImageView image;
    private ImageView previousClub;

    TextView answer[];
    int answerNum[];

    TextView firstR[] = new TextView[charactersPerRow];
    TextView secondR[] = new TextView[charactersPerRow];

    private View.OnClickListener onCharacterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;

            if (num < wordLength && !clickedView.getText().toString().equals("")) {
                answer[num].setText(clickedView.getText());
                clickedView.setText("");

                int i = 0;
                do {
                    if (i < charactersPerRow) {
                        if (clickedView.equals(firstR[i])) {
                            answerNum[num] = i;
                        }
                    } else {
                        if (clickedView.equals(secondR[i - charactersPerRow])) {
                            answerNum[num] = i;
                        }
                    }
                } while (++i < charactersPerRow * 2);

                num++;

                if (num == wordLength) {
                    checkAnswer();
                }
            }
        }
    };

    private View.OnClickListener onCharacterAnswerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView clickedView = (TextView) v;

            if (num > 0 && clickedView.equals(answer[num - 1])) {
                if (answerNum[num - 1] < charactersPerRow) {
                    firstR[answerNum[num - 1]].setText(clickedView.getText());
                } else {
                    secondR[answerNum[num - 1] - charactersPerRow].setText(clickedView.getText());
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
            userInput += answer[i].getText().toString();
        }

        if (objectName.equals(userInput)) {
            Toast.makeText(getApplicationContext(), "Congratulation, you are right!!!",
                    Toast.LENGTH_SHORT).show();

            if (currentObject.getCompleted() == 0) {
                currentObject.setCompleted(1);

                database.setCompleted(tableName, currentObject);

                if (database.reachThreshold(gameMode)) {
                    database.unlockNextItem(tableName, 10);
                }
            }

            Intent popupWindow = new Intent(
                    singlePlayGame.this, popupWindowSinglePlayMode.class);

            popupWindow.putExtra("choosen item", itemIndex + 1);
            popupWindow.putExtra("game mode", gameMode);

            this.finish();

            startActivity(popupWindow);

        } else {
            Toast.makeText(getApplicationContext(), "Unfortunately, you are wrong!!!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private int getResourceId(String resourceName) {
        return getResources().getIdentifier(resourceName, "drawable", getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_game);

        database = new dataBase(this);

        Intent intent = getIntent();

        gameMode = intent.getIntExtra("game mode", -1);

        tableName = database.getTableName(gameMode);

        // data - index of item
        itemIndex = intent.getIntExtra("choosen item", -1);

        currentObject = database.getData(itemIndex, tableName);
        objectName = currentObject.getName();

        // if mode is transfer then we should parse string
        if (tableName.compareTo("transferTable") == 0) {
            objectName = currentObject.divideString();

            int numberOfClubs = currentObject.getNumberOfClubs();

            LinearLayout clubsRow = (LinearLayout) findViewById(R.id.clubs);

            int clubSize = getResources().getDimensionPixelSize(R.dimen.club_size);
            int arrowSize = getResources().getDimensionPixelSize(R.dimen.arrow_size);

            LinearLayout.LayoutParams layoutParamsClub = new LinearLayout.LayoutParams(clubSize, clubSize);
            layoutParamsClub.setMargins(8, 0, 8, 0);
            LinearLayout.LayoutParams layoutParamsArrow = new LinearLayout.LayoutParams(arrowSize, arrowSize);

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
                clubsRow.addView(club);

                if (i == 0) {
                    club.setBackgroundResource(R.drawable.club_background);
                    previousClub = club;
                }
            }

        }


        // if name consists of 2 words
        if (objectName.contains("_")) {
            wordLength = objectName.length() - 1;
            objectName = objectName.replace("_", " ");
            space = objectName.indexOf(' ');

            answer = new TextView[wordLength];
            answerNum = new int[wordLength];
        } else {
            wordLength = objectName.length();
            answer = new TextView[wordLength];
            answerNum = new int[wordLength];
        }

        image = (ImageView) findViewById(R.id.imageView);
        TextView years = (TextView) findViewById(R.id.years);
        int resID;

        if (gameMode != 3) {
            Resources res = getResources();
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
            image.setBackgroundColor(Color.WHITE);
        }

        LinearLayout ll1 = (LinearLayout) findViewById(R.id.answer1);
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.answer2);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size),
                getResources().getDimensionPixelSize(R.dimen.character_size));

        for (int i = 0; i < wordLength; ++i) {
            answer[i] = new TextView(this);
            if (i  == 0) {
                params.setMargins(0, 0, 8, 0);
            } else if (i == wordLength - 1) {
                params.setMargins(8, 0, 0, 0);
            } else params.setMargins(8, 0, 8, 0);

            answer[i].setLayoutParams(params);
            answer[i].setText("");
            answer[i].setTextSize(20f);
            answer[i].setTextColor(Color.WHITE);
            answer[i].setBackgroundColor(Color.BLACK);
            answer[i].setGravity(Gravity.CENTER);

            if (objectName.contains(" ")) {
                if (i < space) {
                    ll1.addView(answer[i]);
                } else {
                    ll2.addView(answer[i]);
                }
            } else ll1.addView(answer[i]);

            answer[i].setOnClickListener(onCharacterAnswerClickListener);
        }

        LinearLayout firstRow = (LinearLayout) findViewById(R.id.first_row);
        LinearLayout secondRow = (LinearLayout) findViewById(R.id.second_row);


        params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.character_size_choice),
                getResources().getDimensionPixelSize(R.dimen.character_size_choice));

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

            firstR[i].setText(new String(new char[] {foot.get(i)} ));
            secondR[i].setText(new String(new char[] {foot.get(i + charactersPerRow)} ));

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

                hintsIntent.putExtra("add char", 5);
                hintsIntent.putExtra("remove char", 5);
                hintsIntent.putExtra("number of chars", objectName.length());

                startActivity(hintsIntent);
            }
        });

    }

    private void clearAnswer() {
        for (int i = num - 1; i >= 0; i--) {
            Log.v("TAG", "" + i + " " + num + " " + wordLength);
            int index = answerNum[i];

            if (index < charactersPerRow) {
                firstR[index].setText(answer[i].getText());
            } else {
                secondR[index - charactersPerRow].setText(answer[i].getText());
            }

            answer[i].setText("");
        }

        num = 0;
    }

    private void makeHint1(int charsToDelete) {
        String tempName = objectName;

        int iter = 0;

        for (int i = 0; i < charactersPerRow * 2; i++) {
            if (i < charactersPerRow && iter < charsToDelete) {
                String oneChar = firstR[i].getText().toString();

                if (tempName.contains(oneChar)) {
                    tempName = tempName.replaceFirst(oneChar, "");
                } else {
                    firstR[i].setText("");
                    iter++;
                }

            } else if (iter < charsToDelete) {
                String oneChar = secondR[i - charactersPerRow].getText().toString();

                if (tempName.contains(oneChar)) {
                    tempName = tempName.replaceFirst(oneChar, "");
                } else {
                    secondR[i - charactersPerRow].setText("");
                    iter++;
                }
            }
        }
    }

    private void makeHint2() {
        String tempName = objectName;

        for (int i = 0; i < charactersPerRow * 2; i++) {
            if (i < charactersPerRow) {
                String oneChar = firstR[i].getText().toString();

                if (tempName.contains(oneChar)) {
                    tempName = tempName.replaceFirst(oneChar, "");
                } else {
                    firstR[i].setText("");
                }

            } else {
                String oneChar = secondR[i - charactersPerRow].getText().toString();

                if (tempName.contains(oneChar)) {
                    tempName = tempName.replaceFirst(oneChar, "");
                } else {
                    secondR[i - charactersPerRow].setText("");
                }
            }
        }
    }

    private void makeHint3(int charsToAdd) {
        String tempName = objectName;

        for (int i = 0; i < charsToAdd; i++) {
            char currentChar = tempName.charAt(i);

            for (int j = 0; j < charactersPerRow * 2; j++) {
                if (j < charactersPerRow) {
                    if (firstR[j].getText().length() > 0 &&
                            firstR[j].getText().charAt(0) == currentChar) {
                        answer[num].setText(firstR[j].getText().toString());
                        answerNum[i] = j;
                        firstR[j].setText("");
                        num++;
                        break;
                    }
                } else {
                    if (secondR[j - charactersPerRow].getText().length() > 0 &&
                            secondR[j - charactersPerRow].getText().charAt(0) == currentChar) {
                        answer[num].setText(secondR[j - charactersPerRow].getText().toString());
                        answerNum[i] = j;
                        secondR[j - charactersPerRow].setText("");
                        num++;
                        break;
                    }
                }
            }
        }
    }

    private void makeHint4() {
        String tempName = objectName;

        for (int i = 0; i < tempName.length(); i++) {
            char currentChar = tempName.charAt(i);

            for (int j = 0; j < charactersPerRow * 2; j++) {
                if (j < charactersPerRow) {
                    if (firstR[j].getText().length() > 0 &&
                            firstR[j].getText().charAt(0) == currentChar) {
                        answer[i].setText(firstR[j].getText());
                        answerNum[i] = j;
                        firstR[j].setText("");
                        num++;
                        break;
                    }
                } else {
                    if (secondR[j - charactersPerRow].getText().length() > 0 &&
                            secondR[j - charactersPerRow].getText().charAt(0) == currentChar) {
                        answer[i].setText(secondR[j - charactersPerRow].getText());
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

        if (database.getVariableValue(hint1) != 0) {
            makeHint1(database.getVariableValue(hint1));

            database.updateVariable(hint1, 0);
        }

        if (database.getVariableValue(hint2) != 0) {
            makeHint2();

            database.updateVariable(hint2, 0);
        }

        if (database.getVariableValue(hint3) != 0) {
            makeHint3(database.getVariableValue(hint3));

            database.updateVariable(hint3, 0);
        }

        if (database.getVariableValue(hint4) != 0) {
            makeHint4();

            database.updateVariable(hint4, 0);
        }
    }
}
