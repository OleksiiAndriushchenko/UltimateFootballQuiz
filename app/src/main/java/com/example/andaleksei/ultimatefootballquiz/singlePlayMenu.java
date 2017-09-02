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
    private int currentObjectId;
    private String tableName;
    private String varName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_menu);
        database = new dataBase(this);
        tableName = database.getTableName(database.getVariableData("playMode"));
        varName = database.getVarName(database.getVariableData("playMode"));
        int data = database.getVariableData(varName);

        Toast.makeText(getApplicationContext(), "table = " + tableName, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "data = " + data, Toast.LENGTH_SHORT).show();

        gridView = (GridView) findViewById(R.id.gridview);
        currentObjectId = database.getNextItemId(tableName);
        myAdapter gridAdapter = new myAdapter(singlePlayMenu.this, currentObjectId);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position++;
                if (position <= currentObjectId) {
                    database.updateVariable(varName, position);
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

        gridView = (GridView) findViewById(R.id.gridview);
        currentObjectId = database.getNextItemId(tableName);
        myAdapter gridAdapter = new myAdapter(singlePlayMenu.this, currentObjectId);
        gridView.setAdapter(gridAdapter);
    }
}
