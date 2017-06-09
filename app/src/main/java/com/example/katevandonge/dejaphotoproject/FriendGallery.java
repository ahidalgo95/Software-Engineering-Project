package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;

import android.graphics.Bitmap;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * FriendGallery class to hold friend's photos.
 */

/**
 * Parses String array input to create photo objects for friends photos
 */
public class FriendGallery {

    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> friendQueue;
    Context context;

    /*
    * Constructor for friendGallery class.
    * */
    @TargetApi(24)
    public FriendGallery(Context context1){
        context = context1;
        //how metadata is stored "karma@locName@latitude@longitude@date@dayOfWeek@time";
        photoComparator = new PhotoComparator();
        friendQueue = new PriorityQueue<Photo>(photoComparator);
    }


    /*
    * Fills the friendQueue with Photo objects given input of an arrayList of pairs.
    * return: void
    * */
    public void fillQueue(ArrayList<Pair<Bitmap, String>> photoArray){
        String photoInfo;
        Bitmap bitmap;
        for(int i=0; i<photoArray.size(); i++){
            bitmap= photoArray.get(i).first;
            photoInfo = photoArray.get(i).second;
            friendQueue.add(parseToPhoto(bitmap, photoInfo));
            Log.i("fillQueue", "added to friendQueue"+ i);
        }
    }


    /*
    * Fills a photo object given a bitmap and a string to parse.
    * return: photo object
    * */
    public Photo parseToPhoto(Bitmap bitmap, String photoInfo){
        //"karma@locName@latitude@longitude@date@dayOfWeek@time
        Photo photo = new Photo(context);
        String[] arr= photoInfo.split("@");
        photo.ogAlbum=3;
        photo.storedBitmap= bitmap;
        photo.date= arr[4];
        photo.dayOfWeek= arr[5];
        photo.time = arr[6];
        photo.latitude = Double.parseDouble(arr[2]);
        photo.longitude = Double.parseDouble(arr[3]);
        photo.locName = arr[1];
        photo.karma = Integer.parseInt(arr[0]);
        return photo;
    }
}
