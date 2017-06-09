package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

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
        assertEquals(gallery.string, "location@date@time@lat@long@locname@karma");
    }

    @Test
    public void testFillQueue(){
        Context context= mainActivity.getActivity().getApplicationContext();
        FriendGallery gallery= new FriendGallery(context);
        String[] a = new String[1];
        String fake= "hell"@"Day"@"time"@555555@555555@"Here"@1;



    }


}
