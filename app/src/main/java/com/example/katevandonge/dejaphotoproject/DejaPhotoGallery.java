package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

/**
 * DejaPhotoGallery class to hold photos taken within Deja Photo app.
 */

public class DejaPhotoGallery{

    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> djQueue;        //main queue photos are stored in
    static PriorityQueue<Photo> djQueueCopy;    //copy of queue of photos
    Context con;
    int queryCall;
    int size;
    int counter;


    /*
    * Constructor for DejaPhotoGallery class.
    * */
    @TargetApi(24)
    public DejaPhotoGallery(Context context) {
        con= context;
        queryCall=0;
        counter = 0;
        size=0;
        photoComparator = new PhotoComparator();
        djQueue = new PriorityQueue<Photo>(photoComparator); //this isn't an error
    }


    /*
    * Queries photos from hidden files in external memory of phone.
    * return: void
    * */
    public void queryTakenPhotos(){
        //directory in phone's external we save photos to
        File fileDir = new File(Environment.getExternalStorageDirectory()+File.separator+".privPhotos");
        File [] files = fileDir.listFiles();
        if(files.length == 0){
            return;
        }
        Log.i("QueryTakenPhotos: ", "files length "+ files.length+"");

        long date;
        for(int i = counter; i < files.length; i++)
        {
            //get files based on Uri
            Uri fileUri = FileProvider.getUriForFile(con, con.getPackageName() + ".provider", files[i]);
            Photo photo = new Photo(con); //make new photo
            //set fields in photo
            photo.setUri(fileUri);
            photo.weight = 0;
            photo.DJP = true;
            photo.latitude = TrackLocation.mLatitude;
            photo.longitude= TrackLocation.mLongitude;
            photo.filePath = files[i].getAbsolutePath();
            photo.ogAlbum = 2;
            date = System.currentTimeMillis();
            photo.setDate(date);
            photo.setWeight();
            djQueue.add(photo);
            counter++;
        }
    }


    /*
    * Method to return the helper queue.
    * return: PriorityQueue
    * */
    public static PriorityQueue<Photo> returnQ(){
        Log.v("copied gallery", "called copiedgall to get PQ");
        djQueueCopy = new  PriorityQueue<Photo>(djQueue);
        return djQueueCopy;
    }


}
