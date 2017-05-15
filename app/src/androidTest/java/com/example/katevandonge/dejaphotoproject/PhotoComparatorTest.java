package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.Comparator;

import static junit.framework.Assert.assertEquals;

/**
 * Created by luujfer on 5/14/17.
 */


public class PhotoComparatorTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void photoComparatorTest() {
        // testing return val of photo comparator
        PhotoComparator photoComparator = new PhotoComparator();
        Context contxt = mainActivity.getActivity().getApplicationContext();
        Photo photoA = new Photo(contxt);
        Photo photoB = new Photo(contxt);
        photoA.weight = 10;
        photoB.weight = 9;
        int ret;
        ret = photoComparator.compare(photoA, photoB);
        assertEquals(ret, -1);
        ret = photoComparator.compare(photoB, photoA);
        assertEquals(ret, 1);

    }

    @Test
    public void timeTotalTest() {
        // testing timeTotal weight breaker
        PhotoComparator photoComparator = new PhotoComparator();
        Context contxt = mainActivity.getActivity().getApplicationContext();
        Photo photoA = new Photo(contxt);
        Photo photoB = new Photo(contxt);
        photoA.weight = 10;
        photoB.weight = 10;
        photoA.timeTotal = 1000;
        photoB.timeTotal = 2000;
        int ret;
        ret = photoComparator.compare(photoA, photoB);
        assertEquals(ret, 1);
        ret = photoComparator.compare(photoB, photoA);
        assertEquals(ret, -1);
    }

    @Test
    public void testNegative(){
        PhotoComparator photoComparator = new PhotoComparator();
        Context contxt = mainActivity.getActivity().getApplicationContext();
        Photo photoA = new Photo(contxt);
        Photo photoB = new Photo(contxt);
        int ret;
        // testing negative numbers
        photoA.weight = -1;
        photoB.weight = -5;
        ret = photoComparator.compare(photoA,photoB);
        assertEquals(ret, -1);
        ret = photoComparator.compare(photoB,photoA);
        assertEquals(ret, 1);

        // testing timeTotal weight breaker
        photoB.weight = -1;
        photoA.timeTotal = 1000;
        photoB.timeTotal = 2000;
        ret = photoComparator.compare(photoA,photoB);
        assertEquals(ret, 1);
        ret = photoComparator.compare(photoB, photoA);
        assertEquals(ret, -1);

    }
}
