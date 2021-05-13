package com.hrd.whereismybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HelperActivity extends AppCompatActivity {

    public static final int REQUEST_CHECK_SETTING = 101;
    public static final int REQUEST_CHECK_SETTING_1 = 102;
    private String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        getPermission();
    }

    private void getPermission() {

        Toast.makeText(this, "In getPermission method", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permission,REQUEST_CHECK_SETTING);
            }else {
                Toast.makeText(this, "Permission Granted already 1", Toast.LENGTH_SHORT).show();
                getTurnOnGps();
                startActivity(new Intent(HelperActivity.this,MapsRoute.class));
                finish();
            }
        }else {
            Toast.makeText(this, "Permission Granted already 2", Toast.LENGTH_SHORT).show();
            getTurnOnGps();
            startActivity(new Intent(HelperActivity.this,MapsRoute.class));
            finish();

        }
    }
    private void getTurnOnGps() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response =task.getResult(ApiException.class);
                    Toast.makeText(HelperActivity.this, "GPS is On!!", Toast.LENGTH_SHORT).show();
                    Log.d("test", "onComplete: GPS is on");
                    startActivity(new Intent(HelperActivity.this,MapsRoute.class));
                    finish();
                } catch (ApiException e) {
                    switch (e.getStatusCode()){

                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            try {
                                resolvableApiException.startResolutionForResult(HelperActivity.this,REQUEST_CHECK_SETTING_1);
                            } catch (IntentSender.SendIntentException sendIntentException) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CHECK_SETTING){
            Log.d("test", "onRequestPermissionsResult: 2 invoked");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("test", "onRequestPermissionsResult: 3 invoked");
                getTurnOnGps();
            }else {
                Log.d("test", "onRequestPermissionsResult: 4 invoked");
                getTurnOnGps();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTING_1){

            switch (resultCode){

                case Activity.RESULT_OK:
                    Toast.makeText(getApplicationContext(),"GPS is Turned ON",Toast.LENGTH_SHORT).show();
                    Log.d("test", "onActivityResult: GPS is Turned On");
                    startActivity(new Intent(HelperActivity.this,MapsRoute.class));
                    finish();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS is required to be turned on", Toast.LENGTH_SHORT).show();
                    Log.d("test", "onActivityResult: GPS is rquired to be turned on");
            }

        }
    }
}