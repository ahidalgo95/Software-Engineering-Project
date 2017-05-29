package com.example.katevandonge.dejaphotoproject;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

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
    String myName;
    String myEmail;
    //String myFirebaseID;
    ArrayList<Uri> myUriList;
    ArrayList<String> myFriends;


    public User(){
        //Initialize array lists
        myUriList = new ArrayList<Uri>();
        myFriends = new ArrayList<String>();

    }

    @Exclude
    public void setName(String name){
        myName = name;
    }

    @Exclude
    public void setEmail(String email){
        myEmail = email;
    }

    @Exclude
    public void setUriList(ArrayList<Uri> uriList){
        myUriList = uriList;
    }

    @Exclude
    public void addUri(Uri toAdd){
        myUriList.add(toAdd);
    }

    @Exclude
    public void addFriend(String friendEmail){
        myFriends.add(friendEmail);
    }
    @Exclude
    public String getEmail(){return myEmail;}

    @Exclude
    public String getName(){return myName;}


    public String getId(){
        return myEmail.substring(0,myEmail.length()-10);
    }

    public void uploadPhotos(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //https://firebase.google.com/docs/storage/android/upload-files
        //Create storage reference from our app
        StorageReference storageRef = storage.getReference();

        //Create a child reference for all of this user's photos
        StorageReference imageFolder = storageRef.child(myEmail);

        //Uploading based on URI's
        for(int i = 0; i < myUriList.size(); i++){
            Uri file = myUriList.get(i);
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

}
