package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;

import java.util.Comparator;
import java.util.PriorityQueue;

import static com.example.katevandonge.dejaphotoproject.CopiedGallery.copiedQueue;

/**
 * Created by katevandonge on 5/31/17.
 */

public class MasterGallery {


    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> MasterQueue;
    static CopiedGallery copied;
    static PriorityQueue<Photo> copiedSet;
    static PriorityQueue<Photo> djSet;
    static PriorityQueue<Photo> friendSet;
    static  DejaPhotoGallery DJGallery;
    Context con;

    @TargetApi(24)
    public void MasterGallery(Context context) {

        //copied = new CopiedGallery(context);
        MasterQueue = new PriorityQueue<Photo>(photoComparator);
        con = context;
        copied = new CopiedGallery();
        DJGallery = new DejaPhotoGallery(con);
      //  DJGallery.queryTakenPhotos();
        //djSet = DJGallery.returnQ();
        //copiedSet = copied.getPQ();

    }

    public void copyToMasterGall(Context context){
        Log.v(" ", "made it into copytomasterGAll");
        MasterQueue = new PriorityQueue<>(copiedQueue);
        Log.v(" ", "COPIEDQUEUE copied to MASTERQUEUE");

    }

    public void createPQ(){
        while(copiedSet.size()>=0){
            Photo curr = copiedSet.poll();
            MasterQueue.add(curr);
        }
    }

}
