package com.example.katevandonge.dejaphotoproject;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by luujfer on 5/9/17.
 */

public class PhotoComparator implements Comparator<Photo>{

    @Override
    public int compare(Photo a, Photo b){
      //Using weights to help order a priority queue
      if(a.weight>b.weight){
          return -1;

      }
      //Using recent time as a time breaker to help more recent be more likely
      if(a.weight == b.weight){
          if(a.recentTime>b.recentTime) {return -1;}
      }
      return 1;
    }
}
