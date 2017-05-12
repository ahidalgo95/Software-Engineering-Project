package com.example.katevandonge.dejaphotoproject;

import android.Manifest;
import android.content.Intent;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

import static android.R.attr.button;



public class MainActivity extends AppCompatActivity implements LocationListener{
    UserLocation m_service;
    TrackLocation mLocation;
    int rate;


    WallpaperManager myWall;
    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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

        Context context= getApplicationContext();
        ContentResolver conR = getApplicationContext().getContentResolver();
        Gallery list = new Gallery(context);

        //Gallery list = new Gallery();
        list.queryGallery(conR); //queries photo uris
        list.fillQueue(); //fills priority queue with picture objs
        Log.v("list size", Integer.toString(list.getSize()));


        //tests setting wallpaper with photos from our queue
        //Photo popped= list.photoQueue.poll();
        //Bitmap bm=popped.toBitmap(conR);
        WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
        Wall wall = new Wall(context, list, wm);
        //wall.set(bm);
        //Context context= getApplicationContext();
        //Wall wall= new Wall(context);

    }

    /*//Define service connection
    private ServiceConnection m_serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            m_service = ((UserLocation.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName className){

            m_service = null;
        }

    };*/

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

        editor.apply();
    }

}




