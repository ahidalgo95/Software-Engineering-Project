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
 * Photo object class used to represent each photo we've queried from the Camera roll.
 */

public class Photo {
    Uri photouri;
    String dayOfWeek;
    String date;
    String time;
    double latitude;
    double longitude;
    Context context1;
    String locName;
    boolean karma;
    boolean release;
    boolean shown;
    int weight;
    long timeTotal;

    /*
    * Constructor for photo class.
    * */
    public Photo(Context context){
        karma = false;
        shown = false;
        release = false;
        context1 = context;
        locName = "waffles";
        timeTotal=0;
    }


    /*
    * Sets the weight of a photo from the camera roll based on day of week, time,
    * location and karma.
    * */
    public void setWeight(){
        weight=0;
        if(checkDay()==true){
            weight=weight+5;
        }
        if(checkTime()==true) {
            weight = weight + 5;
        }
        if(compareLoc()==true){
            weight=weight+5;
        }
        if(release == true) {
            weight = weight * (-1);
        }
        if(karma == true){
            weight=weight+1;
        }
    }

    /*
    * Compares location of picture to current location of user to see if user is within 500 ft of
    * where picture was taken.
    * */
    public boolean compareLoc(){
        double trackedLat= TrackLocation.mLatitude;
        double trackedLong= TrackLocation.mLongitude;
        double diffInRad= 2 *
                (double)Math.sin(Math.sqrt(Math.pow((double)Math.sin((trackedLat-latitude)/2),2)+
                (double)Math.cos(trackedLat)* (double)Math.cos(latitude)*
                        (double) Math.pow((double)Math.sin((trackedLong-longitude)/2),2)));
        double diffInKm=6371* diffInRad;
        double diffInFt= 3280 * diffInKm;
        if(diffInFt<=1000){
            return true;
        }
        return false;
    }

    /*
   * Check if picture was taken on same day of the week as current day.
   * */
    public boolean checkDay(){
        DateFormat format= new SimpleDateFormat("EEE");
        String date = format.format(Calendar.getInstance().getTime());
        if(date.equals(dayOfWeek)){
            return true;
        }
        return false;
    }


    /*
    * Check if picture was taken on same day of the week as current time.
    * */
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

    /*
    * Gets weight of current picture.
    * */
    public int getWeight(){
        return weight;
    }

    /*
    * Sets the uri of the photo.
    * */
    public void setUri(Uri uri){
        photouri = uri;
    }

    /*
    * Sets the day of week, date and time of the picture.
    * */
    public void setDate(long d){
        Date n= new Date(d);
        DateFormat format= new SimpleDateFormat("EEE MM/dd/yyyy HH:mm");
        String formatted= format.format(n);
        String[] arr= formatted.split(" ");
        dayOfWeek=arr[0];
        date= arr[1];
        time=arr[2];
    }

    /*
    * Gets total time of picture since 1970.
    * */
    public void setTimeTotal(long time){
        timeTotal=time;
    }

    /*
    * Sets latitude.
    * */
    public void setLatitude(Double d){ latitude= d; }

    /*
    * Sets longitude.
    * */
    public void setLongitude(Double d){ longitude= d; }

    /*
    * Helper to put location of photo on phone.
    * */
    public void locScreenHelper() {
        final class ThreadK implements Runnable {
            @Override
            public void run() {
                 double mylat = 37.422; //CHANGE TO PHOTOS LOCATIONS
                 double mylong = -122.084; //CHANGE TO PHOTOS LOCATIONS
                Other screenDL = new Other();
                Log.v("FOUR", "FOUR");
                locName = screenDL.displayLocation(mylat, mylong);
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
