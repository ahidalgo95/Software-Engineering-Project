package com.example.katevandonge.dejaphotoproject;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by luujfer on 5/9/17.
 */

public class Photo {
    Uri photouri;
    String dayOfWeek;
    String date;
    String time;
    Double latitude;
    Double longitude;
    Context context1;
    String locName;
    boolean karma;
    boolean release;
    boolean shown;
    int weight;
    long recentTime = 0;

    public Photo(Context context){
        karma = false;
        shown = false;
        release = false;
        context1 = context;
        locName = "hello!";

    }

    /*
    * METHOD NEEDS TO BE MADE!!!
    * */
    public void setWeight(){
        weight=0;
        if(checkDay()==true){
            weight=weight+5;
        }
        if(checkTime()==true){
            weight=weight+5;
        }
        if(release == true){
                weight = weight*(-1);
        }
        if(karma == true){
            weight=weight+1;
        }

    }

    public int getWeight(){
        return weight;
    }

    public boolean checkDay(){
        DateFormat format= new SimpleDateFormat("EEE");
        String date = format.format(Calendar.getInstance().getTime());

        if(date.equals(dayOfWeek)){
            return true;
        }
        return false;
    }

    public boolean checkTime(){
        DateFormat format= new SimpleDateFormat("HH:mm");
        String currDate = format.format(Calendar.getInstance().getTime());
        String[] arr= currDate.split(":");
        int currMin= (Integer.parseInt(arr[0])*60)+ (Integer.parseInt(arr[1]));

        String[] saved= time.split(":");
        int savedMin= (Integer.parseInt(saved[0])*60)+ (Integer.parseInt(saved[1]));
        if(savedMin<=currMin && currMin <= savedMin+120 || currMin<=savedMin && currMin >= savedMin-120){
            return true;
        }
        return false;
    }

    public void setUri(Uri uri){
        photouri = uri;
    }

    public void setDate(long d){
        Date n= new Date(d);
        DateFormat format= new SimpleDateFormat("EEE MM/dd/yyyy HH:mm");
        String formatted= format.format(n);
        String[] arr= formatted.split(" ");
        dayOfWeek=arr[0];
        date= arr[1];
        time=arr[2];
    }

    public void setRecentTime(long a){
        recentTime = a;
    }

    public void setLatitude(Double d){ latitude= d; }

    public void setLongitude(Double d){ longitude= d; }

    public void locScreenHelper() {
        final class ThreadK implements Runnable {
            @Override
            public void run() {
                double mylat = 37.422; //CHANGE TO PHOTOS LOCATIONS
                double mylong = -122.084; //CHANGE TO PHOTOS LOCATIONS
                Other screenDL = new Other();
                Log.v("FOUR", "FOUR");
                String locName = screenDL.displayLocation(mylat, mylong);
                Log.v(locName, locName);
            }
        }
        Thread how = new Thread(new ThreadK());
        how.start();
    }


    /*
    * Converts uri to bitmap and returns that bitmap.
    * */
    public Bitmap toBitmap(ContentResolver cr){
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(cr,photouri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
}
