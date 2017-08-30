package com.example.andaleksei.ultimatefootballquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class singlePlayMenu extends Activity {

    private GridView gridView;
    private dataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_menu);
        database = new dataBase(this);

        gridView = (GridView) findViewById(R.id.gridview);
        int currentFootballerId = database.getNextFootballerId();
        myAdapter gridAdapter = new myAdapter(singlePlayMenu.this, currentFootballerId);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "message",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
