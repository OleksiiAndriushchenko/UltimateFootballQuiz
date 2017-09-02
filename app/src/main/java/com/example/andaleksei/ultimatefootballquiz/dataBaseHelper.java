package com.example.andaleksei.ultimatefootballquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    public static final String NAME = "Name";
    public static final String ACCESS = "Access";
    public static final String CREATE_TABLE_FOOTBALLERS = "CREATE TABLE " + TABLE_NAME_FOOTBALLERS + " (" +
            UID + " INTEGER , " +
            NAME + " VARCHAR(255) ," +
            ACCESS + " INTEGER);";
    public static final String DROP_TABLE_FOOTBALLERS = "DROP TABLE IF EXISTS " + TABLE_NAME_FOOTBALLERS;

    public static final String TABLE_NAME_VARIABLES = "variablesTable";
    public static final String VALUE = "value";
    public static final String CREATE_TABLE_VARIABLES = "CREATE TABLE " + TABLE_NAME_VARIABLES + " (" +
            NAME + " VARCHAR(255) ," +
            VALUE + " INTEGER);";
    public static final String DROP_TABLE_VARIABLES = "DROP TABLE IF EXISTS " + TABLE_NAME_VARIABLES;

    public static final String TABLE_NAME_CLUBS = "clubTable";
    public static final String CREATE_TABLE_CLUBS = "CREATE TABLE " + TABLE_NAME_CLUBS + " (" +
            UID + " INTEGER , " +
            NAME + " VARCHAR(255) ," +
            ACCESS + " INTEGER);";
    public static final String DROP_TABLE_CLUBS = "DROP TABLE IF EXISTS " + TABLE_NAME_CLUBS;

    private Context context;

    public dataBaseHelper(Context context) {
        super(context, DATEBASE_NAME, null, DATEBASE_Version);
        this.context = context;
    }

    public static dataBaseHelper getInstance(Context c) {
        if (instance == null) {
            instance = new dataBaseHelper(c.getApplicationContext());
        }
        return instance;
    }

    private void fillTable(dataBase database, InputStream fis, String tableName) {

        if (fis != null) {


            try {
                // prepare the file for reading
                InputStreamReader chapterReader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(chapterReader);

                String line;

                // read every line of the file into the line-variable, on line at the time
                do {
                    line = buffreader.readLine();
                    Log.v("DATABASE", line);

                    String number = line.subSequence(0, line.indexOf(' ')).toString();
                    Integer num = Integer.valueOf(number);
                    String name = line.subSequence(line.indexOf(' ') + 1, line.length()).toString();

                    item object;
                    if (num == 1) {
                        object = new item(num, name, 1);
                    } else {
                        object = new item(num, name, 0);
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


    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_FOOTBALLERS);
        db.execSQL(CREATE_TABLE_VARIABLES);
        db.execSQL(CREATE_TABLE_CLUBS);


        dataBase database = new dataBase(context);

        database.insertVariable("footballer", 1);
        database.insertVariable("playMode", 0);
        database.insertVariable("club", 1);

        InputStream fis = context.getResources().openRawResource(R.raw.footballers);
        fillTable(database, fis, TABLE_NAME_FOOTBALLERS);

        fis = context.getResources().openRawResource(R.raw.clubs);
        fillTable(database, fis, TABLE_NAME_CLUBS);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_FOOTBALLERS);
        db.execSQL(DROP_TABLE_VARIABLES);
        db.execSQL(DROP_TABLE_CLUBS);
        onCreate(db);
    }

}
