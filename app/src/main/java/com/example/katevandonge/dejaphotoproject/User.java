package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Pair;

import com.google.firebase.database.Exclude;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Peter on 5/28/2017.
 */

public class User {
    String myName;
    String myEmail;
    //String myFirebaseID;
    //Updated this array list to only contain compressed strings of bitmaps for storage later
    ArrayList<Pair<String, Integer>> myShareablePhotos;
    ArrayList<String> myFriends;


    public User() {
        //Initialize array lists
        myShareablePhotos = new ArrayList<Pair<String, Integer>>();
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
    /*
    @Exclude
    public void setUriList(ArrayList<Uri> uriList){
        myUriList = uriList;
    }


    @Exclude
    public void addUri(Uri toAdd){
        myUriList.add(toAdd);
    }
    */

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
    public String getEmail(){return myEmail;}

    @Exclude
    public String getName(){return myName;}


    public String getId(){
        return myEmail.substring(0,myEmail.length()-10);
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
