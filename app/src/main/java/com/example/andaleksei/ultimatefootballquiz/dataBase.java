package com.example.andaleksei.ultimatefootballquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by ANDaleksei on 26.08.17.
 */

public class dataBase {
    private static dataBaseHelper dbHelper;

    public dataBase(Context context) {
        dbHelper = dataBaseHelper.getInstance(context);
    }

    public long insertVariable(String var, long val) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.NAME, var);
        contentValues.put(dataBaseHelper.VALUE, val);
        long id = db.insert(dataBaseHelper.TABLE_NAME_VARIABLES, null, contentValues);
        return id;
    }

    public int updateVariable(String var, int val) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.NAME, var);
        contentValues.put(dataBaseHelper.VALUE, val);
        int id = db.update(dataBaseHelper.TABLE_NAME_VARIABLES, contentValues,
                dataBaseHelper.NAME + " = ?", new String[]{var});
        return id;
    }

    public int getVariableData(String var) {
        int val = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.TABLE_NAME_VARIABLES +
                " WHERE Name = '" + var + "'";
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            val = cursor.getInt(cursor.getColumnIndex(dataBaseHelper.VALUE));
        }
        return val;
    }


    public long insertItem(item object, String tableName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.UID, object.getId());
        contentValues.put(dataBaseHelper.NAME, object.getName());
        contentValues.put(dataBaseHelper.ACCESS, object.getAccess());
        long id = db.insert(tableName, null , contentValues);
        return id;
    }

    public int getNextItemId(String tableName) {
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
        }
        return object;
    }

    public void unlockNextItem(String tableName) {
        item object = new item();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String arg = "SELECT * FROM " + tableName + " WHERE " +
                dataBaseHelper.ACCESS + " = 0 ORDER BY " + dataBaseHelper.UID +
                " ASC LIMIT 1";
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            object.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            object.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            object.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
        }
        object.setAccess(1);

        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.UID, object.getId());
        contentValues.put(dataBaseHelper.NAME, object.getName());
        contentValues.put(dataBaseHelper.ACCESS, object.getAccess());
        db.update(tableName, contentValues,
                dataBaseHelper.NAME + " = ?", new String[]{object.getName()});
    }

    public String getVarName(int data) {
        switch (data) {
            case 1:
                return "footballer";
            case 2:
                return "club";
        }
        return "null";
    }

    public String getTableName(int data) {
        switch (data) {
            case 1:
                return dataBaseHelper.TABLE_NAME_FOOTBALLERS;
            case 2:
                return dataBaseHelper.TABLE_NAME_CLUBS;
        }
        return "null";
    }


    public void upgrade() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 1);
    }

}
