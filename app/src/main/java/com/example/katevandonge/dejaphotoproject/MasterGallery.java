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
    static PriorityQueue<Photo> MasterShared;
    static PriorityQueue<Photo> friendSet;
    static  DejaPhotoGallery DJGallery;
    PriorityQueue<Photo> newQCopied;
    PriorityQueue<Photo> newQCamera;
    PriorityQueue<Photo> newQFriends;
    Context context;
    Photo myArr[];
    Photo sharedPhotos[];


    @TargetApi(24)
    public MasterGallery(Context context){
        context = context;
        //copied = new CopiedGallery();
        photoComparator= new PhotoComparator();
        countRuns = 0;
        Log.v("master", "constructor");
        MasterQueue = new PriorityQueue<Photo>(photoComparator);
        //MasterQueueCopy = new PriorityQueue<Photo>(photoComparator);
        copiedSet = MainActivity.dpcopied.getPQ();
        //copiedSet = copiedSet2;

    }
    public void updateMasterQ(boolean copiedMode, boolean cameraMode, boolean friendMode ){
        Log.i("MASTER GAL", "cam mode "+cameraMode);
        Log.i("MASTER GAL", "copy mode "+copiedMode);
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
            Log.i("adding", "copy mode "+copiedMode);
            addCopied();
        }

        if(cameraMode){
            Log.i("MASTER GAL", "cam mode "+cameraMode);
            addCamera();
        }
        if(MainActivity.sharingMode) {
            createSharedArray();
            MainActivity.currUser.addPhotos(sharedPhotos, context);
        }
        if(friendMode){
            //addFriend();
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

    public void createSharedArray(){
        MasterShared = new PriorityQueue<Photo>(MasterQueue);
        sharedPhotos = new Photo[MasterShared.size()];
        int i=0;
        Photo pic;
        while(MasterShared.size() != 0){
            pic = MasterShared.poll(); //poll photo from queue
            myArr[i] = pic;
            MasterShared.add(pic);
            i++;
        }
        return;


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

    public void addCamera(){
        Log.v("add camera", "add");
        MainActivity.djpGallery.queryTakenPhotos();
        djSet = MainActivity.djpGallery.returnQ();
        while(djSet.size() > 0) {
            Photo curr = djSet.poll();
            MasterQueue.add(curr);
        }

    }

    @TargetApi(24)
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
