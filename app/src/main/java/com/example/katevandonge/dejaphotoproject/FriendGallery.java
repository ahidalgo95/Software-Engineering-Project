package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by katevandonge on 6/8/17.
 */

public class FriendGallery {

    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> friendQueue;
    String metadata;
    Context context;

    @TargetApi(24)
    public FriendGallery(Context context1){
        context = context1;
        metadata = "karma@locName@latitude@longitude@date@dayOfWeek@time";
        photoComparator = new PhotoComparator();
        friendQueue = new PriorityQueue<Photo>(photoComparator);


    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public void fillQueue(ArrayList<Pair<String,String>> photoArray){
        String bitmap;
        String photoInfo;
        for(int i=0; i<photoArray.size(); i++){
            bitmap= photoArray.get(i).first;
            photoInfo = photoArray.get(i).second;
            friendQueue.add(parseToPhoto(bitmap, photoInfo));
            Log.v("fillQueue", ""+ friendQueue.size());
        }
    }

    public Photo parseToPhoto(String bitmap, String photoInfo){
        //"karma@locName@latitude@longitude@date@dayOfWeek@time"
        Photo photo = new Photo(context);
        String[] arr= photoInfo.split("@");
        photo.ogAlbum=3;
        /*photo.date= arr[4];
        photo.time = arr[6];
        photo.latitude = Double.parseDouble(arr[2]);
        photo.longitude = Double.parseDouble(arr[3]);
        photo.locName = arr[1];
        photo.karma = Integer.parseInt(arr[0]);*/
        return photo;
    }
}
