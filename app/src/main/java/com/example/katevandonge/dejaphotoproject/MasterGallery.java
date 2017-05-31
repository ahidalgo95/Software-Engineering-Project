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

    static CopiedGallery copied = new CopiedGallery();
    static PriorityQueue<Photo> masterQueue;

    public void MasterGallery(Context context){
        //copied = new CopiedGallery(context);
    }

    public void copyToMasterGall(Context context){
        Log.v("made it into copytomasterGAll", "made it into copytomasterGall");
        masterQueue = new PriorityQueue<>(copiedQueue);
        Log.v("COPIEDQUEUE copied to MASTERQUEUE", "COPIEDQUEUE copied to MASTERQUEUE");

    }
}
