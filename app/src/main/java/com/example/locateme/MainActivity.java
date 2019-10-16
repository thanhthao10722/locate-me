package com.example.locateme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import helper.MyDB;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_INTERNET = 486;
    private static final int MY_PERMISSION_REQUEST_ACLOCATION = 526;
    private static final int MY_PERMISSION_REQUEST_ANLOCATION = 425;
    private static final int MY_PERMISSION_REQUEST_AFLOCATION = 771;
    public MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
        requirePermission();
        db.writeNewUser();


    }
    public void moveToLogin(View v) {
        Intent login = new Intent(this,LoginActivity.class);
        this.startActivity(login);
    }
    public void moveToMap(View v) {
        Intent map = new Intent(this,MapActivity.class);
        this.startActivity(map);
    }
    public void backButton(View v) {
        finish();
    }
    public void requirePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.INTERNET},MY_PERMISSION_REQUEST_INTERNET);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSION_REQUEST_ACLOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_AFLOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_NETWORK_STATE},MY_PERMISSION_REQUEST_ANLOCATION);
        }

    }
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_INTERNET: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
            }
            case MY_PERMISSION_REQUEST_ACLOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
            }
            case MY_PERMISSION_REQUEST_AFLOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
            }
            case MY_PERMISSION_REQUEST_ANLOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
            }
        }
    }
}
