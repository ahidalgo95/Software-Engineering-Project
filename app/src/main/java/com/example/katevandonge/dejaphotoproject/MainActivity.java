package com.example.katevandonge.dejaphotoproject;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity implements LocationListener{
    UserLocation m_service;
    TrackLocation mLocation;
    static int rate = 5000;//300000;
    Intent intentAlpha;
    Intent intentBeta;
    static Gallery list;
    Wall wally;


    WallpaperManager myWall;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intentAlpha = new Intent(MainActivity.this, UpdateQueueIntentService.class);
        String holder = "" + rate;
        intentAlpha.putExtra("myrate", holder);
        startService(intentAlpha);


        /*
        *  Permissions for accessing fine location
        * */
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    57756687);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }

        /*Start location service*/
        Intent location = new Intent(this, UserLocation.class);
        startService(location);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*
        *  Permissions for reading from Camera Roll.
        * */
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    57756687);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }

        Context context = getApplicationContext();
        ContentResolver conR = getApplicationContext().getContentResolver();
        list = new Gallery(context);
        list.queryGallery(conR); //queries photo uris
        list.fillQueue(); //fills priority queue with picture objs
        Log.v("list size", Integer.toString(list.getSize()));
        WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
        wally = new Wall(context, list, wm);


        Timer timer = new Timer();
        TimerTask hourlytask = new TimerTask(){
            @Override
            public void run(){
                list.updateQueue();
                Log.v("MainActivity", "Queue being updated");
            }
        };
        timer.schedule(hourlytask,01, 60000 * 60);

        Timer shownTimer = new Timer();
        TimerTask dayTask = new TimerTask(){
            @Override
            public void run(){
                wally.resetShown();
                Log.v("MainActivity", "Wally reset timer");
            }
        };
        shownTimer.schedule(dayTask, 01, 60000 * 60);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public void testing(){
        return;
    }



    public void saveRate(View view){
        EditText timeRate = (EditText) findViewById(R.id.rate);

        SharedPreferences sharedPreferences = getSharedPreferences("rate", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String rateStr = timeRate.getText().toString();
        editor.putString("rate", rateStr );
        TextView rateDisplay = (TextView) findViewById(R.id.display);
        rateDisplay.setText("Rate: "+rateStr+" minutes");
        rate = Integer.parseInt(rateStr);

        //stopService(intentAlpha);
        intentBeta = new Intent(MainActivity.this, UpdateQueueIntentService.class);
        String holder2 = "" + rate;
        intentBeta.putExtra("myrate", holder2);
        stopService(intentAlpha);
        startService(intentBeta);
        editor.apply();


    }



}




