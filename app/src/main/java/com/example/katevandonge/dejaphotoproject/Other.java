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

public class Other {
        String city;

        public Other(){

        }

        public String displayLocation(double lat, double lon) {

            // making url request
            try {
                URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
                        + lat + "," + lon + "&sensor=true");
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
                JSONObject rec = results.getJSONObject(0);
                JSONArray address_components=rec.getJSONArray("address_components");
                String formatted_address = "";

                JSONObject recOne = address_components.getJSONObject(2);
                city = recOne.getString("short_name");
                return city;

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



