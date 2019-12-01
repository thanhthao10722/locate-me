package com.example.locateme.Util;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class MapUtil implements LocationListener {
    public Location currentLocation = null;
    private Context context;
    public MapUtil(Context context) {
        this.context = context;
        while(currentLocation == null)
        {
            loadLocation();
        }
    }
    private String getLocationProvider() {

        LocationManager locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria,true);
        boolean enabled = locationManager.isProviderEnabled(provider);
        if(enabled) return provider;
        return null;
    }
    private void loadLocation() {
        LocationManager locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        String locationProvider = this.getLocationProvider();
        if(locationProvider != null) {
            final long MIN_TIME_BW_UPDATES = 1000;
            final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
            try {
                locationManager.requestLocationUpdates(locationProvider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);
                currentLocation = locationManager.getLastKnownLocation(locationProvider);
            } catch (SecurityException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    public LatLng getLocation() {
        if(currentLocation != null) {
            return new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        }
        else
            return new LatLng(16.073605,108.150019);
    }
    public String getAddress()
    {
        String add = "";
        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            Address obj = addresses.get(0);
            String[] list = obj.getAddressLine(0).split(",");
            add = add + list[0] + ", " + list[1] + ", " + list[2] + ", " + obj.getAdminArea() + ", " + obj.getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Exception", e.getMessage());
        }
        return add;
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
