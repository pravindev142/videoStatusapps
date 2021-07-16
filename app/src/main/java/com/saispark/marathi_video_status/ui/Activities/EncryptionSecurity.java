package com.saispark.marathi_video_status.ui.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.crypto.NoSuchPaddingException;

public class EncryptionSecurity {

    private Context context;

    public EncryptionSecurity(Context context) {
        this.context = context;
    }
    public  static int printDifferencedate(Date currentdate, Date addeddate) {
        //milliseconds
        long different = addeddate.getTime() - currentdate.getTime();

        System.out.println("startDate : " + currentdate);
        System.out.println("endDate : "+ addeddate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;


return (int) elapsedMinutes;
    }
    public static Date currenttime(){
        Date currentdate = null;
        try {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Calendar c = Calendar.getInstance();
        String current_time = simpleDateFormat.format(c.getTime() );

            currentdate = simpleDateFormat.parse(current_time);

return  currentdate;

        } catch (ParseException e) {
            e.printStackTrace();

        }
return  currentdate;

    }
    public static Date currenttimaddfiveminute(int time){
        Date addedtime = null;

        try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE,time );
        String addfiveminute_time = simpleDateFormat.format(c.getTime() );
       addedtime = simpleDateFormat.parse(addfiveminute_time);

return  addedtime;


        } catch (ParseException e) {
            e.printStackTrace();

        }

return  addedtime;
    }


    public static boolean isOnline(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;

    }

}








