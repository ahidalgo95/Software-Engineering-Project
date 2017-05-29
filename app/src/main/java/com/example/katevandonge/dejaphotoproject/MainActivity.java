package com.example.katevandonge.dejaphotoproject;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;

import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity implements LocationListener{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    UserLocation m_service;
    TrackLocation mLocation;

    static int rate = 5000; //set at 5000ms for testing at 5 seconds

    static Intent intentAlpha;
    //static Intent intentBeta;
    static Gallery list;
    Wall wally;
    WallpaperManager myWall;
    boolean first = true;

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

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    57756687);

            return;
        }

        //context resolver and context getters
        Context context = getApplicationContext();
        ContentResolver conR = getApplicationContext().getContentResolver();

        //list of photos from the Gallery class
        list = new Gallery(context);

        int listSize= list.queryGallery(conR); //queries photo uris
        if(listSize==0){
            Toast.makeText(context, "Please put photos in gallery!", Toast.LENGTH_LONG).show();
            return;
        }

            list.fillQueue(); //fills priority queue with picture objs
            //Log.v("list size", Integer.toString(list.getSize()));
            WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
            wally = new Wall(context, list, wm);


            //timer to update queue every hour
            Timer timer = new Timer();
            TimerTask hourlytask = new TimerTask() {
                @Override
                public void run() {
                    list.updateQueue();
                    //Log.i("MainActivity", "Queue being updated");
                }
            };
            timer.schedule(hourlytask, 01, 60000 * 60);
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


       /*
       *  saving the user specified rate for rotating the photos
       * */
    public void saveRate(View view) {
        EditText timeRate = (EditText) findViewById(R.id.rate);
        //Text view for display and rate to rotate the photos at
        SharedPreferences sharedPreferences = getSharedPreferences("rate", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String rateStr = timeRate.getText().toString();
        editor.putString("rate", rateStr);
        TextView rateDisplay = (TextView) findViewById(R.id.display);
        rateDisplay.setText("Rate: " + rateStr + " minutes");
        rate = Integer.parseInt(rateStr);
        editor.apply();

        //stops the service when the app opens at a default rate
        stopService(intentAlpha);

        intentAlpha = new Intent(MainActivity.this, UpdateQueueIntentService.class);
        String holder2 = "" + rate;
        intentAlpha.putExtra("myrate", holder2);

        //starts an intent with the user specified rate
        startService(intentAlpha);
    }

    @TargetApi(25)
    public void accessCamera(View view){
         /*
        *  Permissions for reading from Camera.
        * */
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)) {
            }

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    785654);


        }
        Intent intent= new Intent(this, AccessCamera.class);
        startActivity(intent);

    }

}




