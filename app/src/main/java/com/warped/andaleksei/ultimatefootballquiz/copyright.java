package com.warped.andaleksei.ultimatefootballquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class copyright extends AppCompatActivity {
    //private int mCountTextViewClicked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyright);

        final dataBase database = new dataBase(this);

        int language = database.getVariableValue("language");

        TextView text = (TextView) findViewById(R.id.text);
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCountTextViewClicked++;
//                if (mCountTextViewClicked >= 5){
//                    mCountTextViewClicked = 0;
//                    database.updateVariable("coins",database.getVariableValue("coins")+50000);
//                }
//            }
//        });
        InputStream fis;

        if (language == 0)
            fis = getApplicationContext().getResources().openRawResource(R.raw.authors_en);
        else
            fis = getApplicationContext().getResources().openRawResource(R.raw.authors_ru);

        if (fis != null) {

            try {
                InputStreamReader chapterReader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(chapterReader);

                String line = "";

                line += buffreader.readLine() + "\n";
                line += buffreader.readLine() + "\n";
                line += buffreader.readLine();

                text.setText(line);
            } catch (Exception e) {

            } finally {

                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
