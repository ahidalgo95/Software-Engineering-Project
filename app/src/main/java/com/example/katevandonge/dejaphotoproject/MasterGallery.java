package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Properties;

import static com.example.katevandonge.dejaphotoproject.CopiedGallery.copiedQueue;


/*
* Class to manage all gallerys' photos.
* */
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
    PriorityQueue<Photo> newQCopied;
    PriorityQueue<Photo> newQCamera;
    PriorityQueue<Photo> newQFriends;
    Context context;
    Photo myArr[];
    Photo sharedPhotos[];



    /*
    * Constructor for MasterGallery class.
    * */
    @TargetApi(24)
    public MasterGallery(Context context){
        photoComparator= new PhotoComparator();
        countRuns = 0;
        Log.v("master", "constructor");
        MasterQueue = new PriorityQueue<Photo>(photoComparator);
        copiedSet = MainActivity.dpcopied.getPQ();
    }

    /*
    * Method to update the Master queue depending on the mode(s) user wants to be in.
    * */
    public void updateMasterQ(boolean copiedMode, boolean cameraMode, boolean friendMode ){
        Log.i("MasterGallery: ", "updateMasterQ camera mode "+cameraMode);
        Log.i("MasterGallery: ", "updateMasterQ copy mode "+copiedMode);
        Log.i("MasterGallery: ", "updateMasterQ sharing mode "+ MainActivity.sharingMode);
        if(MasterQueue.size()>0){
            newQCopied = convertToPQ(1);
            newQCamera = convertToPQ(2);
            newQFriends = convertToPQ(3);
            if(CopiedGallery.mostRecent!=null) {
                newQCopied.add(CopiedGallery.mostRecent);
            }
            MainActivity.dpcopied.copiedQueue = newQCopied;
            MainActivity.djpGallery.djQueue= newQCamera;
            //set friends
            Log.i("MasterGallery: ", "updateMasterQ MasterQueue size" + MasterQueue.size());
            MasterQueue.clear();
        }
        if(copiedMode){
            Log.i("MasterGallery: ", "updateMasterQ addCopied");
            addCopied();
        }

        if(cameraMode){
            Log.i("MasterGallery: ", "updateMasterQ addCamera");
            addCamera();
        }
        if(MainActivity.sharingMode) {
            Log.i("MasterGallery: ", "updateMasterQ createSharedArray");
            createSharedArray();
            //call method to push to firebase on myArray
        }
        if(friendMode){
            Log.i("MasterGallery: ", "updateMasterQ addFriend");
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


    /*
    * Method to add the DejaPhotoCopied photos to MasterQueue.
    * */
    public void addCopied(){
        copiedSet = MainActivity.dpcopied.getPQ();
        while(copiedSet.size() > 0) {
            countRuns++;
            Photo curr = copiedSet.poll();
            MasterQueue.add(curr);
        }
        Log.i("MasterGallery: ", "addCopied MQ.size " + MasterQueue.size());
        Log.i("MasterGallery: ", "addCopied countRuns " + countRuns);
        Log.i("MasterGallery: ", "addCopied copiedSet " + copiedSet.size());
    }

    /*
     *  Method to add the DejaPhoto photos to MasterQueue.
     *  return: void
     *  */
    public void addCamera(){
        Log.i("MasterGallery: ", "addCamera");
        MainActivity.djpGallery.queryTakenPhotos();
        djSet = MainActivity.djpGallery.returnQ();
        while(djSet.size() > 0) {
            Photo curr = djSet.poll();
            MasterQueue.add(curr);
        }

    }

    /*public void updateMasterAL(){
        ArrayList<Photo> myAL = MainActivity.dpcopied.getAL();
        for(int i=0; i<myAL.size(); i++){
            Photo curr = myAL.get(i);
            MasterQueue.add(curr);
        }
    }*/


    /*
    * Method to create the array of shared photos that will be passed to Firebase.
    * return: void
    * */
    public void createSharedArray(){
        MasterShared = new PriorityQueue<Photo>(MasterQueue);
        sharedPhotos = new Photo[MasterShared.size()];
        int i=0;
        Photo pic;
        myArr= new Photo[MasterShared.size()];
        while(MasterShared.size() != 0){
            Log.i("MasterGallery: ", "createSharedArray i");
            pic = MasterShared.poll(); //poll photo from queue
            myArr[i] = pic;
            //MasterShared.add(pic);
            i++;
        }
        Log.i("MasterGallery: ", "createSharedArray of size" + i+1);
        return;


    }

    /*
    * Method to convert a priorityQueue to array.
    * */
    @TargetApi(24)
    public void convertToArray(PriorityQueue<Photo> old){
        Photo polled;
        PriorityQueue<Photo> polledPQ= new PriorityQueue<Photo>(old);
        myArr = new Photo[polledPQ.size()];
        int i=0;
        while(polledPQ.size() != 0){
            polled = polledPQ.poll(); //poll photo from queue
            myArr[i] = polled;
            Log.i("MasterGallery: ", "convertToArray" + i);
            //Wall.photoArr[i]=polled; //add photo to array
            //MasterQueue.add(polled);
            i++;
        }
        return;
    }



    /*
    * Method to convert a respective photo array to a respective priorityQueue.
     * return: priorityQueue
    * */
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
                    Log.i("Master Gallery: ", "from array to pq " + Integer.toString(i));
                }
            }
        }
        return newPQ;
    }
}
