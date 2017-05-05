package com.example.katevandonge.dejaphotoproject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.app.WallpaperManager;

import java.io.IOException;


public class Wall extends Activity { //android.app.WallpaperManager{
    WallpaperManager myWall;

    public Wall() {
    }

    public void set(WallpaperManager yourwall){
        try {
            yourwall.setResource(+R.drawable.pic);
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


}
//(WallpaperManager)context.getSystemService(Context.WALLPAPER_SERVICE);
