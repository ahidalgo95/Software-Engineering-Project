package com.example.katevandonge.dejaphotoproject;

/**
 * Created by Peter on 5/11/2017.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class UserLocation extends Service {

    static String mLocationString = "";
    TrackLocation mLocation;
    DisplayLocation mDisplayLocation;
    int UPDATE_TIME_MILLISECONDS = 990000;
    double startLat;
    double startLong;
    boolean first = true;
    Gallery locList;


    // Empty default constructor
    public UserLocation() {
        locList = MainActivity.list;
        Log.v("CONSTRUCTOR-UL", "CONSTRUCTOR-UL");
    }

    //Start the service
    public int onStartCommand(Intent intent, int flags, int startId){


        //Toast.makeText(UserLocation.this, "Location Service Started", Toast.LENGTH_SHORT).show();
        mLocation = new TrackLocation(getApplicationContext());
        mLocation.trackLocation();

        //Display the user's location
        mDisplayLocation = new DisplayLocation(mLocation);

        //Keep running
        Thread thread = new Thread(new MyThread(startId));
        thread.start();

        // Log to make sure service starts correctly
        Log.i("UserLocation", "UserLocation service started - tracking user location");

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class MyBinder extends Binder {
        public UserLocation getService(){
            return UserLocation.this;
        }
    }

    final class MyThread implements Runnable{
        int startId;
        public MyThread(int startId){
            this.startId = startId;
        }

        @Override
        public void run(){
            synchronized (this){
                try{
                    wait(UPDATE_TIME_MILLISECONDS);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }

                if (mLocation != null) {
                    // Continuously attempts to get new location and saves the string of location
                    mLocation.trackLocation();
                    mLocationString = mDisplayLocation.displayLocation();

                    // Log results for testing
                    Log.i("UserLocation", "Latitude and Longitude " + mLocation.getLatitude() + " "+ mLocation.getLongitude());
                    Log.i("UserLocation", "Location String " + mLocationString);

                    if(first){
                        startLat = mLocation.mLatitude;
                        startLong = mLocation.mLongitude;
                        first = false;
                        compareLoc();
                    }

                }

                // Keeps running to update location
                run();
            }
        }

        public void compareLoc(){
            double trackedLat= TrackLocation.mLatitude;
            double trackedLong= TrackLocation.mLongitude;
            double diffInRad= 2 *
                    (double)Math.sin(Math.sqrt(Math.pow((double)Math.sin((trackedLat-startLat)/2),2)+
                            (double)Math.cos(trackedLat)* (double)Math.cos(startLat)*
                                    (double) Math.pow((double)Math.sin((trackedLong-startLong)/2),2)));
            double diffInKm=6371* diffInRad;
            double diffInFt= 3280 * diffInKm;
            if(diffInFt>=500){
                locList.updateQueue();
                startLat = trackedLat;
                startLong = trackedLong;
            }
            return;
        }
    }

}

