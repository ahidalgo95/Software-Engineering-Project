package com.example.katevandonge.dejaphotoproject;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by luujfer on 5/9/17.
 */

public class PhotoComparator implements Comparator<Photo>{

    @Override
    public int compare(Photo a, Photo b){
      if(a.weight>b.weight){
         // Log.v("weight a>b", a.weight+" "+b.weight);
          return -1;

      }
       //Log.v("weight b<a", "!");
      return 1;

    }
}
