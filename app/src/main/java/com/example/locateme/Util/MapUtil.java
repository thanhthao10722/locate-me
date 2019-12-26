package com.example.locateme.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class MapUtil implements LocationListener {
    public Location currentLocation;
    private Context context;
    LocationManager locationManager;
    String locationProvider;
    boolean isGPSEnabled,isNetworkEnabled,canGetLocation = false;

    private long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    public MapUtil(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationProvider = getLocationProvider();
        getLocation();
    }

    private String getLocationProvider() {
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        boolean enabled = locationManager.isProviderEnabled(provider);
        if (enabled)
            return provider;
        return null;
    }

    public LatLng getLocationLatLng() {
        if(currentLocation!=null) {
            return new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        }
        return null;
    }

    public String getAddress() {
        String add = "";
        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
        try {
        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            Address obj = addresses.get(0);
            String[] list = obj.getAddressLine(0).split(",");
            add = add + list[0] + ", " + list[1] + ", " + list[2] + ", " + obj.getAdminArea() + ", " + obj.getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }
    public String getSubAddress(){
        String add = "";
        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            Address obj = addresses.get(0);
            String[] list = obj.getAddressLine(0).split(",");
            add = add + obj.getAdminArea() + ", " + obj.getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
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
    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            locationManager = (LocationManager) this.context
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        currentLocation = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (currentLocation == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            currentLocation = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
