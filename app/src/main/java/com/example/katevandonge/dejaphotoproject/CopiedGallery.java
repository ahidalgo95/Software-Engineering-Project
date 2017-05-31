package com.example.katevandonge.dejaphotoproject;

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

    public CopiedGallery() {
        photoComparator = new PhotoComparator();
        copiedQueue = new PriorityQueue<Photo>(photoComparator); //this isn't an error
    }

    public static void addPhoto(Uri uri){
        Log.v("copied gallery", "addphoto called");
        for(int i=0; i<Wall.photoArr.length; i++){
            Photo curr = Wall.photoArr[i];
            if(curr.photouri.equals(uri)){
                Log.v("copied gallery", "photo matched!");
                copiedQueue.add(curr);
                break;
            }
            else{
                Log.v("COPIED GALLERY", "ERROR");
            }
        }
    }

    public static PriorityQueue<Photo> getPQ(){
        Log.v("copied gallery", "called copiedgall to get PQ");
        return copiedQueue;
    }

}
