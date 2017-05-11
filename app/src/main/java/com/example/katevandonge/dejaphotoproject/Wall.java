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
import java.util.Iterator;
import java.util.PriorityQueue;

import static android.R.id.list;

public class Wall extends Activity { //android.app.WallpaperManager{

    Gallery pList;
    WallpaperManager myWall;
    Context conR;
    Photo currPhoto;
    Iterator<Photo> iter;


    public Wall(Context context, Gallery gallery, WallpaperManager wm) {
        conR=context;
        pList = gallery;
        myWall = wm;
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
        iter = pList.photoQueue.iterator();
        Bitmap bm;
        if (iter.hasNext()) {
            Photo curr = iter.next();
            currPhoto = curr;
            bm = curr.toBitmap(getContentResolver());
            set(bm);
        }
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
