package com.hrd.whereismybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.hrd.whereismybus.Adapters.StopsAdapter;
import com.hrd.whereismybus.Adapters.routesAdapter;
import com.hrd.whereismybus.Pojo.route_pojo;
import com.hrd.whereismybus.Pojo.stops_pojo;

import java.util.ArrayList;
import java.util.List;

public class MapsRoute extends FragmentActivity implements OnMapReadyCallback {

    RecyclerView rcv;
    List<route_pojo> list;

    private GoogleMap mMap;
    MarkerOptions sydney_marker_option,eva_marker_option,zoo_marker_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_route);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rcv = findViewById(R.id.recyclerView);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
        rcv.setHasFixedSize(true);

        list = new ArrayList<>();
        list.add(new route_pojo("Name 1","+91 12345 67890","Darbar Chokdi","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_45@2x.png"));
        list.add(new route_pojo("Name 2","+91 74125 89630","Padra","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_44@2x.png"));
        list.add(new route_pojo("Name 3","+91 98745 61233","Kalol","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_35@2x.png"));
        list.add(new route_pojo("Name 4","+91 32104 56987","Waghodia","VIER","https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_34@2x.png"));


        routesAdapter apt = new routesAdapter(this,list);
        rcv.setAdapter(apt);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng zoo = new LatLng(22.3115, 73.1914);
        LatLng eva = new LatLng(22.2734, 73.1888 );

        sydney_marker_option = new MarkerOptions().position(sydney).title("Marker in Sydney");
        zoo_marker_option = new MarkerOptions().position(zoo).title("Marker at M.S University");
        eva_marker_option = new MarkerOptions().position(eva).title("Marker at Eva Mall");

        mMap.addMarker(eva_marker_option);
        mMap.addMarker(zoo_marker_option);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(eva));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eva, 13));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }
}