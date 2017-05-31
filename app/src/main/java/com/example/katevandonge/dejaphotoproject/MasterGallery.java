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
    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> MasterQueue;
    static CopiedGallery copied = new CopiedGallery();
    static PriorityQueue<Photo> copiedSet;
    static PriorityQueue<Photo> dj;
    static PriorityQueue<Photo> friendSet;

    public void MasterGallery(){
        //copied = new CopiedGallery(context);
        MasterQueue = new PriorityQueue<Photo>(photoComparator);
        copiedSet = copied.getPQ();


    }

    public void createPQ(){
        while(copiedSet.size()>=0){
            Photo curr = copiedSet.poll();
            MasterQueue.add(curr);
        }
    }

}
