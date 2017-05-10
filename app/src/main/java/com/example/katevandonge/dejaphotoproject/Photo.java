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
    int weight;

    public Photo(){
        weight=0;
    }

    public void setUri(Uri uri){
        photouri = uri;
    }

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
