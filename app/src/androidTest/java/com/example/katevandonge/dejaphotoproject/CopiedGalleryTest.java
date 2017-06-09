package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by adriancordovayquiroz on 6/8/17.
 */

public class CopiedGalleryTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity= new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testConstructor(){
        Context context= mainActivity.getActivity().getApplicationContext();
        CopiedGallery gallery= new CopiedGallery();
        assertEquals(gallery.copiedQueue.poll(), null);
        //assertEquals(gallery.copiedAL.size(), 0);
    }

    //addPhoto tested with logs

    @Test
    public void getPQTest(){
        Context context= mainActivity.getActivity().getApplicationContext();
        CopiedGallery gallery= new CopiedGallery();
        assertEquals(gallery.copiedQueue.size(), 0);
        assertEquals(gallery.getPQ(), gallery.copiedQueueCopy);
        assertEquals(gallery.copiedQueueCopy.size(), 0);
    }



}
