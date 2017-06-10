package com.example.katevandonge.dejaphotoproject;


import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Peter on 5/28/2017.
 */

public class User{

    // Firebase stores this info
    public String myPassword;   // this user's password
    public String myEmail;      // this user's email
    static ArrayList<Pair<String,String>> myShareablePhotos;  // this user's shareable photos

    @Exclude
    boolean isMutualFriend;     // used to check if two users are mutual friends

    @Exclude
    boolean loggedIn;           // used to check if user logged in with correct password

    @Exclude
    static FriendGallery friendGall; // stores friend's gallery

    @Exclude
    static ArrayList<Pair<Bitmap, String>> myBitmaps;   // bitmaps of this user's photos

    @Exclude
    ArrayList<String> myFriends;        // stores this user's friends

    /*
     *  Default constructor
     */
    public User() {
        //Initialize field variables
        myShareablePhotos = new ArrayList<>();
        myBitmaps = new ArrayList<>();
        myFriends = new ArrayList<>();
        loggedIn = false;
        friendGall = MainActivity.friendGall;

    }

    /*
     * Set user password for testability
     */
    @Exclude
    public void setPassword(String password){
        myPassword = password;
    }

    /*
     * Set user email for testability
     */
    @Exclude
    public void setEmail(String email) {
        myEmail = email;
    }

    /*
     * Set user sharable photos for testability
     */
    @Exclude
    public void setShareablePhotos( ArrayList<Pair<String,String>> shareablePhotos) {
        myShareablePhotos = shareablePhotos;
    }


    /**
     * Created by David Teng
     * This method allows insertion of bitmaps to a specific user's shareable library of photos
     */

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Exclude
    public void addPhotos(Photo[] photo, Context context)
    {

        // Accesses database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference userRef = myFirebaseRef.child("users/" + this.getId() + "/myShareablePhotos");


        for(int i = 0; i < photo.length; i++) {
            String bitmap = encodeBitmap(photo[i].toBitmap(context.getContentResolver()));

            //KARMA , LOCATION NAME, LATITUDE, LONGITUDE, DATE, DAYOFWEEK, TIME
            String metadata= photo[i].karma + "@" + photo[i].locName + "@"+ photo[i].latitude + "@" + photo[i].longitude+ "@" +
                    photo[i].date + "@" + photo[i].dayOfWeek + "@" + photo[i].time;

            Pair<String, String> insVal = new Pair(bitmap, metadata);
            myShareablePhotos.add(insVal);
        }
        userRef.setValue(myShareablePhotos);
    }



