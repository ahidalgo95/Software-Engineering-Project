package com.example.katevandonge.dejaphotoproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;


public class UserLocation extends Service {

    static String mLocationString = "";
    TrackLocation mLocation;
    DisplayLocation mDisplayLocation;
    int UPDATE_TIME_MILLISECONDS = 3000;
    boolean locationEnabled;

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

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not sure if this is right
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

                if (mLocation != null && locationEnabled) {
                    mLocation.trackLocation();
                    mLocationString = mDisplayLocation.displayLocation();
                    Log.i("display from UserLoc", mLocationString);
                }

                run();
            }
        }
    }

}

