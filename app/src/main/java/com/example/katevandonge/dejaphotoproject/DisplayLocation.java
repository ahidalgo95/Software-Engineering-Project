package com.example.katevandonge.dejaphotoproject;


/**
 * Created by Peter on 5/11/2017.
 */


import android.util.Log;

import net.sf.json.JSONSerializer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




/*
 * Display location class used for checking user's location
 */
public class DisplayLocation {

    TrackLocation myLocation;  // Current location

    // Constructor taking in TrackLocation object
    public DisplayLocation(TrackLocation mTrackLocation){
        myLocation = mTrackLocation;
    }

    // Constructor taking in doubles for values, useful for testing
    public DisplayLocation(double lat, double lon){
        myLocation = new TrackLocation(lat, lon);
    }


    // Gets the string of the location of the saved Latitude and Longitude values
    public String displayLocation() {

        // Making url request
        try {
            //Log.i("DisplayLocation" , "Passed in lat and long" + myLocation.getLatitude() +
             //", " + myLocation.getLongitude());
            Log.i("DisplayLocation", myLocation.getLatitude() + myLocation.getLongitude() + "");
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="
                    + myLocation.getLatitude() + "," + myLocation.getLongitude() + "&source=true");
            // Making connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Check for faulty HTTP
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            // Reading data's from url
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            // Output server response
            String output;
            String out="";
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                out+=output;
            }

            // Converting Json formatted string into JSON object
            JSONObject json = (JSONObject) JSONSerializer.toJSON(out);
            JSONArray results=json.getJSONArray("results");

            // Check that the url actually returned anything
            if(results == null || results.size() < 1 ){
                // If not, return empty string
                Log.i("DisplayLocation", "Didn't get good result from reverse geocoding call");
                return "Unknown Location";
            }

            JSONObject rec = results.getJSONObject(0);
            JSONArray address_components=rec.getJSONArray("address_components");

            // String to return
            String formatted_address = "";

            // Iterate through the returned values
            for(int i=1;i<address_components.size();i++){
                JSONObject rec1 = address_components.getJSONObject(i);
                JSONArray types=rec1.getJSONArray("types");
                String comp=types.getString(0);

                // Building the string to return
                if(i < address_components.size() - 1) {
                    formatted_address = formatted_address + rec1.getString("short_name") + ", ";
                }else {
                    formatted_address = formatted_address + rec1.getString("short_name");
                }
            }
            //Log.i("DisplayLocation", rec.getString("formatted_address"));

            // Disconnect the HTTP object
            conn.disconnect();

            // return the formatted current location
            return (""+formatted_address);

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }
}


