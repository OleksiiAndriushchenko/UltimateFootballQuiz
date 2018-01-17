package com.warped.andaleksei.ultimatefootballquiz;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by ANDaleksei on 16.12.17.
 */

public class stringAdapter {

    // 0 - English, 1 - Russian
    public String getString(Context context, String neededString) {
        Resources res = context.getResources();

        dataBase database = new dataBase(context);

        boolean language = database.getVariableValue("language") == 1;

        if (language) {
            String russianWord = neededString + "_ru";

            return res.getString(res.getIdentifier(
                    russianWord,
                    "string",
                    context.getPackageName()));
        } else {
            String englishWord = neededString + "_en";

            return res.getString(res.getIdentifier(
                    englishWord,
                    "string",
                    context.getPackageName()));
        }
    }

}
