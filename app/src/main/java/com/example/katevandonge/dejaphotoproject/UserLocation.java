package com.example.katevandonge.dejaphotoproject;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

public class UserLocation extends Service {

    Service m_service;

    public UserLocation() {
    }

    //Start the service
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //Define service connection
    private ServiceConnection m_serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            m_service = ((UserLocation.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName className){
            m_service = null;
        }

    };


    public class MyBinder extends Binder {
        public UserLocation getService(){
            return UserLocation.this;
        }
    }


}

