package com.example.katevandonge.dejaphotoproject;

import android.util.Log;

import java.util.Comparator;

/**
 * Comparator class used in our Photo PriorityQueue.
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
          if(a.timeTotal>b.timeTotal) {
              return -1;
          }
      }
      return 1;
    }
}
