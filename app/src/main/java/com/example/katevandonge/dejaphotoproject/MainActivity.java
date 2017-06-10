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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity implements LocationListener{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    UserLocation m_service;
    TrackLocation mLocation;
    static boolean sharingMode = false; //false Means sharing is off
    static boolean friendMode = false;
    static boolean cameraMode = false;
    static boolean copiedMode = false;
    User user;
    static int rate = 5000; //set at 5000ms for testing at 5 seconds
    static Intent intentAlpha;
    static Gallery list;
    static MasterGallery master;
    static CopiedGallery dpcopied;
    static DejaPhotoGallery djpGallery;
    static Wall wally;
    static User currUser;
    WallpaperManager myWall;
    static FriendGallery friendGall;
    static Context context;
    //static MasterGallery masterGallery;
    int accessCameraCounter=0;


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

        //context resolver and context getters
        context = getApplicationContext();
        ContentResolver conR = getApplicationContext().getContentResolver();


        //make all galleries here, including master
        friendGall = new FriendGallery(context);
        list = new Gallery(context);
        dpcopied = new CopiedGallery();
        djpGallery = new DejaPhotoGallery(getApplicationContext());
        master = new MasterGallery(context);


        currUser = new User();
        currUser.setEmail("katemvd@gmail.com");
        currUser.setPassword("password");
        Log.v("main", "MASTER SHOULD ONLY ONCE");


        Button launchProfileActivity = (Button) findViewById(R.id.button3);
        launchProfileActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.v("Main", "1");
                launchScrollingActivity();
                Log.v("Main", "6");
            }
        });

        Button launchFriendActivity = (Button) findViewById(R.id.FriendActivity);
        launchFriendActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                launchFriendActivity();
            }
        });
        Button launchCustomActivity = (Button) findViewById(R.id.customLocBtn);
        launchCustomActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCustomActivity();
            }
        });

        Switch changeCameraMode = (Switch) findViewById(R.id.cameraSwitch);
        changeCameraMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraChange(view);
            }
        });

        Switch changeCopiedMode = (Switch) findViewById(R.id.copiedSwitch);
        changeCopiedMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copiedChange(view);
            }
        });

        Switch changeFriendMode = (Switch) findViewById(R.id.friendSwitch);
        changeFriendMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    friendModeChange(view);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



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


        int listSize= list.queryGallery(conR); //queries photo uris






        if(listSize==0){
            Toast.makeText(context, "Please put photos in gallery!", Toast.LENGTH_LONG).show();
            return;
        }

            list.fillQueue(); //fills priority queue with picture objs
             //masterGallery = new MasterGallery();
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

    public void launchScrollingActivity(){
        Intent intent = new Intent(this, ScrollingActivity.class);
        intent.putExtra("kate", "myString");
        startActivityForResult(intent, 1);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("Main", "4");
        Log.v("Main", "Main on Act res");
        if (requestCode == 1) {
            myUri = Uri.parse(data.getStringExtra("imageURI"));
            helper(myUri);
        }
    }
    public void helper(Uri uri){
        Log.v("Main", "5");
        //dpcopied.addPhoto(uri);
        //master.addCopied();
    }*/

    public void launchFriendActivity(){
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
        //Log.i("curr user is", currUser.getEmail());
    }

    public void launchCustomActivity(){
        Intent intent = new Intent(this, CustomLocation.class);
        startActivity(intent);
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

    /**
     * Changes the mode the user is in for sharing. It determines if their photos are being shared
     * with friends or not.
     * @param view
     */
    public void sharingChange(View view){ //tested and works!
        sharingMode = !sharingMode;
        if(sharingMode == true) {
            int size = MasterGallery.myArr.size();
            Photo [] temp = MasterGallery.myArr.toArray(new Photo[size]);
            currUser.addPhotos(temp, getApplicationContext());
        }
        else{
            Photo [] empty = new Photo [0];
            currUser.addPhotos(empty, getApplicationContext());
        }
    }

    /**
     * Changes the mode the user is in for photo views. It determines if they are seeing their own
     * photos or their friends' as the background
     * @param view
     */
    public void friendModeChange(View view) throws InterruptedException {
        friendMode = !friendMode;
        ArrayList<String> friendArr = currUser.getFirebaseFriends();
        User temp = new User();
        for(int i = 0; i<friendArr.size(); i++){
            temp.setEmail(friendArr.get(i));
            if(currUser.checkMutualFriends(temp)){
                temp.getFirebaseShareablePhoto();
            }
        }
        master.updateMasterQ(copiedMode, cameraMode, friendMode);
    }

    public void cameraChange(View view){
        Log.i("CAMERA CHANGED","");
        cameraMode = !cameraMode;
        Log.i("cam mode",""+cameraMode);
        master.updateMasterQ(copiedMode, cameraMode, friendMode);
    }

    public void copiedChange(View view){
        Log.i("COPY MODE CHANGED","");
        copiedMode = !copiedMode;
        Log.i("copy mode",""+copiedMode);
        master.updateMasterQ(copiedMode, cameraMode, friendMode);
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
        Button btn = (Button)findViewById(R.id.camButton);
        btn.setOnClickListener(camListener);

        if(accessCameraCounter==0) {
            accessCameraCounter++;
            Intent intent = new Intent(getApplicationContext(), AccessCamera.class);
            startActivity(intent);
        }


    }

    private View.OnClickListener camListener = new View.OnClickListener()
    {

        public void onClick(View v)
        {
            Intent intent= new Intent(getApplicationContext(), AccessCamera.class);
            startActivity(intent);

        }

    };


}




