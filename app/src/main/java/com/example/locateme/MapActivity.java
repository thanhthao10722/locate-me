package com.example.locateme;

import android.app.ProgressDialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements LocationListener {

    private GoogleMap myMap;
    private ProgressDialog myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        //progress bar
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Map Loading...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);

        //show progress
        myProgress.show();

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                onMyMapReady(googleMap);
            }
    });
    }

    private void onMyMapReady(GoogleMap googleMap) {
        //retrieve Google Map object
        myMap = googleMap;
        //Google Map successfully loaded
        myMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                myProgress.dismiss();
                showMyLocationViaAnyTools();
            }
        });
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.setMyLocationEnabled(true);
    }

    private String getLocationProvider() {

        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria,true);
        boolean enabled = locationManager.isProviderEnabled(provider);
        if(enabled) return provider;
        return null;
    }

    private void showMyLocationViaAnyTools() {
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        String locationProvider = this.getLocationProvider();

        if(locationProvider != null) {
            final long MIN_TIME_BW_UPDATES = 1000;
            final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
            Location myLocation = null;
            try {
                locationManager.requestLocationUpdates(locationProvider,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,(LocationListener)this);
                myLocation = locationManager.getLastKnownLocation(locationProvider);
            }catch (SecurityException e) {
                e.printStackTrace();
                return;
            }
            if(myLocation != null) {
                LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)             // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(0)                   // Sets the tilt of the camera
                        .build();                   // Creates a CameraPosition from the builder
                myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                // Thêm Marker cho Map:
                MarkerOptions option = new MarkerOptions();
                final MarkerOptions my_location = option.title("My Location");
                option.snippet("....");
                option.position(latLng);
                Marker currentMarker = myMap.addMarker(option);
                currentMarker.showInfoWindow();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
