package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.util.Comparator;
import java.util.PriorityQueue;

import static com.example.katevandonge.dejaphotoproject.CopiedGallery.copiedQueue;

/**
 * Created by katevandonge on 5/31/17.
 */

public class MasterGallery {
    Comparator<Photo> photoComparator;
    //static CopiedGallery copiedGallery;
    static PriorityQueue<Photo> MasterQueue;
    static int countRuns;
    static PriorityQueue<Photo> MasterQueueCopy;
    //static CopiedGallery copied;//= new CopiedGallery();
    static PriorityQueue<Photo> copiedSet;
    //static PriorityQueue<Photo> copiedSet2;
    static PriorityQueue<Photo> dj;
    static PriorityQueue<Photo> friendSet;

    @TargetApi(24)
    public MasterGallery(){
        //copied = new CopiedGallery();
        photoComparator= new PhotoComparator();
        countRuns = 0;
        Log.v("master", "constructor");
        MasterQueue = new PriorityQueue<Photo>(photoComparator);
        MasterQueueCopy = new PriorityQueue<Photo>(photoComparator);
        copiedSet = MainActivity.dpcopied.getPQ();
        //copiedSet = copiedSet2;

    }

    public void addCopied(){
        copiedSet = MainActivity.dpcopied.getPQ();
        //if(MainActivity.copiedMode) {
            while(copiedSet.size() > 0) {
                countRuns++;
                Photo curr = copiedSet.poll();
                MasterQueue.add(curr);
            }
            String info2 = "" + MasterQueue.size();
            String info3 = "" + countRuns;
            Log.v("Master M size", info2);
            Log.v("Master runs", info3);
        String info4 = "" + copiedSet.size();
        Log.v("Master copied", info4);
            Wall.pList = MasterQueue;
            Wall.counter=-1;
            //MasterQueueCopy = MasterQueue;
            //convertToArray(MasterQueueCopy);
        //}
    }


    /*public void createPQ(){
        Log.v("mster", "4");
        //updateCopied();
        Log.v("mster", "6");
        Log.v("Master", "create called");
        String info = "" + copiedSet.size();
        Log.v("Master copied size", info);
        while(copiedSet.size()>0){
            Photo curr = copiedSet.poll();
            MasterQueue.add(curr);
        }
        String info2 = "" + MasterQueue.size();
        Log.v("Master M size", info2);
        Wall.pList = MasterQueue;
        Wall.counter=-1;
        MasterQueueCopy = MasterQueue;
        convertToArray(MasterQueueCopy);
    }*/

    /*public void updateCopied(){
        Log.v("mster", "5");
        copiedSet = MainActivity.djcopied.getPQ();
    }*/

    public void convertToArray(PriorityQueue<Photo> polledPQ){
        Photo polled;
        int i=0;
        while(polledPQ.size() != 0){
            polled = polledPQ.poll(); //poll photo from queue
            Wall.photoArr[i]=polled; //add photo to array
            i++;
        }
        return;
    }

    /*public void addCopiedPhoto(Uri uri){
        Log.v("mster", "1");
        //copied.addPhoto(uri);
        Log.v("mster", "3");
        createPQ();
    }*/

}
