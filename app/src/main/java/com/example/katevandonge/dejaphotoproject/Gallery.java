package com.example.katevandonge.dejaphotoproject;

/**
 * Created by luujfer on 5/8/17.
 */

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Contains a uriList and PriorityQueue which holds photos.
 * Created by luujfer on 5/8/17.
 */

public class Gallery {
    int size;
    ArrayList<Uri> uriList;
    PriorityQueue<Photo> photoQueue;

    @TargetApi(24)
    public Gallery(){
        size = 0;
        Comparator<Photo> photoComparator= new PhotoComparator();
        photoQueue = new PriorityQueue<Photo>(photoComparator); //this isn't an error
    }

    public int getSize(){
        return size;
    }


    /*
    * Queries photos from Camera roll on Android, fills uriList with Uris of our photos.
    * */
    public void queryGallery(ContentResolver cr){
        Uri imagesURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] proj = { MediaStore.Images.Media._ID};
        Cursor cursor;
        cursor = cr.query(imagesURI,proj, null, null, MediaStore.Images.Media._ID);
        size = cursor.getCount();
        Log.v("cursor size", Integer.toString(size));
        ArrayList<Uri> result = new ArrayList<Uri>(size);

        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            do {
                final String data = cursor.getString(dataColumn);
                Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, data);
                result.add(uri);
                //Log.v("test", "testing to see if goes in loop");
            } while (cursor.moveToNext());
        }
        cursor.close();
        uriList = result;
    }


    /*
    * Makes photo objects and fills with corresponding uris and fills photoQueue with photo objects.
    * */
    public void fillQueue (){
        Uri uri;
        for(int i = 0; i<size ; i++){
            uri = uriList.get(i);
            Photo photo = new Photo();
            photo.setUri(uri);
            photoQueue.add(photo);
        }
        Log.v("size of photo queue", Integer.toString(photoQueue.size()));
    }




}
