package com.example.katevandonge.dejaphotoproject;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;

import static android.R.attr.button;


public class MainActivity extends AppCompatActivity {
    Button btn;
    Button btn2;
    WallpaperManager myWall;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Start location service
        Intent intent = new Intent(this, UserLocation.class);
        startService(intent);

        btn = (Button) findViewById(R.id.setWall);
        btn2 = (Button) findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager pared = WallpaperManager.getInstance(getApplicationContext());
                Wall wally = new Wall(getApplicationContext());
               // wally.set(pared);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager pared2 = WallpaperManager.getInstance(getApplicationContext());
                Wall wally2 = new Wall(getApplicationContext());
                wally2.clear(pared2);
            }
        });


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

<<<<<<< HEAD
        Context context= getApplicationContext();
        ContentResolver conR = getApplicationContext().getContentResolver();
        Gallery list = new Gallery(context);
=======
        Context context = getApplicationContext();

        /*Gallery list = new Gallery();
>>>>>>> 6cba19c8e07e3573143eb653ab3ee6bd799e1e2c
        list.queryGallery(conR); //queries photo uris
        list.fillQueue(); //fills priority queue with picture objs
        Log.v("list size", Integer.toString(list.getSize()));


        //tests setting wallpaper with photos from our queue
        Photo popped= list.photoQueue.poll();
        Bitmap bm=popped.toBitmap(conR);
        WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
        Wall wall = new Wall();
        wall.set(wm, bm);*/
        //Context context= getApplicationContext();
        Wall wall= new Wall(context);
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

    public void testing(){
        return;
    }
}




