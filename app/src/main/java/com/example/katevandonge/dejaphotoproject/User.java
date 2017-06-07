package com.example.katevandonge.dejaphotoproject;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Exclude;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.*;


import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Peter on 5/28/2017.
 */

public class User {
    String myPassword;
    String myEmail;
    //String myFirebaseID;

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
