package com.example.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.security.AccessController.getContext;

/**
 * Created by ANDaleksei on 26.08.17.
 */

public class dataBaseHelper extends SQLiteOpenHelper {
    private static final String DATEBASE_NAME = "myDatebase";
    private static final String TABLE_NAME = "footballerTable";
    private static final int DATEBASE_Version = 1;
    private static final String UID = "_id";
    private static final String NAME = "Name";
    private static final String ACCESS = "Access";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            UID + " INTEGER , " +
            NAME + " VARCHAR(255) ," +
            ACCESS + " INTEGER);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;

    public dataBaseHelper(Context context) {
        super(context, DATEBASE_NAME, null, DATEBASE_Version);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

        InputStream fis = context.getResources().openRawResource(R.raw.footballers);

        if (fis != null) {


            try {
                // prepare the file for reading
                InputStreamReader chapterReader = new InputStreamReader(fis);
                BufferedReader buffreader = new BufferedReader(chapterReader);

                String line;

                // read every line of the file into the line-variable, on line at the time
                do {
                    line = buffreader.readLine();

                    System.out.println(line);

                    String number = line.subSequence(0, line.indexOf(' ')).toString();
                    Integer num = Integer.valueOf(number);
                    String name = line.subSequence(line.indexOf(' ') + 1, line.length()).toString();

                    footballer fb;
                    if (num == 1) {
                        fb = new footballer(num, name, 1);
                    } else {
                        fb = new footballer(num, name, 0);
                    }

                    dataBase database = new dataBase(context);
                    database.insertData(fb);

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

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public static String getDatebaseName() {
        return DATEBASE_NAME;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static int getDATEBASE_Version() {
        return DATEBASE_Version;
    }

    public static String getUID() {
        return UID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getACCESS() {
        return ACCESS;
    }
}
