package com.warped.andaleksei.ultimatefootballquiz.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by VitDmit on 18.01.2018.
 */

public class PreferenceUtils {
    public static final String KEY_RATED_AT_PLAY_STORE = "rated-at-play-store";
    public static final String KEY_ANSWER_KEY_CLICKED_COUNT = "answer-key-clicked-count";

    public static void setRatedAtPlayStore(Context context,boolean isRated){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_RATED_AT_PLAY_STORE,isRated);
        editor.apply();
    }
    public static boolean isRatedAtPlayStore(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(KEY_RATED_AT_PLAY_STORE,false);
    }

    public static int getClickedCount(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(KEY_ANSWER_KEY_CLICKED_COUNT,0);
    }
    public static void setClickedCount(Context context,int newValue){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(KEY_ANSWER_KEY_CLICKED_COUNT,newValue);
        edit.apply();
    }
    public static void modyfyClickedCountBy(Context context, int valueToAdd){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int prevValue = sharedPreferences.getInt(KEY_ANSWER_KEY_CLICKED_COUNT,0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(KEY_ANSWER_KEY_CLICKED_COUNT,prevValue+valueToAdd);
        edit.apply();
    }
    public static void changeClickedCountRightAnswer(Context context) {
        modyfyClickedCountBy(context,5);
    }

    public static void changeClickedCountWrongAnswer(Context context) {
        modyfyClickedCountBy(context, 1);
    }
}
