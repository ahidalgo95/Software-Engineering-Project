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
    static Photo mostRecent;

    @TargetApi(24)
    public CopiedGallery() {
        photoComparator = new PhotoComparator();
        copiedQueue = new PriorityQueue<Photo>(photoComparator); //this isn't an error
        //copiedQueueCopy = new PriorityQueue<Photo>(photoComparator); //this isn't an error

    }

    public static void addPhoto(Uri uri){
        Log.i("addPhoto: ", "addPhoto");
        for(int i=0; i<Wall.allGall.length; i++){
            Photo curr = Wall.allGall[i];
            if(curr.photouri.equals(uri)){
                Log.i("addPhoto:", "addPhoto- matched Uris");
                curr.ogAlbum = 1;
                copiedQueue.add(curr);
                mostRecent = curr;
                Log.i("addPhoto:", "size of Queue "+ copiedQueue.size());
                break;
            }
        }
    }

    public static PriorityQueue<Photo> getPQ(){
        copiedQueueCopy = new  PriorityQueue<Photo>(copiedQueue);
        return copiedQueueCopy;
    }





}
