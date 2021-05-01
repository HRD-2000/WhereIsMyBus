package com.hrd.whereismybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.hrd.whereismybus.Adapters.StopsAdapter;
import com.hrd.whereismybus.Adapters.routesAdapter;
import com.hrd.whereismybus.Pojo.Login_pojo;
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

    String routes_url;
    String result;

    String header;

    private GoogleMap mMap;
    MarkerOptions sydney_marker_option, eva_marker_option, zoo_marker_option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_route);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rcv = findViewById(R.id.recyclerView);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        rcv.setHasFixedSize(true);

        header = getString(R.string.header);
        routes_url = header + "user_login.php";

        list = new ArrayList<>();
        list.add(new route_pojo("Name 1", "+91 12345 67890", "Darbar Chokdi", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_45@2x.png"));
        list.add(new route_pojo("Name 2", "+91 74125 89630", "Padra", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_44@2x.png"));
        list.add(new route_pojo("Name 3", "+91 98745 61233", "Kalol", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_35@2x.png"));
        list.add(new route_pojo("Name 4", "+91 32104 56987", "Waghodia", "VIER", "https://chromeunboxed.com/wp-content/uploads/2017/08/IDR_LOGIN_DEFAULT_USER_34@2x.png"));


        routesAdapter apt = new routesAdapter(this, list);
        rcv.setAdapter(apt);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

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
        pos4 = new LatLng(22.272672,73.187759);*/

        mMap.addMarker(m1);
        mMap.addMarker(m2);
        mMap.addMarker(m3);
        mMap.addMarker(m4);

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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(eva));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eva, 13));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public class retrieve extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

         //   loadingDialog.startLoadingDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                JsonParser o = new JsonParser();
                result = o.insert(routes_url);
                //list = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("res");

                Log.v("Login_DATA",""+result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                    route_pojo p = new route_pojo();

                    p.setS_location(jsonObject11.getString("source"));
                    p.setE_location(jsonObject11.getString("destination"));
               /*   p.setProfile(jsonObject11.getString("password"));
                    p.setPhone_no(jsonObject11.getString("password"));*/
                    p.setName(jsonObject11.getString("routes_name"));

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

            routesAdapter apt = new routesAdapter(MapsRoute.this,list);
            rcv.setAdapter(apt);


        }
    }

}