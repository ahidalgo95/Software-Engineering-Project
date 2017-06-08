package com.example.katevandonge.dejaphotoproject;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;
import android.util.Pair;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.*;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Peter on 5/28/2017.
 */

public class User {
    public String myPassword;
    public String myEmail;

    @Exclude
    boolean isMutualFriend;

    @Exclude
    boolean loggedIn;

    @Exclude
    ArrayList<Pair<String,Integer>> myShareablePhotos;

    @Exclude
    ArrayList<String> myFriends;



    public User(){
        //Initialize array lists
        myShareablePhotos = new ArrayList<Pair<String,Integer>>();
        myFriends = new ArrayList<String>();
        loggedIn = false;

    }

    @Exclude
    public void setPassword(String password){
        myPassword = password;
    }

    @Exclude
    public void setEmail(String email){
        myEmail = email;
    }

    @Exclude
    public void setUriList( ArrayList<Pair<String,Integer>> shareablePhotos){
        myShareablePhotos = shareablePhotos;
    }

    @Exclude
    public void addPhoto(Pair<String,Integer> toAdd){
        myShareablePhotos.add(toAdd);
    }

    @Exclude
    public void addFriend(String friendEmail){
        myFriends.add(friendEmail);
    }
    @Exclude
    public String getEmail(){return myEmail;}

    @Exclude
    public boolean isLoggedIn(){
        return loggedIn;
    }

    @Exclude
    public void setLoggedIn(boolean login){
        loggedIn = login;
    }

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
        latch.await();


        Log.i("User", "Mutual friend check end");
        return isMutualFriend;
    }

    @Exclude
    public void uploadPhotos(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //https://firebase.google.com/docs/storage/android/upload-files
        //Create storage reference from our app
        StorageReference storageRef = storage.getReference();

        //Create a child reference for all of this user's photos
        StorageReference imageFolder = storageRef.child(myEmail);

        //Uploading based on URI's
        for(int i = 0; i < myShareablePhotos.size(); i++){
            Pair<String, Integer> file = myShareablePhotos.get(i);
            /*StorageReference newImage = storageRef.child(myEmail+"/"+file.getLastPathSegment());
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
            });*/
        }
    }

}
