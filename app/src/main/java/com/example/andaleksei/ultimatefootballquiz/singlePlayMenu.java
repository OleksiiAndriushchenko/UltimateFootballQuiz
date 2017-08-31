package com.example.andaleksei.ultimatefootballquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class singlePlayMenu extends Activity {

    private GridView gridView;
    private dataBase database;
    private int currentFootballerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_menu);
        database = new dataBase(this);

        gridView = (GridView) findViewById(R.id.gridview);
        currentFootballerId = database.getNextFootballerId();
        myAdapter gridAdapter = new myAdapter(singlePlayMenu.this, currentFootballerId);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position++;
                if (position <= currentFootballerId) {
                    database.updateVariable("footballer", position);
                    Intent singlePlayGameIntent = new Intent(singlePlayMenu.this, singlePlayGame.class);

                    startActivity(singlePlayGameIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "You cann't choose locked footballer!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        database = new dataBase(this);

        gridView = (GridView) findViewById(R.id.gridview);
        currentFootballerId = database.getNextFootballerId();
        myAdapter gridAdapter = new myAdapter(singlePlayMenu.this, currentFootballerId);
        gridView.setAdapter(gridAdapter);
    }
}
