package com.example.katevandonge.dejaphotoproject;

import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by katevandonge on 5/10/17.
 */


public class Karma{

    boolean karma;
    WallpaperManager pared;

    public Karma(){
        Log.v("k2", "karm2");
    }

    public Karma(Context context){
        karma = false;
        Log.v("kk", "karm message");
        pared = WallpaperManager.getInstance(context);
    }

    public void switching(){
        Log.v("Switching", "SWITCHING");
        if(karma == false){
            karma = true;
        }
        return;
    }
}
