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

    Context con;
    int size;
    ArrayList<Uri> uriList;
    ArrayList<Long> dateList;
    ArrayList<Double> latList;
    ArrayList<Double> longList;
    PriorityQueue<Photo> photoQueue;
    static PriorityQueue<Photo> queueCopy;
    Comparator<Photo> photoComparator;
    int queryCall;


    /*
    * Constructor for Gallery class.
    * */
    @TargetApi(24)
    public Gallery(Context context){
        size = 0;
        queryCall=0;
        con= context;
        uriList = new ArrayList<Uri>(size);
        dateList = new ArrayList<Long>(size);
        latList = new ArrayList<Double>(size);
        longList = new ArrayList<Double>(size);
        photoComparator= new PhotoComparator();
        photoQueue = new PriorityQueue<Photo>(photoComparator); //this isn't an error
        queueCopy = new PriorityQueue<Photo>(photoComparator);
    }

    public int getSize(){
        return size;
    }


    /*
    * Queries photos from Camera roll on Android, fills uriList with Uris of our photos.
    * */
    public int queryGallery(ContentResolver cr){
        Uri imagesURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] proj = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.LATITUDE, MediaStore.Images.Media.LONGITUDE};
        Cursor cursor;
        cursor = cr.query(imagesURI,proj, null, null, MediaStore.Images.Media._ID);
        if(queryCall==0) {
            size = cursor.getCount();
        }
        queryCall++;
        int sizeCalledAgain= cursor.getCount();
        Log.v("cursor size", Integer.toString(size));

        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            final int longColumn= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE);
            final int latColumn= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE);
            //Log.v("latColumn", Integer.toString(latColumn));
            final int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
            do {
                final String data = cursor.getString(dataColumn);
                final Double lon= cursor.getDouble(longColumn);
                final Double lat= cursor.getDouble(latColumn);
                final long date= cursor.getLong(dateColumn);
                Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, data);
                dateList.add(date);
                latList.add(lat);
                longList.add(lon);
                uriList.add(uri);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sizeCalledAgain;
    }


    /*
    * Makes photo objects and fills with corresponding uris and fills photoQueue with photo objects.
    * */
    public void fillQueue (){
        for(int i = 0; i<size ; i++){
            Photo photo = new Photo(con);
            photo.setUri(uriList.get(i));
            photo.setTimeTotal(dateList.get(i));
            photo.setDate(dateList.get(i));
            photo.setLatitude(latList.get(i));
            photo.setLongitude(longList.get(i));
            photo.locScreenHelper();
            photo.setWeight();
            photoQueue.add(photo);
            queueCopy.add(photo);
        }

    }


    /*
    * To be called when we need to update the queue with a service.
    * */
    @TargetApi(24)
    public void updateQueue(){
        Log.v("update queue was called", "!");
        PriorityQueue<Photo> newQueue= new PriorityQueue<Photo>(photoComparator);
        PriorityQueue<Photo> newQcopy = new PriorityQueue<Photo>(photoComparator);
        newQueue= convertToPQ();
        newQcopy= convertToPQ();
        int queriedSize=queryGallery(con.getContentResolver());
        Log.v("size of orig gallery", Integer.toString(size));
        if(queriedSize>size){
            Log.v("new size of gallery", Integer.toString(queriedSize));
            Log.v("adding", Integer.toString(queriedSize-size)+" new photos to gallery");
            for(int i = size; i<queriedSize ; i++){
                Log.v("photos being added to", " gallery");
                Photo photo = new Photo(con);
                photo.setUri(uriList.get(i));
                photo.setTimeTotal(dateList.get(i));
                photo.setDate(dateList.get(i));
                photo.setLatitude(latList.get(i));
                photo.setLongitude(longList.get(i));
                photo.locScreenHelper();
                photo.setWeight();
                newQueue.add(photo);
                newQcopy.add(photo);
                size = queriedSize;
            }
        }
        Wall.photoArr= convertToArray(newQueue);
        photoQueue=newQueue;
        queueCopy=newQcopy;
    }

    /*
    * Converts photo array in wall to priority queue.
    * */
    @TargetApi(24)
    public PriorityQueue<Photo> convertToPQ(){
        PriorityQueue<Photo> newPQ= new PriorityQueue<Photo>(photoComparator);
        Photo[] pArray= Wall.photoArr;
        Photo currPhoto;
        Log.v("convert to PQ called"," ");
        for(int i=0; i<pArray.length; i++){
            Log.v("from array to pq", Integer.toString(i));
            currPhoto= pArray[i];
            int j=1;
            if(pArray[i]!=null) {
                currPhoto.setWeight();
                newPQ.add(currPhoto);
                Log.v("adding from array to pq", Integer.toString(j)+"times");
                j++;
            }
        }
        return newPQ;
    }


    /*
     * Converts input priority queue to array.
     */
    public Photo[] convertToArray(PriorityQueue<Photo> polledPQ){
        Photo polled;
        Photo[] newPArray= new Photo[polledPQ.size()];
        int i=0;
        while(polledPQ.size() != 0){
            Log.v("pq convertToArray", Integer.toString(i));
            polled= polledPQ.poll();
            //Log.i("Gallery Convert2Array" , polled.locName);
            newPArray[i]=polled;
            i++;
        }
        return newPArray;
    }


}
