package com.example.katevandonge.dejaphotoproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.app.WallpaperManager;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import static android.R.id.list;
import static com.example.katevandonge.dejaphotoproject.MainActivity.master;


/*
* Wall class for putting wallpaper on user's phone.
* */

public class Wall extends Activity {

    static Gallery galleryK;            //our gallery object
    static WallpaperManager myWall;     //our wall object
    static PriorityQueue<Photo> pListOld;  //priorityqueue of photos to make photoArr
    static PriorityQueue<Photo> pList;
    Context con;
    static Photo curr;
    static int counter;                 //counter to be used in widget
    static Photo [] photoArr;           //photoArr to be used in widget
    static Photo [] allGall;
    static ArrayList<Photo> photoAL;
    static int Qsize;                          //size of pqueue
    static int Rsize;

    /*
    * Constructor for our Wall object.
    * */
    public Wall(Context context, Gallery gallery, WallpaperManager wm) {
        galleryK = gallery;
        con = context;
        pListOld = gallery.queueCopy;
        pList = MasterGallery.MasterQueue;
        curr = pListOld.poll();
        pList.add(curr);
        myWall = wm;
        counter = 0;
        Qsize = pListOld.size();
        Rsize = pList.size();
        allGall = new Photo[Qsize];
        photoArr = new Photo[Rsize];
        updateArray();
    }
    public static void updateArray(){
        Qsize = pListOld.size();
        if(pList.size()==0){
            pList.add(curr);
        }
        Rsize = pList.size();
        photoArr = new Photo[Rsize];
        Log.v("wall up arr", ""+Rsize);
        for (int i = 0; i < Qsize; i++) { //poll photos length of input pqueue
            allGall[i] = pListOld.poll();
        }

        for (int i = 0; i < Rsize; i++) { //poll photos length of input pqueue
            photoArr[i] = pList.poll();
        }

    }


}

