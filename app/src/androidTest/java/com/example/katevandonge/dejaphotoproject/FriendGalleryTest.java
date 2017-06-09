package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.util.Pair;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by adriancordovayquiroz on 6/8/17.
 */

public class FriendGalleryTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity= new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testConstructor(){
        Context context= mainActivity.getActivity().getApplicationContext();
        FriendGallery gallery= new FriendGallery(context);
        assertEquals(gallery.friendQueue.size(), 0);
        assertEquals(context, gallery.context);
    }

    //fillQueue tested with logs

    @Test
    public void testFillBitmap(){
        Context context= mainActivity.getActivity().getApplicationContext();
        FriendGallery gallery= new FriendGallery(context);
        Bitmap b= null;
        String s= "0@hell@0@0@null@haha@thissux"; //"karma@locName@latitude@longitude@date@dayOfWeek@time"
        Photo parsed= gallery.parseToPhoto(b,s);
        Photo photo= new Photo(context);
        photo.ogAlbum=3;
        photo.storedBitmap= null;
        photo.date= null;
        photo.time = "thissux";
        photo.latitude = 0;
        photo.longitude = 0;
        photo.locName = "hell";
        photo.karma = 0;
        assertEquals(photo.karma, parsed.karma);
        assertEquals(photo.storedBitmap, parsed.storedBitmap);
        assertEquals(photo.date, parsed.date);
        assertEquals(photo.time, parsed.time);
        assertEquals(photo.locName, parsed.locName);
    }


}
