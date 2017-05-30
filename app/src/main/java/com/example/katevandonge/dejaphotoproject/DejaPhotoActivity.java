package com.example.katevandonge.dejaphotoproject;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

/**
 * Created by luujfer on 5/29/17.
 */

public class DejaPhotoActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryTakenPhotos();
    }

    public void queryTakenPhotos(){
       // Uri imagesURI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; //uri to have access gallery
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File [] files = storageDir.listFiles();
        for(int i = 0; i < files.length; i++)
        {
           // Bitmap photo = BitmapFactory.decodeFile(files[i].getAbsolutePath());

        }
        Log.i("QUERY SIZE",""+ files.length);

        //Uri fileUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", storageDir);

    }
}
