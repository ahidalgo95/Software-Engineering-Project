package com.example.katevandonge.dejaphotoproject;

/**
 * Created by luujfer on 5/8/17.
 */

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by luujfer on 5/8/17.
 */

public class PhotoList {

    int size;
    Uri imagesURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public Cursor cursor;
    String [] proj;


    public PhotoList(){
        size = 0;

    }

    public int getSize(){
        return size;
    }

    public ArrayList<String> queryGallery(ContentResolver cr){
        cursor = cr.query(imagesURI,proj, null, null, MediaStore.Images.Media._ID);
        size = cursor.getCount();
        Log.v("cursor size", Integer.toString(size));
        ArrayList<String> result = new ArrayList<String>(size);
        if (cursor.moveToFirst()) {
            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            do {
                final String data = cursor.getString(dataColumn);
                result.add(data);
                Log.v("test", "testing to see if goes in loop");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }




}
