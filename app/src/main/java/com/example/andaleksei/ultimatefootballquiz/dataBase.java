package com.example.andaleksei.ultimatefootballquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by ANDaleksei on 26.08.17.
 */

public class dataBase {
    private static dataBaseHelper dbHelper;
    // completed items in each mode

    private List<String> tableNames;

    public dataBase(Context context) {
        if (dbHelper == null) {
            dbHelper = dataBaseHelper.getInstance(context);
        }

        tableNames = Arrays.asList(dbHelper.TABLE_NAME_FOOTBALLERS,
                dbHelper.TABLE_NAME_CLUBS,
                dbHelper.TABLE_NAME_TRANSFERS,
                dbHelper.TABLE_NAME_LEGENDS);
    }

    public long insertItem(item object, String tableName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.UID, object.getId());
        contentValues.put(dataBaseHelper.NAME, object.getName());
        contentValues.put(dataBaseHelper.ACCESS, object.getAccess());
        contentValues.put(dataBaseHelper.COMPLETED, object.getCompleted());

        long id = db.insert(tableName, null , contentValues);

        return id;
    }

    public int getLastUnlockedItem(String tableName) {
        item object = new item();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String arg = "SELECT * FROM " + tableName + " WHERE " +
                dataBaseHelper.ACCESS + " = 1 ORDER BY " + dataBaseHelper.UID +
                " DESC LIMIT 1";
        Cursor cursor =db.rawQuery(arg, null);

        while (cursor.moveToNext())

        {
            object.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            object.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            object.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
            object.setCompleted(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.COMPLETED)));
        }

        return object.getId();
    }

    public item getData(int id, String tableName) {
        item object = new item();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String arg = "SELECT * FROM " + tableName + " WHERE " +
                dataBaseHelper.UID + " = " + id;

        Cursor cursor =db.rawQuery(arg, null);

        while (cursor.moveToNext())
        {
            object.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            object.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            object.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
            object.setCompleted(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.COMPLETED)));
        }

        return object;
    }

    public int getCompletedState(int id, String tableName) {
        item object = new item();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String arg = "SELECT * FROM " + tableName + " WHERE " +
                dataBaseHelper.UID + " = " + id;

        Cursor cursor = db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            object.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            object.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            object.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
            object.setCompleted(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.COMPLETED)));
        }
        return object.getCompleted();
    }

    public void unlockNextItem(String tableName, int quantity) {
        item object = new item();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String arg = "SELECT * FROM " + tableName + " WHERE " +
                dataBaseHelper.ACCESS + " = 0 ORDER BY " + dataBaseHelper.UID +
                " ASC LIMIT " + quantity;

        Cursor cursor =db.rawQuery(arg, null);

        List<item> newItems = new ArrayList<item>();

        while (cursor.moveToNext())
        {
            object.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            object.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            object.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
            object.setCompleted(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.COMPLETED)));

            object.setAccess(1);
            newItems.add(object);
        }

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        for (int i = 0; i < newItems.size(); i++) {
            contentValues.put(dataBaseHelper.UID, newItems.get(i).getId());
            contentValues.put(dataBaseHelper.NAME, newItems.get(i).getName());
            contentValues.put(dataBaseHelper.ACCESS, newItems.get(i).getAccess());
            contentValues.put(dataBaseHelper.COMPLETED, newItems.get(i).getCompleted());

            db.update(tableName, contentValues,
                    dataBaseHelper.NAME + " = ?", new String[]{newItems.get(i).getName()});
        }
    }

    public String getTableName(int data) {
        switch (data) {
            case 1:
                return dataBaseHelper.TABLE_NAME_FOOTBALLERS;
            case 2:
                return dataBaseHelper.TABLE_NAME_CLUBS;
            case 3:
                return dataBaseHelper.TABLE_NAME_TRANSFERS;
            case 4:
                return dataBaseHelper.TABLE_NAME_LEGENDS;
        }
        return "null";
    }

    public int getNumberOfElements(int gameMode) {
        switch (gameMode) {
            case 1:
                return 450;
            case 2:
                return 150;
            case 3:
                return 150;
            case 4:
                return 75;
        }
        return -1;
    }

    public int getNumberOfCompletedItems(int table) {
        String tableName = tableNames.get(table - 1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String arg = "SELECT * FROM " + tableName + " WHERE " + dataBaseHelper.COMPLETED + " = 1";

        Cursor cursor =db.rawQuery(arg, null);

        int numberOfCompletedItems = 0;

        while (cursor.moveToNext()) numberOfCompletedItems++;

        return numberOfCompletedItems;

    }

    public void setCompleted(String tableName, item object) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dataBaseHelper.UID, object.getId());
        contentValues.put(dataBaseHelper.NAME, object.getName());
        contentValues.put(dataBaseHelper.ACCESS, object.getAccess());
        contentValues.put(dataBaseHelper.COMPLETED, object.getCompleted());

        db.update(tableName, contentValues,
                dataBaseHelper.NAME + " = ?", new String[]{object.getName()});
    }

    public boolean reachThreshold(int table) {

        int completedItems = getNumberOfCompletedItems(table);
        double unlockedItems = (double) getLastUnlockedItem(tableNames.get(table - 1));

        boolean canOpen = completedItems > unlockedItems * 0.7;

        return canOpen;
    }

    public int getVariableValue(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String arg = "SELECT * FROM " + dataBaseHelper.TABLE_NAME_VARIABLES;

        Cursor cursor =db.rawQuery(arg, null);

        while (cursor.moveToNext()) {
            String varName = cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME));
            if (varName.compareTo(name) == 0)
                return cursor.getInt(cursor.getColumnIndex(dataBaseHelper.VALUE));
        }

        return -1;
    }

    public void updateVariable(String name, int value) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(dataBaseHelper.NAME, name);
        contentValues.put(dataBaseHelper.VALUE, value);

        db.update(dbHelper.TABLE_NAME_VARIABLES, contentValues,
                dataBaseHelper.NAME + " = ?", new String[]{name});
    }
}
