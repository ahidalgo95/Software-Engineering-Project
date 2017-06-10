package com.example.katevandonge.dejaphotoproject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

/**
 * Created by katevandonge on 5/12/17.
 */

public class WriteLocation {
        String neighbourhood; // String of location where the photo was taken

        public WriteLocation(){

        }

        // Gets the string of the location of the saved Latitude and Longitude values
        public String displayLocation(double lat, double lon) {

            // making url request
            try {
                    URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng="
                            + lat + "," + lon + "&source=true");
                // making connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }
                //Log.i("displayLocation1", "I get here1");
                // Reading data's from url
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                String out="";
                while ((output = br.readLine()) != null) {
                    out+=output;
                }
                // Converting Json formatted string into JSON object
                JSONObject json = (JSONObject) JSONSerializer.toJSON(out);
                JSONArray results=json.getJSONArray("results");

                // Check that the url actually returned anything
                if(results == null || results.size() < 1 ){
                    // If not, return empty string
                    Log.i("Other", "Didn't get good result from reverse geocoding call");
                    return "Unknown Location";
                }



                // Get all results
                JSONObject rec = results.getJSONObject(0);
                JSONArray address_components=rec.getJSONArray("address_components");

                //Log.i("Other", "formatted: " + rec.getString("formatted_address"));

                neighbourhood = "";

                // Selects the parts of the address we want to display on homescreen
                for(int i=0;i<address_components.size();i++) {
                    // Get current component
                    JSONObject rec1 = address_components.getJSONObject(i);
                    JSONArray types=rec1.getJSONArray("types");
                    String comp=types.getString(0);

                    // Don't want street number
                    if(comp.equals("street_number")){
                        continue;
                    }
                    // Keep route
                    if(comp.equals(("route"))){
                        neighbourhood = neighbourhood + rec1.getString("long_name") + ", ";
                    }
                    // Keep locality
                    if(comp.equals("locality")){
                        neighbourhood = neighbourhood + rec1.getString("long_name") + ", ";
                    }
                    // Don't want administrative area level 2
                    if(comp.equals("administrative_area_level_2")){
                        //neighbourhood = neighbourhood + rec1.getString("short_name") + ", ";
                        continue;
                    }
                    // Keep administrative area level 1
                    if(comp.equals("administrative_area_level_1")){
                        neighbourhood = neighbourhood + rec1.getString("long_name");
                    }
                    // Don't want country
                    if(comp.equals("country")){
                        continue;
                    }
                    // Don't want postal code
                    if(comp.equals("postal_code")){
                        continue;
                    }

                }


                // Return the location of the photo
                //Log.i("Other", neighbourhood);
                return neighbourhood;

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



