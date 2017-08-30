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
    dataBaseHelper dbHelper;

    public dataBase(Context context) {
        dbHelper = new dataBaseHelper(context);
    }

    public long insertData(footballer fb) {
        SQLiteDatabase dbb = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.getUID(), fb.getId());
        contentValues.put(dataBaseHelper.getNAME(), fb.getName());
        contentValues.put(dataBaseHelper.getACCESS(), fb.getAccess());
        long id = dbb.insert(dataBaseHelper.getTableName(), null , contentValues);
        return id;
    }

    public int getNextFootballerId() {
        footballer fb = new footballer();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.getTableName() + " WHERE " +
                dataBaseHelper.getACCESS() + " = 1 ORDER BY " + dataBaseHelper.getUID() +
                " DESC LIMIT 1";
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            fb.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.getUID())));
            fb.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.getNAME())));
            fb.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.getACCESS())));
        }
        return fb.getId();
    }

    public footballer getData(int id) {
        footballer fb = new footballer();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.getTableName() + " WHERE " +
                dataBaseHelper.getUID() + " = " + id;
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            fb.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.getUID())));
            fb.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.getNAME())));
            fb.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.getACCESS())));
        }
        return fb;
    }

    public void unlockNextFootballer() {
        footballer fb = new footballer();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String arg = "SELECT * FROM " + dataBaseHelper.getTableName() + " WHERE " +
                dataBaseHelper.getACCESS() + " = 0 ORDER BY " + dataBaseHelper.getUID() +
                " ASC LIMIT 1";
        Log.v("GAME", arg);
        Cursor cursor =db.rawQuery(arg, null);
        while (cursor.moveToNext())
        {
            fb.setId(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.getUID())));
            fb.setName(cursor.getString(cursor.getColumnIndex(dataBaseHelper.getNAME())));
            fb.setAccess(cursor.getInt(cursor.getColumnIndex(dataBaseHelper.getACCESS())));
        }
        fb.setAccess(1);
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataBaseHelper.getUID(), fb.getId());
        contentValues.put(dataBaseHelper.getNAME(), fb.getName());
        contentValues.put(dataBaseHelper.getACCESS(), fb.getAccess());
        Log.v("GAME", fb.getId() + " " + fb.getName() + " " + fb.getAccess());
        db.update(dataBaseHelper.getTableName(), contentValues,
                dataBaseHelper.getNAME() + " = ?", new String[]{fb.getName()});
    }


    public void upgrade() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 1);
    }

}
