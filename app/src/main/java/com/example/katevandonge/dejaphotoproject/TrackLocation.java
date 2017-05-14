package com.example.katevandonge.dejaphotoproject;

/**
 * Created by Peter on 5/11/2017.
 */

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

// To make this work in the emulator, you must set High Accuracy Location Settings
// and also send a location from the emulator settings to the emulator

public class TrackLocation
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Location mLastLocation;
    Context mContext;
    double mLatitude;
    double mLongitude;


    public  TrackLocation(Context context){
        mContext = context;
    }

    public TrackLocation(double lat, double lon){
        mLatitude = lat;
        mLongitude = lon;
    }

    protected void trackLocation() {
        // Create a GoogleAPIClient and buld it
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Connect the client
        mGoogleApiClient.connect();

        // Create the LocationRequest object and set its values
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3* 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        // This section of code checks if the user has High Accuracy Location Services enabled,
        // which is required for the application to work
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        Log.i("TrackLocation", "Location settings check passed");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        Log.i("TrackLocation", "High Accuracy Location settings need to be enabled");
                        Toast.makeText(mContext,"Please enable High Accuracy Location and relaunch the app", Toast.LENGTH_LONG).show();
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    public void updateLocation() {

        // Tries to get the location, has check incase user does not have location services enabled
        try{
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }catch ( SecurityException e){
            //If not enabled, then do nothing
        }
        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            //Log.i("trackLocation", mLatitude + " " + mLongitude );
        }else{
            //Log.i("trackLocation, "mLastLocation was null", Toast.LENGTH_SHORT).show();
        }
    }

    public double getLatitude(){
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude){mLongitude = longitude;}

    public void setLatitude(double latitude){mLatitude = latitude;}

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void handleNewLocation(Location location) {
        //Log.d("handleNewLocation", location.toString());
    }

    public void onLocationChanged(Location location){
        handleNewLocation(location);
    }

}
