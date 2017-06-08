package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by luujfer on 5/14/17.
 */

public class PhotoJUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void constructorTest(){
        Context contxt = mainActivity.getActivity().getApplicationContext();
        Photo testPhoto = new Photo(contxt);
        assertEquals(testPhoto.karma, false);
        assertEquals(testPhoto.shown, false);
        assertEquals(testPhoto.release, false);
        assertEquals(testPhoto.context1, contxt);
        assertEquals(testPhoto.locName, "Location");
        assertEquals(testPhoto.timeTotal, 0);
    }

    @Test
    public void setDateTest(){
        Context contxt = mainActivity.getActivity().getApplicationContext();
        Photo testPhoto = new Photo(contxt);
        long testDate = (long) 1494802502 * 1000;  // SUNDAY, 15:55 05/14/2017
        testPhoto.setDate(testDate);
        assertEquals(testPhoto.dayOfWeek, "Sun");
        assertEquals(testPhoto.time, "15:55");
        assertEquals(testPhoto.date, "05/14/2017");
    }

    /* did not test for location weight factor, log statements instead
    @Test
    public void setWeightTest(){
        Context contxt = mainActivity.getActivity().getApplicationContext();
        Photo testPhoto = new Photo(contxt);
        long testDate = (long) 1494802502 * 1000;  // SUNDAY, 15:55 05/14/2017
        testPhoto.setDate(testDate);
        testPhoto.setWeight();
        assertNotEquals(testPhoto.weight, 10);
        testPhoto.release = true;
        testPhoto.setWeight();
        assertNotEquals(testPhoto.weight, -10);
        testPhoto.release = false;
        testPhoto.karma =0;
        testPhoto.setWeight();
        assertNotEquals(testPhoto.weight, 11);
    }*/


}
