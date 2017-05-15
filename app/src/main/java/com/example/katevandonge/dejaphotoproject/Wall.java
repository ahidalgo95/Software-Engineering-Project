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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import static android.R.id.list;


/*
* Wall class for putting wallpaper on user's phone.
* */

public class Wall extends Activity {

    static Gallery galleryK;            //our gallery object
    static WallpaperManager myWall;     //our wall object
    static PriorityQueue<Photo> pList;         //priorityqueue of photos to make photoArr
    Context con;
    static int counter;                 //counter to be used in widget
    static Photo [] photoArr;           //photoArr to be used in widget
    int Qsize;                          //size of pqueue



    /*
    * Constructor for our Wall object.
    * */
    public Wall(Context context, Gallery gallery, WallpaperManager wm) {
        galleryK = gallery; //set gallery
        con = context;
        pList = gallery.queueCopy;  //set queue
        myWall = wm; //set wallpapermanager
        counter = 0;
        Qsize = pList.size();
        Log.i("Wall: ", "size "+Integer.toString(Qsize));
        photoArr = new Photo[Qsize];
        for (int i = 0; i < Qsize; i++) { //poll photos length of input pqueue
            photoArr[i] = pList.poll();
            Log.i("Wall: ","adding to photo array "+Integer.toString(i));

        }
    }

    /**
     * Reset recently shown every 24 hours
     */
    public void resetShown(){
        for(int looper=0; looper<Qsize; looper++){
            if(photoArr[looper]!=null){ //if photo not null
                photoArr[looper].shown=false;
                Log.i("Wall:", "reset shown "+Integer.toString(looper));
            }
        }
    }
}

