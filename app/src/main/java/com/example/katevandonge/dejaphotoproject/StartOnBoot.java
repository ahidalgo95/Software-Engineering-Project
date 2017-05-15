package com.example.katevandonge.dejaphotoproject;


/**
 * Created by Peter on 5/9/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * StartOnBoot is responsible for launching the app on phone boot up
 */

public class StartOnBoot extends BroadcastReceiver {

    // This launches the application on phone bootup
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            // Create intent to launch main activity
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

            Log.i("StartOnBoot" , "Application Launched on phone bootup");
        }
    }

}
