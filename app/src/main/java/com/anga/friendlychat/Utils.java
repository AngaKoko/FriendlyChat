package com.anga.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;
import java.util.Locale;

public class Utils {

    /**
     * Hides keyboard when called
     */
    public static void hideKeyBoard(Activity activity){
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getUpdateAt(long time){
        if(DateUtils.isToday(time))
            return getTime(time);
        else return getShortDate(time);
    }

    public static   String getTime(long timestamp) {
        try{
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTimeInMillis(timestamp);
            String date = DateFormat.format("h:mm a", cal).toString();
            return date;
        }catch (Exception e) {
        }
        return "";
    }

    public static String getShortDate(long timestamp) {
        try{
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTimeInMillis(timestamp);
            String date = DateFormat.format("MMM d, ''yy", cal).toString();
            return date;
        }catch (Exception e) {
        }
        return "";
    }
}
