package com.example.andaleksei.ultimatefootballquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class copyright extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyright);

        TextView text = (TextView) findViewById(R.id.text);

        InputStream fis = getApplicationContext().getResources().openRawResource(R.raw.authors);

        if (fis != null) {

            try {
                InputStreamReader chapterReader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(chapterReader);

                String line = "";

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
