package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.util.Log;

import java.util.Comparator;
import java.util.PriorityQueue;

import static com.example.katevandonge.dejaphotoproject.CopiedGallery.copiedQueue;

/**
 * Created by katevandonge on 5/31/17.
 */

public class MasterGallery {

    static CopiedGallery copied;
    static  DejaPhotoGallery dj;
    static PriorityQueue<Photo> masterQueue;
    Context con;

    public void MasterGallery(Context context){
        con=context;
        copied= new CopiedGallery();
        dj= new DejaPhotoGallery(con);
        //copied = new CopiedGallery(context);
    }

    public void copyToMasterGall(Context context){
        Log.v(" ", "made it into copytomasterGAll");
        masterQueue = new PriorityQueue<>(copiedQueue);
        Log.v(" ", "COPIEDQUEUE copied to MASTERQUEUE");

    }
}
