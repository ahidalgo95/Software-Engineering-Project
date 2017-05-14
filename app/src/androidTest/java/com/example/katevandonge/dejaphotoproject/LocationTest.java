package com.example.katevandonge.dejaphotoproject;


import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.ServiceTestRule;
import android.util.Log;
import android.widget.TextView;
import android.content.Context;


import com.example.katevandonge.dejaphotoproject.*;


import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Peter on 5/12/2017.
 */

/*
 * Note: TrackLocation and UserLocation are not easily testable and are being logged
 */

public class LocationTest {

    @Rule
    public ActivityTestRule<MainActivity>  mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void testDisplayLocation(){

        double testLat = 36.247712;
        double testLon = -117.753385;

        DisplayLocation testDisplayLocation = new DisplayLocation(testLat, testLon);
        String value = testDisplayLocation.displayLocation();
        Log.i("LocationTest" ,value);
        assertEquals("Inyo County, CA, US", value);

    }


}
