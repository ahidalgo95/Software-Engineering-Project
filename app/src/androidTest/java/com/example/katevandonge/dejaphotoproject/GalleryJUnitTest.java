package com.example.katevandonge.dejaphotoproject;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

/**
 * Created by adriancordovayquiroz on 5/12/17.
 */




public class GalleryJUnitTest extends Activity{

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);


    /*@Rule
    Context context;

    @Rule
    public Gallery galleryConst = new Gallery(context);

    @Test
    public void testConstructor(){

        Context context = getApplicationContext();
        Gallery galleryConst = new Gallery(context);


        assertEquals("0", galleryConst.size);
        assertEquals("0", galleryConst.uriList);
        assertEquals("0", galleryConst.dateList);
        assertEquals("0", galleryConst.longList);
        assertEquals("0", galleryConst.photoQueue);
        assertEquals("0", galleryConst.queueCopy);
        assertEquals("0", galleryConst.photoComparator);

    }*/
}
