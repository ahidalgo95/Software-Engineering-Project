package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by katevandonge on 6/8/17.
 */

public class FriendGallery {

    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> FriendQueue;
    String string;
    Context context;
    static String[] stringPractice;

    @TargetApi(24)
    public FriendGallery(Context context1){
        context = context1;
        string = "location@date@time@lat@long@locname@karma";
        photoComparator = new PhotoComparator();
        FriendQueue = new PriorityQueue<Photo>(photoComparator);
        stringPractice = new String[3];
        stringPractice[0] = "locatio1n@date1@time1@1@1@locname1@11";
        stringPractice[1] = "locatio2n@date2@time2@2@2@locname2@22";
        stringPractice[2] = "locatio3n@date3@time3@3@3@locname3@33";
        fillQueue(stringPractice);


    }

    public void fillQueue(String[] photoArray){
        String photoInfo;
        for(int i=0; i<photoArray.length; i++){
            photoInfo = photoArray[i];
            FriendQueue.add(parseToPhoto(photoInfo));
            Log.v("friend queue", ""+FriendQueue.size());
        }
    }

    public Photo parseToPhoto(String photoInfo){
        //"location@date@time@lat@long@locname@karma";
        Photo photo = new Photo(context);
        String[] arr= photoInfo.split("@");
        Log.v("friend", arr[0]);
        Log.v("friend", arr[1]);
        Log.v("friend", arr[2]);
        Log.v("friend", arr[3]);
        Log.v("friend", arr[4]);
        Log.v("friend", arr[5]);
        Log.v("friend", arr[6]);
        photo.date = arr[1];
        photo.time = arr[2];
        photo.latitude = Double.parseDouble(arr[3]);
        photo.longitude = Double.parseDouble(arr[4]);
        photo.locName = arr[5];
        photo.karma = Integer.parseInt(arr[6]);
        return photo;
    }
}
