package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by katevandonge on 5/31/17.
 */

public class CopiedGallery {
    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> copiedQueue;
    static PriorityQueue<Photo> copiedQueueCopy;

    @TargetApi(24)
    public CopiedGallery() {
        photoComparator = new PhotoComparator();
        copiedQueue = new PriorityQueue<Photo>(photoComparator); //this isn't an error
        copiedQueueCopy = new PriorityQueue<Photo>(photoComparator); //this isn't an error

    }

    public static void addPhoto(Uri uri){
        Log.v("copied gallery", "addphoto called");
        for(int i=0; i<Wall.allGall.length; i++){
            Photo curr = Wall.allGall[i];
            if(curr.photouri.equals(uri)){
                Log.v("copied gallery", "photo matched!");
                copiedQueue.add(curr);
                String size = "" + copiedQueue.size();
                Log.v("copied gallery", size);
                break;
            }
        }
    }

    public static PriorityQueue<Photo> getPQ(){
        //Log.v("copied gallery", "7");
        copiedQueueCopy = copiedQueue;
        return copiedQueueCopy;
    }

}
