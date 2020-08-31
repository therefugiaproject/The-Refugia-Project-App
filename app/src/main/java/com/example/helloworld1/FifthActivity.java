package com.example.helloworld1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class FifthActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address;
    Switch sw_locationupdates, sw_gps;

    String s_lat, s_lon, s_altitude, s_address;

    // Variable to remember if we are tracking location or nor
    boolean updateOn = false;

    //Location request is a config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    //Google's API for location services
    FusedLocationProviderClient fusedLocationProviderClient;

    LocationCallback locationCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        Button button = findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                goToSixthActivity();
            }

        });

        //give each UI variable a value
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        sw_locationupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);

        //set all properties of LocationRequest
        locationRequest = new LocationRequest();

        //How often does the default location check occur?
        locationRequest.setInterval(30000);

        //How often does the default location check occur when the most frequent update?
        locationRequest.setFastestInterval(5000);

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //event that is triggered whenever update interval is met
        locationCallBack = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //save the location
                updateUIValues(locationResult.getLastLocation());
            }
        };

        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_gps.isChecked()) {
                    //most accurate - use GPS
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("Using GPS sensors");
                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("Using Towers + WIFI");
                }
            }
        });

        sw_locationupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_locationupdates.isChecked()) {
                    //turn on location tracking
                    startLocationUpdates();
                } else {
                    //turn off location tracking
                    stopLocationUpdates();
                }
            }
        });

        updateGPS();
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        tv_updates.setText("Location is being tracked");
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updateGPS();
    }

    @SuppressLint("MissingPermission")
    private void stopLocationUpdates() {
        tv_updates.setText("Location is NOT being tracked");
        tv_lat.setText("Location is NOT being tracked");
        tv_lon.setText("Location is NOT being tracked");
        tv_speed.setText("Location is NOT being tracked");
        tv_address.setText("Location is NOT being tracked");
        tv_accuracy.setText("Location is NOT being tracked");
        tv_altitude.setText("Location is NOT being tracked");
        tv_sensor.setText("Location is NOT being tracked");
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case PERMISSIONS_FINE_LOCATION:
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                updateGPS();
            }
            else{
                Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        }
    }

    private void updateGPS(){
        //get permissions from the user to track GPS
        //get the current location from the fused client
        //update the UI - i.e. set all properties in their associated text view items

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(FifthActivity.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //user provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //we got permissions, put values of the location into the UI
                    updateUIValues(location);
                }
            });
        }
        else{
            //user didn't provide permission yet
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }

        }

    }

    public void updateUIValues(Location location) {
        //update all of the text view objects with a new location
        tv_lat.setText(String.valueOf(location.getLatitude()));
        s_lat = String.valueOf(location.getLatitude());
        tv_lon.setText(String.valueOf(location.getLongitude()));
        s_lon = String.valueOf(location.getLongitude());
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));

        if(location.hasAltitude()){
            tv_altitude.setText(String.valueOf(location.getAltitude()));
            s_altitude = String.valueOf(location.getAltitude());
        }
        else{
            tv_altitude.setText("Not available");
            s_altitude = "Not available";
        }

        if(location.hasSpeed()){
            tv_speed.setText(String.valueOf(location.getSpeed()));
        }
        else{
            tv_speed.setText("Not available");
        }

        Geocoder geocoder = new Geocoder(FifthActivity.this);
        try{
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            tv_address.setText(addresses.get(0).getAddressLine(0));
            s_address = String.valueOf(addresses.get(0).getAddressLine(0));
        }
        catch(Exception e){
            tv_address.setText("Unable to get street address");
            s_address = "Unable to get street address";
        }

    }

    private void goToSixthActivity() {

        Intent intent2 = getIntent();
        String myUri = intent2.getStringExtra("uri");

        Intent intent1 = new Intent(this, SixthActivity.class);
        intent1.putExtra("latitude", s_lat);
        intent1.putExtra("longitude", s_lon);
        intent1.putExtra("altitude", s_altitude);
        intent1.putExtra("address", s_address);
        intent1.putExtra("Book", "three");
        intent1.putExtra("uri", myUri);
        startActivity(intent1);
    }

    @Override
    public void onClick(View view) {

        goToSixthActivity();
    }
}