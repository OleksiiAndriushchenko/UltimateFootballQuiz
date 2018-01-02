package com.example.andaleksei.ultimatefootballquiz;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.R.attr.name;
import static android.R.attr.value;
import static android.R.attr.widgetLayout;
import static java.security.AccessController.getContext;

/**
 * Created by ANDaleksei on 26.08.17.
 */

public class dataBaseHelper extends SQLiteOpenHelper {
    private static dataBaseHelper instance = null;

    public static final String DATEBASE_NAME = "myDatebase";
    public static final int DATEBASE_Version = 1;


    public static final String TABLE_NAME_FOOTBALLERS = "footballerTable";
    public static final String UID = "_id";
    public static final String COMPLETED = "Completed";
    public static final String NAME = "Name";
    public static final String ACCESS = "Access";
    public static final String VALUE = "Value";
    public static final String CREATE_TABLE_FOOTBALLERS = "CREATE TABLE " + TABLE_NAME_FOOTBALLERS + " (" +
            UID + " INTEGER , " +
            NAME + " VARCHAR(255) ," +
            COMPLETED + " INTEGER , " +
            ACCESS + " INTEGER);";
    public static final String DROP_TABLE_FOOTBALLERS = "DROP TABLE IF EXISTS " + TABLE_NAME_FOOTBALLERS;

    public static final String TABLE_NAME_CLUBS = "clubTable";
    public static final String CREATE_TABLE_CLUBS = "CREATE TABLE " + TABLE_NAME_CLUBS + " (" +
            UID + " INTEGER , " +
            NAME + " VARCHAR(255) , " +
            COMPLETED + " INTEGER , " +
            ACCESS + " INTEGER);";
    public static final String DROP_TABLE_CLUBS = "DROP TABLE IF EXISTS " + TABLE_NAME_CLUBS;


    public static final String TABLE_NAME_TRANSFERS = "transferTable";
    public static final String CREATE_TABLE_TRANSFERS = "CREATE TABLE " + TABLE_NAME_TRANSFERS + " (" +
            UID + " INTEGER , " +
            NAME + " VARCHAR(255) ," +
            COMPLETED + " INTEGER , " +
            ACCESS + " INTEGER);";
    public static final String DROP_TABLE_TRANSFERS = "DROP TABLE IF EXISTS " + TABLE_NAME_TRANSFERS;

    public static final String TABLE_NAME_LEGENDS = "legendTable";
    public static final String CREATE_TABLE_LEGENDS = "CREATE TABLE " + TABLE_NAME_LEGENDS + " (" +
            UID + " INTEGER , " +
            NAME + " VARCHAR(255) ," +
            COMPLETED + " INTEGER , " +
            ACCESS + " INTEGER);";
    public static final String DROP_TABLE_LEGENDS = "DROP TABLE IF EXISTS " + TABLE_NAME_LEGENDS;

    public static final String TABLE_NAME_VARIABLES = "variablesTable";
    public static final String CREATE_TABLE_VARIABLES = "CREATE TABLE " + TABLE_NAME_VARIABLES + " (" +
            UID + " INEGER , " +
            NAME + " VARCHAR(255) ," +
            VALUE + " INTEGER);";
    public static final String DROP_TABLE_VARIABLES = "DROP TABLE IF EXISTS " + TABLE_NAME_VARIABLES;

    private Context context;

    public dataBaseHelper(Context context) {
        super(context, DATEBASE_NAME, null, DATEBASE_Version);
        this.context = context;
    }

    public static dataBaseHelper getInstance(Context c) {
        if (instance == null) {
            instance = new dataBaseHelper(c.getApplicationContext());
            SQLiteDatabase db = instance.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FOOTBALLERS, null);

            if (!cursor.moveToFirst()) {
                instance.onUpgrade(db, 1, 1);
            }
        }
        return instance;
    }

    private void fillTable(dataBase database, InputStream fis, String tableName, int opennedItems) {

        if (fis != null) {


            try {
                // prepare the file for reading
                InputStreamReader chapterReader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(chapterReader);

                String line;

                // read every line of the file into the line-variable, on line at the time
                do {
                    line = buffreader.readLine();

                    String number = line.subSequence(0, line.indexOf(' ')).toString();
                    Integer num = Integer.valueOf(number);
                    String name = line.subSequence(line.indexOf(' ') + 1, line.length()).toString();

                    item object;
                    if (num <= opennedItems) {
                        object = new item(num, name, 1, 0);
                    } else {
                        object = new item(num, name, 0, 0);
                    }

                    long id = database.insertItem(object, tableName);

                } while (line != null);
            } catch (Exception e) {
                // print stack trace.
            } finally {
                // close the file.
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void insertVariable(SQLiteDatabase db, String name, int id, int value) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(dataBaseHelper.UID, id);
        contentValues.put(dataBaseHelper.NAME, name);
        contentValues.put(dataBaseHelper.VALUE, value);

        db.insert(dataBaseHelper.TABLE_NAME_VARIABLES, null , contentValues);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_FOOTBALLERS);
        db.execSQL(CREATE_TABLE_CLUBS);
        db.execSQL(CREATE_TABLE_TRANSFERS);
        db.execSQL(CREATE_TABLE_LEGENDS);
        db.execSQL(CREATE_TABLE_VARIABLES);

        dataBase database = new dataBase(context);

        InputStream fis = context.getResources().openRawResource(R.raw.footballers);
        fillTable(database, fis, TABLE_NAME_FOOTBALLERS, 30);

        fis = context.getResources().openRawResource(R.raw.clubs);
        fillTable(database, fis, TABLE_NAME_CLUBS, 10);

        fis = context.getResources().openRawResource(R.raw.transfers);
        fillTable(database, fis, TABLE_NAME_TRANSFERS, 10);

        fis = context.getResources().openRawResource(R.raw.legends);
        fillTable(database, fis, TABLE_NAME_LEGENDS, 0);

        insertVariable(db, "coins", 1, 50);
        insertVariable(db, "add char", 2, 0);
        insertVariable(db, "add chars", 3, 0);
        insertVariable(db, "language", 4, 0);
        insertVariable(db, "time", 5, 15);
        insertVariable(db, "players", 6, 10);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_FOOTBALLERS);
        db.execSQL(DROP_TABLE_CLUBS);
        db.execSQL(DROP_TABLE_TRANSFERS);
        db.execSQL(DROP_TABLE_LEGENDS);
        db.execSQL(DROP_TABLE_VARIABLES);
        onCreate(db);
    }

}