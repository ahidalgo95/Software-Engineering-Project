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

public class User extends AppCompatActivity {

    public String myPassword;
    public String myEmail;

    @Exclude
    boolean readyToReturn;

    @Exclude
    boolean isMutualFriend;

    @Exclude
    boolean loggedIn;


    static ArrayList<Pair<String,Long>> myShareablePhotos;

    static FriendGallery friendGall;

    @Exclude
    ArrayList<String> myFriends;

    public User() {
        //Initialize array lists
        myShareablePhotos = new ArrayList<Pair<String, Long>>();
        myFriends = new ArrayList<String>();
        loggedIn = false;
        friendGall = MainActivity.friendGall;

    }


    @Exclude
    public void setPassword(String password){
        myPassword = password;
    }

    @Exclude
    public void setEmail(String email) {
        myEmail = email;
    }


    /**
     * Created by David Teng
     * This method allows insertion of bitmaps to a specific user's shareable library of photos
     */

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Exclude
    public void addPhotos(Photo photo, Context context, long temp)
    {
        String bitmap = encodeBitmap(photo.toBitmap(context.getContentResolver()));
        Long karma_value = temp;
        Pair<String, Long> insVal = new Pair(bitmap, karma_value);
        myShareablePhotos.add(insVal);


    }
    public ArrayList<Pair<String, Long>> getAL(){
        return myShareablePhotos;
    }

    @Exclude
    public void setUriList( ArrayList<Pair<String,Long>> shareablePhotos) {
        myShareablePhotos = shareablePhotos;
    }
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String encodeBitmap(Bitmap bmp)
    {
        //We compress the bitmap down to a string in order to store it efficiently on firebase
        if(bmp == null)
            return "";

        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
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
    public void addPhoto(Pair<String,Long> toAdd) {
        myShareablePhotos.add(toAdd);
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public ArrayList<Pair<Bitmap, Integer>> getPhotos() {

        ArrayList<Pair<Bitmap, Integer>> bmap = new ArrayList<Pair<Bitmap, Integer>>();

        for(int i = 0; i < myShareablePhotos.size(); i++)
        {
            Bitmap temp = decodeBitMap(myShareablePhotos.get(i).first);
            Long temp2 = myShareablePhotos.get(i).second;
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

    @Exclude
    public void getFirebaseShareablePhoto() throws InterruptedException {
        // Accesses database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference shareablePhotosRef = myFirebaseRef.child("users/" + this.getId() + "/myShareablePhotos");

        //myShareablePhotos = new ArrayList<>();
        // Loop through users in order with the forEach() method. The callback
        // provided to forEach() will be called synchronously with a DataSnapshot
        // for each child:
        //final CountDownLatch latch = new CountDownLatch(1);
        shareablePhotosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String,Long> tempHash = (HashMap<String,Long>)snapshot.getValue();

                    String tempURI = tempHash.get("first") + "";
                    long tempKarma = tempHash.get("second");
                    Log.i("ShareableInListener", tempURI + " " + tempKarma);

                    Pair<String,Long> tempPair = new Pair<String,Long>(tempURI, tempKarma);
                    myShareablePhotos.add(tempPair);
                    Log.i("ShareableInListener", "size: " + myShareablePhotos.size());
                }
                //latch.countDown();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // do nothing
                //latch.countDown();
            }
        });

        //latch.await();
        // Force an update
        //shareablePhotosRef.child("temp").setValue("temp");
        //shareablePhotosRef.child("temp").removeValue();



        //Log.i("ShareableBeforeRetun", "shareablephotos size: " + myShareablePhotos.size());
        //for(int i = 0; i < myShareablePhotos.size(); i++){
        //    Log.i("ShareableStored",myShareablePhotos.get(i).first + " " + myShareablePhotos.get(i).second);
        //}

        //return myShareablePhotos;
    }


    @Exclude
    public void addFriend(String friendEmail){
        myFriends.add(friendEmail);
    }

    @Exclude
    public String getEmail(){return myEmail;}

    @Exclude
    public String getName(){return myPassword;}

    @Exclude
    public ArrayList<String> getFriends(){
        return myFriends;
    }

    @Exclude
    public String getId(){
        //return myEmail.substring(0,myEmail.length()-10);
        return myEmail.replaceAll("\\.", "_");
    }

    @Exclude
    public int getFriendIndex(){
        return myFriends.size();
    }

    @Exclude
    public boolean checkMutualFriends(User friend) throws InterruptedException {
        // Accesses database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference userRef = myFirebaseRef.child("users/" + this.getId() + "/myFriends");


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

    public boolean isLoggedIn() {
        return loggedIn;
    }

}
