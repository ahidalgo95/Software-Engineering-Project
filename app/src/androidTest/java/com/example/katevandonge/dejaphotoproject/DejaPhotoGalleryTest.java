package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.os.Environment;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Tests for DejaPhotoGallery class.
 */

public class DejaPhotoGalleryTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity= new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testConstructor(){
        Context context= mainActivity.getActivity().getApplicationContext();
        DejaPhotoGallery gallery= new DejaPhotoGallery(context);
        assertEquals(gallery.size, 0);
        assertEquals(gallery.queryCall, 0);
        assertEquals(gallery.counter, 0);
        assertEquals(gallery.djQueue.poll(), null);
    }

    @Test
    public void testQueryTakenPhotos(){
        Context context= mainActivity.getActivity().getApplicationContext();
        DejaPhotoGallery gallery= new DejaPhotoGallery(context);
        gallery.queryTakenPhotos();
        File fileDir = new File(Environment.getExternalStorageDirectory()+File.separator+".privPhotos");
        File [] files = fileDir.listFiles();
        assertEquals(files.length, gallery.counter);
        assertEquals(gallery.djQueue.size(), files.length);
    }


    @Test
    public void testReturnPQ(){
        Context context= mainActivity.getActivity().getApplicationContext();
        DejaPhotoGallery gallery= new DejaPhotoGallery(context);
        Photo p= new Photo(context);
        gallery.djQueue.add(p);
        assertEquals(gallery.getPQ().size(), 1);
    }


}
