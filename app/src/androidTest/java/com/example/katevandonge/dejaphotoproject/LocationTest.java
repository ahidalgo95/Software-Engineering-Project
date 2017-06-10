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

    // Tests the method that keeps track of the user's location and the string of the address
   /* @Test
    public void testDisplayLocation(){

        double testLat = 36.247712;
        double testLon = -117.753385;

        DisplayLocation testDisplayLocation = new DisplayLocation(testLat, testLon);
        String address = testDisplayLocation.displayLocation();

        assertEquals("Inyo County, CA, US", address);

    }*/

    // Tests the method that gets the city where a photo was taken
   /* @Test
    public void testOther(){
        double testLat = 25.761680;
        double testLon = -80.191790;

        WriteLocation testOther = new WriteLocation();
        String neighbourhood = testOther.displayLocation(testLat, testLon);


        assertEquals("Brickell Avenue, Miami, Florida" , neighbourhood);
    }*/

    // Test if the user is in a location with no address information
    @Test
    public void testDisplayLocationNull(){
        double testLat = 34;
        double testLon = 34;

        DisplayLocation testDisplayLocation = new DisplayLocation(testLat, testLon);
        String address = testDisplayLocation.displayLocation();

        assertEquals("Unknown Location", address);

    }


    // Test if a photo has no location or was taken somewhere with no address information
    @Test
    public void testOtherNull(){
        // Test a location in the middle of the ocean
        double testLat = 34;
        double testLon = 34;

        WriteLocation testOther = new WriteLocation();
        String neighbourhood = testOther.displayLocation(testLat, testLon);

        assertEquals("Unknown Location" , neighbourhood);

        // Test a location of a photo with no location info
        testLat = 0.0;
        testLon = 0.0;

        neighbourhood = testOther.displayLocation(testLat, testLon);

        assertEquals("Unknown Location", neighbourhood);
    }




}
