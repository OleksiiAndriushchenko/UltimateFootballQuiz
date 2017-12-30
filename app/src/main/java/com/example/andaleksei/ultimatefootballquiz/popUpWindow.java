package com.example.andaleksei.ultimatefootballquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class popUpWindow extends AppCompatActivity {

    dataBase database;

    boolean language;

    private String getFirstVariant() {

        String players = language ? "Игроки" : "Players";
        String clubs = language ? "Клубы" : "Clubs";
        String transfers = language ? "Трансферы" : "Transfers";
        String completed = language ? "пройдено" : "completed";
        String lastText = language ? "Пройди больше уровней" : "Complete more levels";

        String text = language ? "Этот режим закрыт!\n" : "This mode is locked!\n";

        if (database.getNumberOfCompletedItems(1) < 300) {
            text += players + ": " + String.valueOf(database.getNumberOfCompletedItems(1));
            text += " / 300,\n";
        } else {
            text += players + ": " + completed + ",\n";
        }

        if (database.getNumberOfCompletedItems(2) < 100) {
            text += clubs + ": " + String.valueOf(database.getNumberOfCompletedItems(2));
            text += " / 100,\n";
        } else {
            text += clubs + ": " + completed + ",\n";
        }

        if (database.getNumberOfCompletedItems(3) < 70) {
            text += transfers + ": " + String.valueOf(database.getNumberOfCompletedItems(3));
            text += " / 70,\n";
        } else {
            text += transfers + ": " + completed + ",\n";
        }

        text += lastText;

        return text;
    }

    private String getSecondVariant() {

        String text = "";

        int leftPlayers = 30 - database.getNumberOfCompletedItems(1);

        String level = "";

        if (language) {

            if (leftPlayers <= 24 && leftPlayers >= 22 ||
                    leftPlayers <= 4 && leftPlayers >= 2) {
                level = " уровня ";
            } else if (leftPlayers == 21 || leftPlayers == 1) {
                level = " уровень ";
            } else level = " уровней ";

            text += "Пройди ещё " + String.valueOf(leftPlayers);
            text += level + "с игроками, чтобы открыть этот режим!";
        } else {

            level = leftPlayers == 1 ? " player " : " players ";

            text += "Complete " + String.valueOf(leftPlayers) + level + "to open this mode!";
        }

        return text;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        database = new dataBase(this);

        language = database.getVariableValue("language") == 1;

        int variant = getIntent().getIntExtra("variant", -1);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        params.dimAmount = 0.5f;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                getResources().getString(R.string.font));

        TextView textView = (TextView) findViewById(R.id.text);

        String text = "";

        if (variant == 1)
            text = getFirstVariant();
        else if (variant == 2)
            text = getSecondVariant();
        else text = language ? "Ошибка!" : "Error!";

        textView.setText(text);
        textView.setTypeface(custom_font);

        ImageView cancel = (ImageView) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.popup_animation_in, R.anim.popup_animation_out);
            }
        });
    }
}
