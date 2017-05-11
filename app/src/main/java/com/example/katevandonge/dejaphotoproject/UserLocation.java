package com.example.katevandonge.dejaphotoproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


public class UserLocation extends Service {

    TrackLocation mLocation;
    public UserLocation() {
    }

    //Start the service
    public int onStartCommand(Intent intent, int flags, int startId){

        //Toast.makeText(UserLocation.this, "Location Service Started", Toast.LENGTH_SHORT).show();
        mLocation = new TrackLocation(getApplicationContext());
        mLocation.trackLocation();
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

}

