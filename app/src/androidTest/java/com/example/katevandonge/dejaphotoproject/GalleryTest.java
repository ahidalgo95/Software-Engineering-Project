package com.example.katevandonge.dejaphotoproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.ActivityCompat;

import org.junit.Rule;
import org.junit.Test;

import com.example.katevandonge.dejaphotoproject.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

/**
 * Tests for Gallery class.
 */

public class GalleryTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity= new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testConstructor(){
        Context context= mainActivity.getActivity().getApplicationContext();
        Gallery galleryConst = new Gallery(context);
        assertEquals(0, galleryConst.size);
        assertEquals(0, galleryConst.queryCall);
        assertEquals(0, galleryConst.uriList.size());
        assertEquals(0, galleryConst.dateList.size());
        assertEquals(0, galleryConst.longList.size());
        assertEquals(0, galleryConst.photoQueue.size());
        assertEquals(0, galleryConst.queueCopy.size());
    }

    @Test
    public void testQueryGallery(){
        Context context= mainActivity.getActivity().getApplicationContext();
        Gallery galleryConst = new Gallery(context);
        assertEquals(galleryConst.queryGallery(context.getContentResolver()), 10);
        assertEquals(galleryConst.dateList.size(), 10);
        assertEquals(galleryConst.uriList.size(), 10);
        assertEquals(galleryConst.longList.size(), 10);
        assertEquals(galleryConst.latList.size(), 10);
        assertEquals(galleryConst.queryCall, 1);
    }

    @Test
    public void testFillQueue(){
        Context context= mainActivity.getActivity().getApplicationContext();
        Gallery galleryConst = new Gallery(context);
        assertEquals(galleryConst.photoQueue.size(), 10);
        assertEquals(galleryConst.queueCopy.size(), 10);
    }

   //tests for updateQueue are log statements

    @Test
    @TargetApi(24)
    public void testConvertToPQ(){
        Context context= mainActivity.getActivity().getApplicationContext();
        Gallery galleryConst = new Gallery(context);
        Comparator<Photo> photoComparator= new PhotoComparator();
        PriorityQueue<Photo> newPQ= new PriorityQueue<Photo>(photoComparator);
        newPQ= galleryConst.convertToPQ();
        assertEquals(newPQ.size(), 10);
    }

    //tests for convertToArray are logged







}