    /*
     * Encodes the bitmap to store on firebase
     */
    @Exclude
    public String encodeBitmap(Bitmap bmp)
    {
        //We compress the bitmap down to a string in order to store it efficiently on firebase
        if(bmp == null)
            return "";

        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bYtE);
        bmp.recycle();
        byte[] byteArray = bYtE.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return imageFile;
    }

    /**
     * Created by David Teng
     * This method allows retrieval of a user's shareable library of photos
     */
    @Exclude
    public void addPhoto(Pair<String,String> toAdd) {
        myShareablePhotos.add(toAdd);
    }

    /*
     * Converts shareable photos into bitmaps
     */
    @Exclude
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public ArrayList<Pair<Bitmap, Integer>> getPhotos() {

        ArrayList<Pair<Bitmap, Integer>> bmap = new ArrayList<Pair<Bitmap, Integer>>();

        for(int i = 0; i < myShareablePhotos.size(); i++)
        {
            Bitmap temp = decodeBitMap(myShareablePhotos.get(i).first);
            String temp2 = myShareablePhotos.get(i).second;
            Pair<Bitmap, Integer> retVal = new Pair(temp, temp2);
            if(temp != null)
                bmap.add(retVal);
        }

        return bmap;
    }

    /**
     *  Created by David Teng
     *  This method is decodes user's photos stored as compressed strings
     */

    @Exclude
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public Bitmap decodeBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    /*
     *  Gets this user's friends on firebase
     */
    @Exclude
    public ArrayList<String> getFirebaseFriends(){
        // Initialize myBitmaps
        myFriends = new ArrayList<>();

        // Accesses database and goes to user's friends database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference shareablePhotosRef = myFirebaseRef.child("users/" + this.getId() + "/myFriends");

        // Loop through users in order with the forEach() method. The callback
        // provided to forEach() will be called synchronously with a DataSnapshot
        // for each child:
        shareablePhotosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Gets formatted email address
                    String tempFriend = (String) snapshot.getValue();

                    String tempFormattedFriend = tempFriend.replaceAll("\\.", "_");

                    Log.i("ShareableInListener", tempFormattedFriend);

                    // Add to array list
                    myFriends.add(tempFormattedFriend);
                    Log.i("ShareableInListener", "FriendsSize: " +  myFriends.size());
                }
                Log.i("ShareableInListener", "FriendsFinalSize: " + myFriends.size());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // do nothing
            }
        });
        return myFriends;
    }

    /*
     *  Get this user's shareable phots
     */
    @Exclude
    public void getFirebaseShareablePhoto() throws InterruptedException {
        // Initialize myBitmaps
        myBitmaps = new ArrayList<>();

        // Accesses database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference shareablePhotosRef = myFirebaseRef.child("users/" + this.getId() + "/myShareablePhotos");

        // Loop through users in order with the forEach() method. The callback
        // provided to forEach() will be called synchronously with a DataSnapshot
        // for each child:
        shareablePhotosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Gets this child's value
                    HashMap<String,Long> tempHash = (HashMap<String,Long>)snapshot.getValue();

                    // Gets the two pieces from Firebase
                    String tempStringBitmap = tempHash.get("first") + "";
                    String tempMetadata = tempHash.get("second") + "";
                    Log.i("ShareableInListener", tempStringBitmap + " " + tempMetadata);

                    // Decodes the bitmap we got
                    Bitmap tempBitmap = decodeBitMap(tempStringBitmap);

                    // Create pair to add to bitmaps
                    Pair<Bitmap,String> tempPair = new Pair<Bitmap, String>(tempBitmap, tempMetadata);
                    myBitmaps.add(tempPair);

                    // Test and ensure things are being added
                    Log.i("ShareableInListener", "shareablesize: " + myBitmaps.size());
                }

                // Test final size
                Log.i("ShareableInListener", "BitmapSize " + myBitmaps.size());

                // Add the bitmaps to friend's gallery for usability
                friendGall.fillQueue(myBitmaps);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // do nothing
            }
        });

    }


    /*
     * Add friend for testability
     */
    @Exclude
    public void addFriend(String friendEmail){
        myFriends.add(friendEmail);
    }

    /*
     *  Get user's email for testability
     */
    @Exclude
    public String getEmail(){
        return myEmail;
    }

    /*
     * Get this user's password for testability
     */
    @Exclude
    public String getPassword(){
        return myPassword;
    }


    /*
     * Access this user's friends for testability
     */
    @Exclude
    public ArrayList<String> getFriends(){
        return myFriends;
    }

    @Exclude
    public String getId(){
        return myEmail.replaceAll("\\.", "_");
    }

    /*
     * Get the current amount of friends this user has
     */
    @Exclude
    public int getFriendIndex(){
        return myFriends.size();
    }

    /*
     * Checks if this user and the "friend" passed in are mutual friends
     */
    @Exclude
    public boolean checkMutualFriends(User friend) throws InterruptedException {
        // Accesses database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference userRef = myFirebaseRef.child("users/" + this.getId() + "/myFriends");


        // Countdown latch ensures data is checked before continuing
        final CountDownLatch latch = new CountDownLatch(1);
        // Check friends
        userRef.child(friend.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Check if friend has user as a friend as well in next block
                    isMutualFriend = true;
                    Log.i("User", "Mutual friend check 1 passed");
                }
                else {
                    //do nothing
                    isMutualFriend = false;
                    Log.i("User", "Mutual friend check 1 failed");
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // do nothing
                Log.i("User", "Mutual check cancelled");
                latch.countDown();
            }
        });
        latch.await();


        userRef = myFirebaseRef.child("users/" + friend.getId() + "/myFriends");

        final CountDownLatch latch2 = new CountDownLatch(1);
        // Check if friend has user as friend
        userRef.child(this.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Check if friend has user as a friend as well
                    isMutualFriend = true;
                    Log.i("User", "Mutual friend check 2 passed");
                }
                else {
                    //do nothing
                    isMutualFriend = false;
                    Log.i("User", "Mutual friend check 2 failed");
                }
                latch2.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // do nothing
                Log.i("User", "Mutual check cancelled");
                latch2.countDown();
            }
        });
        latch2.await();


        Log.i("User", "Mutual friend check end");
        return isMutualFriend;
    }

    @Exclude
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Exclude
    public boolean isLoggedIn() {
        return loggedIn;
    }

}
