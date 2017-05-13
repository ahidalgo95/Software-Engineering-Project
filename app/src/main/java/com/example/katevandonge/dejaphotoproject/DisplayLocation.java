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




public class DisplayLocation {

    TrackLocation myLocation;

    public DisplayLocation(TrackLocation mTrackLocation){
        myLocation = mTrackLocation;
    }

    public void setLocation(TrackLocation newLocation){
        myLocation = newLocation;
    }

    public String displayLocation() {

        // making url request
        try {
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="
                    + myLocation.getLatitude() + "," + myLocation.getLongitude() + "&sensor=true");
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
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                out+=output;
            }
            // Converting Json formatted string into JSON object
            JSONObject json = (JSONObject) JSONSerializer.toJSON(out);
            JSONArray results=json.getJSONArray("results");
            if(results == null || results.size() < 1 ){
                return "";
            }
            JSONObject rec = results.getJSONObject(0);
            JSONArray address_components=rec.getJSONArray("address_components");
            String formatted_address = "";
            for(int i=1;i<address_components.size();i++){
                JSONObject rec1 = address_components.getJSONObject(i);
                JSONArray types=rec1.getJSONArray("types");
                String comp=types.getString(0);

                if(i < address_components.size() - 1) {
                    formatted_address = formatted_address + rec1.getString("short_name") + ", ";
                }else {
                    formatted_address = formatted_address + rec1.getString("short_name");
                }

                /*if(comp.equals("locality")){
                    System.out.println("city ————-"+rec1.getString("short_name"));
                }
                else if(comp.equals("country")){
                    System.out.println("country ———-"+rec1.getString("short_name"));
                }*/
            }
            //Log.i("DisplayLocation", rec.getString("formatted_address"));
            conn.disconnect();
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


