package com.example.katevandonge.dejaphotoproject;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by adriancordovayquiroz on 6/9/17.
 */

public class MasterGalleryTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity= new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testConstructor(){
        Context context= mainActivity.getActivity().getApplicationContext();
        MasterGallery gallery= new MasterGallery(context);
        assertEquals(context, gallery.context);
    }


}

