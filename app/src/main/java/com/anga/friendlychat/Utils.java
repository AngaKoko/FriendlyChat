package com.anga.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    public static void setProfileImage(final Context context, String imagePath, final ImageView view){
        try{

            StorageReference ref = FirebaseStorage.getInstance().getReference()
                    .child(imagePath);

            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context.getApplicationContext())
                            .load(uri)
                            .apply(RequestOptions.circleCropTransform())
                            .into(view);
                    Log.d("STORAGE", "success"+uri);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("STORAGE", "error"+e);
                }
            });
        }catch (Exception e){e.printStackTrace();}
    }
}
