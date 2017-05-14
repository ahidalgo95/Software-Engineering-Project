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

    // Emtpy default constructor
    public UserLocation() {
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

                    Log.i("UserLocation", "Latitude and Longitude " + mLocation.getLatitude() + " "+ mLocation.getLongitude());
                    Log.i("UserLocation", "Location String" + mLocationString);
                }

                // Keeps running to update location
                run();
            }
        }
    }

}

