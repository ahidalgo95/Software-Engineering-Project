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
import java.util.TimeZone;

/**
 * Photo object class used to represent each photo we've queried from the Camera roll.
 */

public class Photo {
    Uri photouri;           // stores photo URI
    String dayOfWeek;       // stores day photo was taken
    String date;            // stores date photo was taken
    String time;            // stores time photo was taken
    double latitude;        // stores location latitude
    double longitude;       // stores location longitude
    Context context1;       // stores application context
    String locName;         // stores location name
    boolean karma;          // has been karma'd boolean
    boolean release;        // released boolean
    boolean shown;          // recently shown boolean
    int weight;             // stores photo's weight
    long timeTotal;         // holds time to know if recently taken

    /*
    * Constructor for photo class. Initialize some variables
    * */
    public Photo(Context context){
        karma = false;
        shown = false;
        release = false;
        context1 = context;
        locName = "Location";
        timeTotal=0;
    }


    /*
    * Sets the weight of a photo from the camera roll based on day of week, time,
    * location and karma.
    * */
    public void setWeight(){
        weight=0;
        // if current days match, add weight
        if(checkDay()==true){
            weight=weight+5;
        }
        // if current times match, add weight
        if(checkTime()==true) {
            weight = weight + 5;
        }
        // if within location, add weight
        if(compareLoc()==true){
            weight=weight+5;
        }
        // if released, negative weight
        if(release == true) {
            weight = weight * (-1);
        }
        // if karma'd, add weight
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
        Log.i("Photo:" ,"comparing locations");
        if(diffInFt<=1000){
            Log.i("Photo:" ,"1000 ft or less away");
            return true;
        }
        return false;
    }

    /*
   * Check if picture was taken on same day of the week as current day.
   * */
    public boolean checkDay(){
        // get the current day of the week
        DateFormat format= new SimpleDateFormat("EEE");
        String date = format.format(Calendar.getInstance().getTime());
        // compare the current day of the week with photo's day of week
        if(date.equals(dayOfWeek)){
            Log.i("Photo","Comparing day: TRUE");
            Log.i("Photo:", dayOfWeek + "curr: "+date);
            // return true if they are equal
            return true;
        }
        Log.i("Photo","comparing days : FALSE");
        Log.i("Photo", "photoDay:"+ dayOfWeek +"curr: "+date);
        //return false if they are not equal
        return false;
    }


    /*
    * Check if picture was taken on same day of the week as current time.
    * */
    public boolean checkTime(){
        // get the current time
        DateFormat format= new SimpleDateFormat("HH:mm");
        String currDate = format.format(Calendar.getInstance().getTime());
        //split the time from hour and minutes
        String[] arr= currDate.split(":");
        //convert the time to minutes for current time
        int currMin= (Integer.parseInt(arr[0])*60)+ (Integer.parseInt(arr[1]));
        String[] saved= time.split(":");
        // convert time to minutes for photo time
        int savedMin= (Integer.parseInt(saved[0])*60)+ (Integer.parseInt(saved[1]));
        // compare the two times to see they are within 2 hours of each other
        if(savedMin<=currMin && currMin <= savedMin+120 || currMin<=savedMin && currMin >= savedMin-120){
            Log.i("Photo:","comparing time in mins: TRUE");
            Log.i("Photo: ", + savedMin +"curr mins: "+currMin);
            // if within two hours, return true
            return true;
        }
        // if not within two hours, return false
        Log.i("Photo:", "Comparing times in min : FALSE");
        Log.i("Photo: ",  savedMin+ "curr mins: "+currMin);
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
        // populate the fields for the date the photo was taken
        Date n= new Date(d);
        DateFormat format= new SimpleDateFormat("EEE MM/dd/yyyy HH:mm");
        String formatted= format.format(n);

        // split the date format by day of week, date, time

        //Log.i("Photo Date", formatted);

        String[] arr= formatted.split(" ");
        dayOfWeek=arr[0];
        date= arr[1];
        time=arr[2];
    }

    /*
    * Gets total time of picture since 1970.
    * */
    public void setTimeTotal(long time){timeTotal=time;}

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
                 double mylat = latitude; //CHANGE TO PHOTOS LOCATIONS
                 double mylong = longitude; //CHANGE TO PHOTOS LOCATIONS
                WriteLocation screenDL = new WriteLocation();
                locName = screenDL.displayLocation(mylat, mylong);
                Log.i("Photo:" ,"photo location"+ mylat + ", " + mylong);
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
        Log.i("Photo:" ,"getting bitmap");
        try {
            bm = MediaStore.Images.Media.getBitmap(cr,photouri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
}
