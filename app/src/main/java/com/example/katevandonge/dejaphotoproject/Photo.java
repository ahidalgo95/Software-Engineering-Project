package com.example.katevandonge.dejaphotoproject;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

/**
 * Created by luujfer on 5/9/17.
 */

public class Photo {
    Uri photouri;
    Double date;
    Double latitude;
    Double longitude;

    int weight;

    public Photo(){
        weight=0;
    }

    /*
    * METHOD NEEDS TO BE MADE!!!
    * */
    public void setWeight(int loc, int date, int time){

    }

    public void setUri(Uri uri){
        photouri = uri;
    }

    public void setDate(Double d){ date = d; }

    public void setLatitude(Double d){ latitude= d; }

    public void setLongitude(Double d){ longitude= d; }

    /*
    * Converts uri to bitmap and returns that bitmap.
    * */
    public Bitmap toBitmap(ContentResolver cr){
        Bitmap bm = null;
        try {
            bm = MediaStore.Images.Media.getBitmap(cr,photouri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
}
