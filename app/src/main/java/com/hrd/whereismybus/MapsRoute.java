package com.hrd.whereismybus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hrd.whereismybus.Adapters.StopsAdapter;
import com.hrd.whereismybus.Adapters.routesAdapter;
import com.hrd.whereismybus.Pojo.Login_pojo;
import com.hrd.whereismybus.Pojo.marker_pojo;
import com.hrd.whereismybus.Pojo.route_pojo;
import com.hrd.whereismybus.Pojo.stops_pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsRoute extends FragmentActivity implements OnMapReadyCallback {

    RecyclerView rcv;
    List<route_pojo> list;
    List<marker_pojo> model;
    LatLng userLatLng;
    String routes_url;
    String result,result1;
    String driver_n_routes_url,urlForMarkers;
    String header;
    LoadingWithAnim loading1,loading2;
    private GoogleMap mMap;
    MarkerOptions sydney_marker_option, eva_marker_option, zoo_marker_option;
    FloatingActionButton userLocationBtn;
    public static final int REQUEST_CHECK_SETTING = 101;
    public static final int REQUEST_CHECK_SETTING_1 = 102;
    private String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_route);

        getPermission();

        loading1 = new LoadingWithAnim(MapsRoute.this);
        loading2 = new LoadingWithAnim(MapsRoute.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        userLocationBtn = findViewById(R.id.user_floating);
        rcv = findViewById(R.id.recyclerView);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        rcv.setHasFixedSize(true);

        header = getString(R.string.header);
        routes_url = header + "user_login.php";

        driver_n_routes_url = header + "driver_n_routes.php";
        urlForMarkers = header + "stop_markers.php";
        new retrieve().execute();
        new retrieve_marker().execute();



        //list = new ArrayList<>();
        //list.add(new route_pojo("Name 1", "+91 12345 67890", "Darbar Chokdi", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_45@2x.png"));
        //list.add(new route_pojo("Name 2", "+91 74125 89630", "Padra", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_44@2x.png"));
        //list.add(new route_pojo("Name 3", "+91 98745 61233", "Kalol", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_35@2x.png"));
        //list.add(new route_pojo("Name 4", "+91 32104 56987", "Waghodia", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_34@2x.png"));

        //routesAdapter apt = new routesAdapter(this, list);
        //rcv.setAdapter(apt);

        userLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,16));
            }
        });


        /*for (int i = 0; i < model.size() ; i++) {

            LatLng latLng = new LatLng(Double.parseDouble(String.valueOf(model.get(i))), Double.parseDouble(String.valueOf(model.get(i))));
            mMap.addMarker(new MarkerOptions().position(latLng).title(String.valueOf(model.get(i))));
        }*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng zoo = new LatLng(22.3115, 73.1914);
        LatLng eva = new LatLng(22.2734, 73.1888);

        sydney_marker_option = new MarkerOptions().position(sydney).title("Marker in Sydney");
        zoo_marker_option = new MarkerOptions().position(zoo).title("Marker at M.S University");
        eva_marker_option = new MarkerOptions().position(eva).title("Marker at Eva Mall");

        mMap.addMarker(eva_marker_option);
        mMap.addMarker(zoo_marker_option);

        MarkerOptions m1, m2, m3, m4;

        m1 = new MarkerOptions().position(new LatLng(22.273372, 73.182706)).title("Darbar Chowkdi");
     //   m2 = new MarkerOptions().position(new LatLng(22.272672, 73.187759)).title("Eva The Mall");
        m3 = new MarkerOptions().position(new LatLng(22.270184, 73.196796)).title("Tulsidham Circle");
        m4 = new MarkerOptions().position(new LatLng(22.4076018, 73.306419)).title("VIER");

        /*pos1 = new LatLng(22.273372,73.182706 );
        pos2 = new LatLng(22.272672,73.187759);
        pos3 = new LatLng(22.273372,73.182706 );
        pos4 = new LatLng(22.272672,73.187759);

        mMap.addMarker(m1);
        //mMap.addMarker(m2);
        mMap.addMarker(m3);
        mMap.addMarker(m4);*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,16));
            }
        });

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(eva));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eva, 13));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public class retrieve_marker extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading2.startLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try
            {
                JsonParser o = new JsonParser();
                result1 = o.insert(urlForMarkers);
                model = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(result1);
                JSONArray jsonArray = jsonObject.getJSONArray("res");

                Log.v("test",""+result1);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                    marker_pojo p = new marker_pojo();

                    p.setStop_name(jsonObject11.getString("stop_name"));
                    p.setLatitude(jsonObject11.getString("latitude"));
                    p.setLongitude(jsonObject11.getString("longitude"));

                    //LatLng latLng = new LatLng(Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude()));
                    //mMap.addMarker(new MarkerOptions().position(latLng).title(p.getStop_name()));

                    Log.d("test", "doInBackground: "+p.toString());
                    model.add(p);

                    //Log.d("test", "marker_model: "+model.toString());

                }




            }
            catch ( JSONException e)
            {
                e.printStackTrace();
                //  Toast.makeText(Login.this, "Please check your Internet Connection and Retry", Toast.LENGTH_LONG).show();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            loading2.dismissDialog();

            for (int i = 0; i < model.size() ; i++) {
                Log.d("test", "marker_list: "+model.get(i).getLongitude());
                LatLng latLng = new LatLng(Double.parseDouble(model.get(i).getLatitude()), Double.parseDouble(model.get(i).getLongitude()));
                mMap.addMarker(new MarkerOptions().position(latLng).title(model.get(i).getStop_name()));
            }
        }


    }

    public class retrieve extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading1.startLoadingDialog();
         //   loading1Dialog.startLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                JsonParser o = new JsonParser();
                result = o.insert(driver_n_routes_url);
                list = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("res");

                Log.v("test",""+result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                    route_pojo p = new route_pojo();

                    p.setRoute_id(jsonObject11.getInt("driver_id"));
                    p.setName(jsonObject11.getString("driver_name"));
                    p.setPhone_no(jsonObject11.getString("driver_phone_no"));
                    p.setS_location(jsonObject11.getString("driver_start_loc"));
                    p.setE_location(jsonObject11.getString("driver_end_loc"));
                    p.setProfile(jsonObject11.getString("driver_profile_pic"));

                    Log.d("test", "doInBackground: "+p.toString());
                    list.add(p);

                }
            }
            catch ( JSONException e)
            {
                e.printStackTrace();
                //  Toast.makeText(Login.this, "Please check your Internet Connection and Retry", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            loading1.dismissDialog();
            routesAdapter apt = new routesAdapter(MapsRoute.this,list);
            rcv.setAdapter(apt);


        }
    }

    private void getPermission() {

        Toast.makeText(this, "In getPermission method", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permission,REQUEST_CHECK_SETTING);
            }else {
                Toast.makeText(this, "Permission Granted already 1", Toast.LENGTH_SHORT).show();
                getTurnOnGps();
                //startActivity(new Intent(Home.this,MapsActivity.class));
            }
        }else {
            Toast.makeText(this, "Permission Granted already 2", Toast.LENGTH_SHORT).show();
            getTurnOnGps();
            //startActivity(new Intent(Home.this,MapsActivity.class));

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
                    Toast.makeText(MapsRoute.this, "GPS is On!!", Toast.LENGTH_SHORT).show();
                    Log.d("test", "onComplete: GPS is on");
                    //startActivity(new Intent(home.this,MapsActivity.class));
                } catch (ApiException e) {
                    switch (e.getStatusCode()){

                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            try {
                                resolvableApiException.startResolutionForResult(MapsRoute.this,REQUEST_CHECK_SETTING_1);
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
                    //startActivity(new Intent(home.this,MapsActivity.class));
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "GPS is required to be turned on", Toast.LENGTH_SHORT).show();
                    Log.d("test", "onActivityResult: GPS is rquired to be turned on");
            }

        }
    }
}