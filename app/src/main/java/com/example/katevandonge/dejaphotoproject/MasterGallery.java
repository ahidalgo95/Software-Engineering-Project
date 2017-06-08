package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import static com.example.katevandonge.dejaphotoproject.CopiedGallery.copiedQueue;

/**
 * Created by katevandonge on 5/31/17.
 */

public class MasterGallery {

    Comparator<Photo> photoComparator;
    static PriorityQueue<Photo> MasterQueue;
    static int countRuns;
    static PriorityQueue<Photo> MasterQueueCopy;
    static PriorityQueue<Photo> MasterQueueCopy2;
    static PriorityQueue<Photo> copiedSet;
    static PriorityQueue<Photo> djSet;
    static PriorityQueue<Photo> friendSet;
    static  DejaPhotoGallery DJGallery;
    PriorityQueue<Photo> newQCopied;
    PriorityQueue<Photo> newQCamera;
    PriorityQueue<Photo> newQFriends;
    Context con;
    Photo myArr[];


    @TargetApi(24)
    public MasterGallery(){
        //copied = new CopiedGallery();
        photoComparator= new PhotoComparator();
        countRuns = 0;
        Log.v("master", "constructor");
        MasterQueue = new PriorityQueue<Photo>(photoComparator);
        //MasterQueueCopy = new PriorityQueue<Photo>(photoComparator);
        copiedSet = MainActivity.dpcopied.getPQ();
        //copiedSet = copiedSet2;

    }
    public void updateMasterQ(boolean copiedMode, boolean friendMode, boolean cameraMode ){
        if(MasterQueue.size()>0){
            newQCopied = convertToPQ(1);
            newQCamera = convertToPQ(2);
            newQFriends = convertToPQ(3);
            newQCopied.add(CopiedGallery.mostRecent);
            MainActivity.dpcopied.copiedQueue = newQCopied;
            //set cam
            //set friends
            String info22 = "" + MasterQueue.size();
            Log.v("Master M size before!", info22);
            MasterQueue.clear();
        }
        if(copiedMode){
            addCopied();
        }
        if(friendMode){
            //addFriend();
        }
        if(cameraMode){
            //addCamera();
        }
        MasterQueueCopy = new PriorityQueue<Photo>(MasterQueue);
        MasterQueueCopy2 = new PriorityQueue<Photo>(MasterQueue);
        Wall.pList = MasterQueueCopy2;
        Wall.counter=0;
        convertToArray(MasterQueueCopy);
        Wall.updateArray();
        //Wall.photoArr = myArr;
    }

    public void addCopied(){
        copiedSet = MainActivity.dpcopied.getPQ();
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

    }

    public void updateMasterAL(){
        ArrayList<Photo> myAL = MainActivity.dpcopied.getAL();
        for(int i=0; i<myAL.size(); i++){
            Photo curr = myAL.get(i);
            MasterQueue.add(curr);
        }
    }

    public void convertToArray(PriorityQueue<Photo> polledPQ){
        Photo polled;
        myArr = new Photo[polledPQ.size()];
        int i=0;
        while(polledPQ.size() != 0){
            polled = polledPQ.poll(); //poll photo from queue
            myArr[i] = polled;
            Log.v("in here", "filling arr");
            //Wall.photoArr[i]=polled; //add photo to array
            //MasterQueue.add(polled);
            i++;
        }
        return;
    }

    public PriorityQueue<Photo> convertToPQ(int album){
        PriorityQueue<Photo> newPQ = new PriorityQueue<Photo>(photoComparator);
        Photo[] pArray= Wall.photoArr;
        Photo currPhoto;
        for(int i=0; i<pArray.length; i++){ //for length of array
            currPhoto = pArray[i];
            if(pArray[i]!=null) {
                //currPhoto.setWeight();
                if(currPhoto.ogAlbum==album) {
                    newPQ.add(currPhoto); //add photos to priority queue
                    Log.i("Master Gallery", "from array to pq " + Integer.toString(i));
                }
            }
        }
        return newPQ;
    }
}
