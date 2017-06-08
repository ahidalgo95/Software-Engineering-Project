package com.example.katevandonge.dejaphotoproject;

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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Peter on 5/28/2017.
 */

public class User {
    String myPassword;
    String myEmail;


    @Exclude
    boolean isMutualFriend;

    @Exclude
    boolean loggedIn;

    @Exclude
    ArrayList<Pair<String,Integer>> myShareablePhotos;

    @Exclude
    ArrayList<String> myFriends;

    public User() {
        //Initialize array lists
        myShareablePhotos = new ArrayList<Pair<String, Integer>>();
        myFriends = new ArrayList<String>();
        loggedIn = false;

    }


    @Exclude
    public void setPassword(String password){
        myPassword = password;
    }

    @Exclude
    public void setEmail(String email) {
        myEmail = email;
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
        latch.await();


        Log.i("User", "Mutual friend check end");
        return isMutualFriend;
    }


    /**
     * Created by David Teng
     * This method allows insertion of bitmaps to a specific user's shareable library of photos
     */

    @Exclude
    public void addPhotos(Photo photo, Context context)
    {
        String bitmap = encodeBitmap(photo.toBitmap(context.getContentResolver()));
        Integer karma_value = 0;
        Pair<String, Integer> insVal = new Pair(bitmap, karma_value);
        myShareablePhotos.add(insVal);


    }

    @Exclude
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
    public ArrayList<Pair<Bitmap, Integer>> getPhotos() {

        ArrayList<Pair<Bitmap, Integer>> bmap = new ArrayList<Pair<Bitmap, Integer>>();

        for(int i = 0; i < myShareablePhotos.size(); i++)
        {
            Bitmap temp = decodeBitMap(myShareablePhotos.get(i).first);
            Integer temp2 = myShareablePhotos.get(i).second;
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
    public void addFriend(String friendEmail){
        myFriends.add(friendEmail);
    }

    @Exclude
    ArrayList<String> getFriends(){ return myFriends; }

    @Exclude
    public String getEmail(){return myEmail;}

    @Exclude
    public String getName(){return myPassword;}


    public String getId(){
        return myEmail.substring(0,myEmail.length()-10);
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
/*
    public void uploadPhotos(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //https://firebase.google.com/docs/storage/android/upload-files
        //Create storage reference from our app
        StorageReference storageRef = storage.getReference();

        //Create a child reference for all of this user's photos
        StorageReference imageFolder = storageRef.child(myEmail);

        //Uploading based on URI's
        for(int i = 0; i < myShareablePhotos.size(); i++){
            Uri file = myShareablePhotos.get(i);
            StorageReference newImage = storageRef.child(myEmail+"/"+file.getLastPathSegment());
            UploadTask uploadTask = newImage.putFile(file);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.i("UserUpload", "Upload photo failed");
            }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    // Added suppress warnings
                    @SuppressWarnings("VisibleForTests")Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.i("UserUpload", "Upload photo success");
            }
            });
        }
    }
    */

}
