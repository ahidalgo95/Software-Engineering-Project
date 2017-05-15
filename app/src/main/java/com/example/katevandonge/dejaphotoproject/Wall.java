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

    static Gallery galleryK;
    static WallpaperManager myWall;
    PriorityQueue<Photo> pList;
    Context con;
    static int counter;
    static Photo [] photoArr;
    int Qsize;



    /*
    * Constructor for our Wall object.
    * */
    public Wall(Context context, Gallery gallery, WallpaperManager wm) {
        galleryK = gallery;
        con = context;
        pList = gallery.queueCopy;
        myWall = wm;
        counter = 0;
        Qsize = pList.size();
        String QQ = "" + Qsize;
        Log.v("QQ", "QQ");
        photoArr = new Photo[Qsize];
        for (int i = 0; i < Qsize; i++) {
            photoArr[i] = pList.poll();
            String ii = "" + i;
            //Log.v("Photo Array", ii);
            Log.v("LOOP", "LOOP");
        }
    }
    /**
     * Reset recently shown every 24 hours
     */
    public void resetShown(){
        for(int looper=0; looper<Qsize; looper++){
            if(photoArr[looper]!=null){
                photoArr[looper].shown=false;
                //Log.v("WALL WALL reseting T/f", "WALL WALL reseting T/f");
            }
        }
    }
}

