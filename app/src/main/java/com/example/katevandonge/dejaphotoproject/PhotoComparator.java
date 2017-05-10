package com.example.katevandonge.dejaphotoproject;

import java.util.Comparator;

/**
 * Created by luujfer on 5/9/17.
 */

public class PhotoComparator implements Comparator<Photo>{

    @Override
    public int compare(Photo a, Photo b){
      if(a.weight>b.weight){ //THIS IS WRONG AND NEEDS TO BE CHANGED!!!
          return 0;
      }
      return 1;

    }
}
