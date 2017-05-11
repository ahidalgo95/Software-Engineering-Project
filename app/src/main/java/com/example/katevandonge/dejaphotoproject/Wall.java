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

import static android.R.id.list;

public class Wall extends Activity { //android.app.WallpaperManager{

    Gallery pList;
    WallpaperManager myWall;
    Context conR;



    public Wall(Context context) {
        conR=context;
    }

    /*
    *  Sets input bitmap to wallpaper.
    * */
    public void set(WallpaperManager wm, Bitmap bm){
        try {
            myWall.setBitmap(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clear(WallpaperManager myWall){
        try {
            myWall.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void rotatePhoto(Bitmap bm) {

        Gallery list = new Gallery(conR);
        list.queryGallery(conR.getContentResolver()); //queries photo uris
        list.fillQueue(); //fills priority queue with picture objs
        Log.v("list size", Integer.toString(list.getSize()));

        Photo popped= list.photoQueue.poll();
        bm = popped.toBitmap(conR.getContentResolver());
        WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
        Wall wall = new Wall(conR);
        wall.set(wm, bm);

        //user set time to update

        //to do time goes by next photo set


        };

}
//(WallpaperManager)context.getSystemService(Context.WALLPAPER_SERVICE);
