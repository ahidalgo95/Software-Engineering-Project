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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import static android.R.id.list;

public class Wall extends Activity { //android.app.WallpaperManager{

    PriorityQueue<Photo> pList;
    WallpaperManager myWall;
    Context conR;
<<<<<<< HEAD
    /*Photo currPhoto;
    Iterator<Photo> iter;*/

=======
    Photo currPhoto;
    static Photo [] photoArr;
    static int currIndex;
    ///Comparator<Photo> photoComparator;
>>>>>>> ab4a4d1534ff3112b51aecd4ba323192ca1c68fb

    public Wall(Context context, Gallery gallery, WallpaperManager wm) {
        conR=context;
        pList = gallery.photoQueue;
        myWall = wm;
      // photoComparator = new PhotoComparator();
       // photoArr = Arrays.sort(pList.toArray(),);
        rotatePhoto();

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
        Log.v("next", "next");
        return;
    }

    public void prev(){
        Log.v("prev", "prev");
        return;
    }



}
//(WallpaperManager)context.getSystemService(Context.WALLPAPER_SERVICE);
