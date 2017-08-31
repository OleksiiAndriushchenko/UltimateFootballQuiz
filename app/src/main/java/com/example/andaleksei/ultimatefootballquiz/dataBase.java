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
        Log.v("BASE", "" + dbHelper.hashCode());
    }

    public long insertVariable(String var, long val) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.NAME, var);
        contentValues.put(dataBaseHelper.VALUE, val);
        long id = db.insert(dataBaseHelper.TABLE_NAME_VARIABLES, null, contentValues);
        return id;
    }

    public void updateVariable(String var, int val) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.NAME, var);
        contentValues.put(dataBaseHelper.VALUE, val);
        int id = db.update(dataBaseHelper.TABLE_NAME_VARIABLES, contentValues,
                dataBaseHelper.NAME + " = ?", new String[]{var});
    }

    public int getVariableData(String var) {
        int val = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.TABLE_NAME_VARIABLES;
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            val = cursor.getInt(cursor.getColumnIndex(dataBaseHelper.VALUE));
        }
        return val;
    }


    public long insertData(footballer fb) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.UID, fb.getId());
        contentValues.put(dataBaseHelper.NAME, fb.getName());
        contentValues.put(dataBaseHelper.ACCESS, fb.getAccess());
        long id = db.insert(dataBaseHelper.TABLE_NAME_FOOTBALLERS, null , contentValues);
        return id;
    }

    public int getNextFootballerId() {
        footballer fb = new footballer();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.TABLE_NAME_FOOTBALLERS + " WHERE " +
                dataBaseHelper.ACCESS + " = 1 ORDER BY " + dataBaseHelper.UID +
                " DESC LIMIT 1";
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            fb.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            fb.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            fb.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
        }
        return fb.getId();
    }

    public footballer getData(int id) {
        footballer fb = new footballer();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.TABLE_NAME_FOOTBALLERS + " WHERE " +
                dataBaseHelper.UID + " = " + id;
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            fb.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            fb.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            fb.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
        }
        return fb;
    }

    public void unlockNextFootballer() {
        footballer fb = new footballer();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.TABLE_NAME_FOOTBALLERS + " WHERE " +
                dataBaseHelper.ACCESS + " = 0 ORDER BY " + dataBaseHelper.UID +
                " ASC LIMIT 1";
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            fb.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.UID)));
            fb.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.NAME)));
            fb.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.ACCESS)));
        }
        fb.setAccess(1);

        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.UID, fb.getId());
        contentValues.put(dataBaseHelper.NAME, fb.getName());
        contentValues.put(dataBaseHelper.ACCESS, fb.getAccess());
        db.update(dataBaseHelper.TABLE_NAME_FOOTBALLERS, contentValues,
                dataBaseHelper.NAME + " = ?", new String[]{fb.getName()});
    }


    public void upgrade() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 1);
    }

}
