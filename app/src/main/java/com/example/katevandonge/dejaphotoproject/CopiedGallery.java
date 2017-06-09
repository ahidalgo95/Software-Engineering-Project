package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Copied Gallery class to hold photos selected from galleries within phone's external memory.
 */

public class CopiedGallery implements GalleriesInterface {
    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> copiedQueue; //main queue for this class
    static PriorityQueue<Photo> copiedQueueCopy;
    static Photo mostRecent;                //saves the most recent photo selected


    /*
    * Constructor for CopiedGallery class.
    * */
    @TargetApi(24)
    public CopiedGallery() {
        photoComparator = new PhotoComparator();
        copiedQueue = new PriorityQueue<Photo>(photoComparator); //this isn't an error

    }

    /*
    * Helper to add a photo with a specified uri to the queue for copiedGallery class.
    * return: void
    * */
    public void addPhoto(Uri uri){
        Log.i("addPhoto: ", "addPhoto");
        for(int i=0; i<Wall.allGall.length; i++){
            Photo curr = Wall.allGall[i];
            //where current photo has the uri we are looking for
            if(curr.photouri.equals(uri)){
                Log.i("addPhoto:", "matched Uris");
                curr.ogAlbum = 1; //marker for copiedGallery
                copiedQueue.add(curr); //add to priorityqueue
                mostRecent = curr;
                Log.i("addPhoto:", "size of Queue "+ copiedQueue.size());
                break;
            }
        }
    }

    /*
    * Helper method to return the priority queue of photos we want to display when CopiedGallery
    * should be displayed.
    * returns: priorityQueue
    * */
    public PriorityQueue<Photo> getPQ(){
        copiedQueueCopy = new  PriorityQueue<Photo>(copiedQueue);
        return copiedQueueCopy;
    }





}
