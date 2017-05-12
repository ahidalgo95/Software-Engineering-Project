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

public class Wall extends Activity { //android.app.WallpaperManager{

    static Gallery galleryK;
    static WallpaperManager myWall;
    PriorityQueue<Photo> pList;
    Context conR;
    static int counter;
    /*Photo currPhoto;
    Iterator<Photo> iter;*/


    Photo currPhoto;
    static Photo [] photoArr;
    static int currIndex;
    ///Comparator<Photo> photoComparator;

    public Wall(Context context, Gallery gallery, WallpaperManager wm) {
        galleryK = gallery;
        conR=context;
        pList = gallery.queueCopy;
        myWall = wm;
        counter = 0;


        // photoComparator = new PhotoComparator();
       // photoArr = Arrays.sort(pList.toArray(),);
        //rotatePhoto();
        int Qsize = pList.size();
        String QQ = "" + Qsize;
        Log.v("QQ", "QQ");
        Log.v(QQ, QQ);
        photoArr = new Photo[Qsize];
        for(int i=0; i<Qsize; i++){
            photoArr[i] = pList.poll();
            String ii = "" + i;
            Log.v(ii, ii);
            Log.v("LOOP", "LOOP");
        }

        /*Bitmap bm = photoArr[0].toBitmap(getContentResolver());
        try {
            myWall.setBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    /*
    *  Sets input bitmap to wallpaper.
    * */
    public void set(Bitmap bm){
        try {
            myWall.setBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clear(){
        try {
            myWall.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotatePhoto() {

        /*iter = pList.photoQueue.iterator();
        Bitmap bm;
        if (iter.hasNext()) {
            Photo curr = iter.next();
            currPhoto = curr;
            bm = curr.toBitmap(getContentResolver());
            set(bm);

       /* iter = pList.photoQueue.iterator();
        Bitmap bm;
        if (iter.hasNext()) {
            Photo curr = iter.next();
            while(iter.hasNext() && curr.shown == true){
                curr = iter.next();
            }
            if(curr.shown == false){
                currPhoto = curr;
                bm = curr.toBitmap(getContentResolver());
                set(bm);
            }
            if(iter.hasNext() == false){

            }

        }*/
    }


        //user set time to update

        //to do time goes


    public void next(){
        Log.v("nextWALL", "nextWALL");
        return;
    }

    public void prev(){
        Log.v("prev", "prev");
        return;
    }



}
//(WallpaperManager)context.getSystemService(Context.WALLPAPER_SERVICE);
