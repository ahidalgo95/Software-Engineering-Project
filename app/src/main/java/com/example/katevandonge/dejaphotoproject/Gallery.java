package com.example.katevandonge.dejaphotoproject;


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

    Context con;                            //context variable
    int size;                               //size of our gallery
    ArrayList<Uri> uriList;                 //list of uris read in when queried
    ArrayList<Long> dateList;               //list of dates read in when queried
    ArrayList<Double> latList;              //list of latitudes read in when queried
    ArrayList<Double> longList;             //list of longitudes read in when queried
    PriorityQueue<Photo> photoQueue;        //queue for displaying highest priority pics
    static PriorityQueue<Photo> queueCopy;  //copy of our queue to be used in wall
    Comparator<Photo> photoComparator;      //comparator for our photo queue
    int queryCall;                          //number of times query has been called


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
        queueCopy = new PriorityQueue<Photo>(photoComparator);  //@TargetApi(24) used and fixes
    }

    /*
    * Getter method for our size variable.
    * */
    public int getSize(){
        return size;
    }


    /*
    * Queries photos from Camera roll on Android, fills uriList with Uris of our photos.
    * */
    public int queryGallery(ContentResolver cr){
        Uri imagesURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; //uri to have access gallery

        //array that we use to store data types during query
        String [] proj = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_TAKEN, MediaStore.Images.Media.LATITUDE, MediaStore.Images.Media.LONGITUDE};
        Cursor cursor; //cursor to go through array
        cursor = cr.query(imagesURI,proj, null, null, MediaStore.Images.Media._ID); //query photos by ID
        if(queryCall==0) {
            size = cursor.getCount(); //set size if query hasnt been called before
        }
        queryCall++;
        int sizeCalledAgain= cursor.getCount(); //var we return
        Log.i("Gallery:", "cursor size" + Integer.toString(size));

        if (cursor.moveToFirst()) { //move to first row of array
            //gets column indices
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            final int longColumn= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE);
            final int latColumn= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE);
            //Log.v("latColumn", Integer.toString(latColumn));
            final int dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
            do {
                //gets data from columns and adds to corresponding list
                final String data = cursor.getString(dataColumn);
                final Double lon= cursor.getDouble(longColumn);
                final Double lat= cursor.getDouble(latColumn);
                final long date= cursor.getLong(dateColumn);
                Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, data);
                dateList.add(date);
                latList.add(lat);
                longList.add(lon);
                uriList.add(uri);
            } while (cursor.moveToNext()); //move to next
        }
        cursor.close();
        return sizeCalledAgain;
    }


    /*
    * Makes photo objects and fills with corresponding uris and fills photoQueue with photo objects.
    * */
    public void fillQueue (){
        for(int i = 0; i<size ; i++){
            Photo photo = new Photo(con); //make new photo
            //set fields in photo
            photo.setUri(uriList.get(i));
            photo.setTimeTotal(dateList.get(i));
            photo.setDate(dateList.get(i));
            photo.setLatitude(latList.get(i));
            photo.setLongitude(longList.get(i));
            photo.locScreenHelper();
            photo.setWeight(); //set photo weight
            //add photos to queue
            photoQueue.add(photo);
            queueCopy.add(photo);
        }

    }


    /*
    * To be called when we need to update the queue with a service.
    * */
    @TargetApi(24)
    public void updateQueue(){
        PriorityQueue<Photo> newQueue= new PriorityQueue<Photo>(photoComparator);
        PriorityQueue<Photo> newQcopy = new PriorityQueue<Photo>(photoComparator);
        newQueue= convertToPQ(); //convert wall array to pq
        newQcopy= convertToPQ(); //convert wall array to pq
        int queriedSize=queryGallery(con.getContentResolver()); //requery gallery
        Log.i("Gallery:", "gallery size "+ Integer.toString(size));
        if(queriedSize>size){ //if requeried size greater (more photos taken)
            Log.i("Gallery:", "requeried size "+Integer.toString(queriedSize));
            Log.i("Gallery:", "photos to add "+Integer.toString(queriedSize-size));
            for(int i = size; i<queriedSize ; i++){
                Log.i("Gallery:", "adding photo");
                Photo photo = new Photo(con); //make new photo
                //set fields of photo
                photo.setUri(uriList.get(i));
                photo.setTimeTotal(dateList.get(i));
                photo.setDate(dateList.get(i));
                photo.setLatitude(latList.get(i));
                photo.setLongitude(longList.get(i));
                photo.locScreenHelper();
                photo.setWeight(); //set weight
                //add photo to queues
                newQueue.add(photo);
                newQcopy.add(photo);
                size = queriedSize;
            }
        }
        Wall.photoArr= convertToArray(newQueue);

        Wall.pList = newQcopy;
        photoQueue=newQueue; //set new queues to class queues
        queueCopy=newQcopy;
        Wall.counter=-1;
    }

    /*
    * Converts photo array in wall to priority queue.
    * */
    @TargetApi(24)
    public PriorityQueue<Photo> convertToPQ(){
        PriorityQueue<Photo> newPQ= new PriorityQueue<Photo>(photoComparator);
        Photo[] pArray= Wall.photoArr;
        Photo currPhoto;
        for(int i=0; i<pArray.length; i++){ //for length of array
            currPhoto= pArray[i];
            int j=1;
            if(pArray[i]!=null) {
                currPhoto.setWeight();
                newPQ.add(currPhoto); //add photos to priority queue
                Log.i("Gallery", "from array to pq "+Integer.toString(j));
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
            Log.i("Gallery:", "convert to array "+Integer.toString(i));
            polled= polledPQ.poll(); //poll photo from queue
            //Log.i("Gallery Convert2Array" , polled.locName);
            newPArray[i]=polled; //add photo to array
            i++;
        }
        return newPArray;
    }


}
