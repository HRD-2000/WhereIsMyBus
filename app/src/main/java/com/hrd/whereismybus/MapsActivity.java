package com.hrd.whereismybus;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hrd.whereismybus.Adapters.StopsAdapter;
import com.hrd.whereismybus.Adapters.routesAdapter;
import com.hrd.whereismybus.Network.Internet;
import com.hrd.whereismybus.Pojo.route_pojo;
import com.hrd.whereismybus.Pojo.stops_pojo;
import com.hrd.whereismybus.directionhelpers.FetchURL;
import com.hrd.whereismybus.directionhelpers.TaskLoadedCallback;
import com.hrd.whereismybus.distancematrixhelper.GetDistancesData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    Marker marker;
    DatabaseReference databaseReference;
    Timer timer;
    int cam;
//    FloatingActionButton floatingActionButton;
    Location location1,location2;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;
    ValueAnimator polyAnimator;
    Boolean camera = true;
    Boolean alreadyexecuted = false;
    Boolean flag = false;
    String lastLat,lastLng;
    String newLat,newLng;
    private float v;
    Double vLat,vLng;
    Intent i;
    String locationv1;
    //List<route_pojo> list;
    AppCompatButton btn;
    double startLat,startLng,endLat,endLng;
    Object[] dataTransfer;
    String url_distance;

    StopsAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<stops_pojo> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

      /*  rcv = findViewById(R.id.recyclerView);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        rcv.setHasFixedSize(true);

        list = new ArrayList<>();
        list.add(new route_pojo("Name 1","+91 12345 67890","Darbar Chokdi","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_45@2x.png"));
        list.add(new route_pojo("Name 2","+91 74125 89630","Padra","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_44@2x.png"));
        list.add(new route_pojo("Name 3","+91 98745 61233","Kalol","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_35@2x.png"));
        list.add(new route_pojo("Name 4","+91 32104 56987","Waghodia","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_34@2x.png"));


        routesAdapter apt = new routesAdapter(this,list);
        rcv.setAdapter(apt);*/
        btn = findViewById(R.id.appCompatButton);
        recyclerView = findViewById(R.id.recyclerView_routes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        list.add(new stops_pojo("Darbar Chokdi",01));
        list.add(new stops_pojo("Eva Mall",02));
        list.add(new stops_pojo("Tulsidham",03));
        list.add(new stops_pojo("VIER",04));



        adapter = new StopsAdapter(MapsActivity.this,list);
        recyclerView.setAdapter(adapter);

        i = getIntent();
        locationv1 = i.getStringExtra("locationResult");

        startLat = 22.273372;
        startLng = 73.182706;
        endLat = 22.4076018;
        endLng = 73.306419;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTransfer = new Object[3];
                url_distance = getDistanceUrl(startLat,startLng,endLat,endLng);
                GetDistancesData getDistancesData = new GetDistancesData();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url_distance;
                dataTransfer[2] = new LatLng(endLat,endLng);

                getDistancesData.execute(dataTransfer);
            }
        });

 //       floatingActionButton = findViewById(R.id.floating);

        chechInternet();

  /*      floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReciveLocationFromFirebase();
                cam = 1;

                new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"),"driving");
            }
        });*/

         cam = 1;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                onLocationChanged();
            }
        } else {
            onLocationChanged();
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        place1 = new MarkerOptions().position(new LatLng(22.2674321, 73.22514831)).title("Location 1");
        //place2 = new MarkerOptions().position(new LatLng(21.2674321, 73.82514831)).title("Location 2");

    }

    public void onLocationChanged()
    {
        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            //private long time = 0;
            @Override
            public void run() {
                ReciveLocationFromFirebase();
                //time += 500;
                //Log.d("TimerExample", "Going for... " + time);

                h.postDelayed(this, 5000);
            }
        },5000);
    }

    void ReciveLocationFromFirebase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Current Location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String lat = dataSnapshot.child("latitude").getValue().toString();
                String lang = dataSnapshot.child("longitude").getValue().toString();
               // callTimer(lat,lang);
                if(flag == false){
                    Log.d("test", "onDataChange: in flag if block");
                    lastLat = lat;
                    lastLng = lang;
                    newLat = lat;
                    newLng = lang;
                    flag = true;
                }else{
                    lastLat = newLat;
                    lastLng = newLng;
                    newLat = lat;
                    newLng = lang;
                }

                startLat = Double.valueOf(lat);
                startLng = Double.valueOf(lang);



                location(lastLat,lastLng,newLat,newLng);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void place2My(MarkerOptions m2){
        place2 = m2;
        Log.d("URL", "place2My: "+place2);
    }

    void location(String pre_Lat, String pre_Lang,String nextLat, String nextLang)
    {
        Double latitude = Double.valueOf(nextLat).doubleValue();
        Double longitude = Double.valueOf(nextLang).doubleValue();

        LatLng latLng = new LatLng(longitude, latitude);
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_icon_v1));


        if (marker != null) {
            marker.setPosition(latLng);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
        }
        else {
            marker = mMap.addMarker(markerOptions);
        }


        final LatLng lastLocation = new LatLng(Double.valueOf(pre_Lat).doubleValue(), Double.valueOf(pre_Lang).doubleValue());
        final LatLng newLocation = new LatLng(Double.valueOf(nextLat).doubleValue(), Double.valueOf(nextLang).doubleValue());

        /*final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v = valueAnimator.getAnimatedFraction();
                vLat = v * newLocation.latitude + (1-v) * lastLocation.latitude;
                vLng = v * newLocation.longitude + (1-v) * lastLocation.longitude;
                LatLng newPos = new LatLng(vLat,vLng);
                marker.setPosition(newPos);
                marker.setAnchor(0.5f,0.5f);
                location1 = new Location(String.valueOf(lastLocation));
                location2 = new Location(String.valueOf(newPos));
                marker.setRotation((float) calculate_bearing(lastLocation,newPos));
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(newLocation)
                .zoom(15.5f)
                .build()));
            }
        });
        valueAnimator.start();*/



        /*for (int i = 10; i <=100 ; i++) {
            //if(i%2==0)
            //{
                markerOptions.rotation(i);
            //}
            //else {
              //  markerOptions.rotation(30);
            //}
        }*/
        Location location = new Location(String.valueOf(latLng));
        
        //markerOptions.rotation(location.getBearing());




        if(cam == 1) {
            updatecamera(latLng);
            cam = 0;
        }

        place2My(markerOptions);

        Toast.makeText(MapsActivity.this, "Latitude is: " + latitude + ", Longitude is " + longitude, Toast.LENGTH_SHORT).show();

    }

    private String getDistanceUrl(double startLat,double startLng,double endLat,double endLng) {


        StringBuilder googleDistanceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/distancematrix/json?");
        googleDistanceUrl.append("origins="+startLat+","+startLng);
        googleDistanceUrl.append("&destinations="+endLat+","+endLng);
        googleDistanceUrl.append("&key="+"AIzaSyD1oGZ67zz1tNv3OQh_UlRA_CC6tOFd7nM");

        Log.d("test", "getDistanceUrl: "+googleDistanceUrl.toString());

        return googleDistanceUrl.toString();
    }


    public double calculate_bearing(LatLng previous_latlong, LatLng current_latlong){
       /* LatLng start = new LatLng(22.273225, 73.182623 );
        LatLng end = new LatLng(22.273355, 73.182526);*/

        LatLng start = previous_latlong;
        LatLng end = current_latlong;

        Double lat1,lat2,long1,long2;

        lat1 = Math.toRadians(start.latitude);
        long1 = Math.toRadians(start.longitude);

        lat2 = Math.toRadians(end.latitude);
        long2 = Math.toRadians(end.longitude);

        double dL = (end.longitude - start.longitude);
        Log.d("test", "dL : "+dL+"\nsin(dL) :"+sin(4.38101)+","+sin(4.381010000000003));
        double temp = Math.toRadians(dL);
        Log.d("test", "sin(x): "+sin(temp));
        double X = cos(lat2) * sin(temp);
        Log.d("test", "X= "+cos(lat2)+"*"+sin(temp)+"="+X);
        Double Y = cos(lat1)*sin(lat2) - sin(lat1)*cos(lat2) * cos(temp);
        Log.d("test", "Y= "+cos(lat1)+""+sin(lat2)+" - "+sin(lat1)+""+cos(lat2)+" * " + cos(temp)+" = "+Y);
        double bearing = Math.toDegrees(Math.atan2(X,Y));
        Log.d("test", "bearing in radians : "+Math.atan2(X,Y)+"\nbearing in degree : "+bearing);

        return bearing;
    }
    public void updatecamera(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //startLocService();
                } else {
                    Toast.makeText(this, "Give me permissions", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(mMap.getCameraPosition().target)
                .zoom(16)
                .bearing(30)
                .tilt(45)
                .build()));*/

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_light));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }

        mMap.addMarker(place1);

        LatLng zoo = new LatLng(22.3115, 73.1914);
        LatLng eva = new LatLng(22.2734, 73.1888 );

        MarkerOptions zoo_marker_option = new MarkerOptions().position(zoo).title("Darbar Chowkdi");
        MarkerOptions eva_marker_option = new MarkerOptions().position(eva).title("Eva Mall");


        mMap.addMarker(eva_marker_option);
        mMap.addMarker(zoo_marker_option);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(eva));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eva, 13));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"),"driving");


    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //waypoints
        String way_points = "waypoints=|22.2734,73.1888";
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + way_points + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);

        Log.v("URL",""+url);

        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

    }

    public void chechInternet(){

        Internet internet = new Internet(MapsActivity.this);

        if(!internet.isNetworkAvailable()) {
            Toast.makeText(this,"No Internet",Toast.LENGTH_SHORT).show();
        }

    }



}

