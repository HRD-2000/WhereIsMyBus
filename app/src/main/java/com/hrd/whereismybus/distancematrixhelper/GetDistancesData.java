package com.hrd.whereismybus.distancematrixhelper;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.hrd.whereismybus.R;
import com.hrd.whereismybus.directionhelpers.DataParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class GetDistancesData extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    String googleDistanceData;
    String duration,distance;
    LatLng latLng;

    Context context;

    TextView distance_tv,duration_tv;


    @Override
    protected String doInBackground(Object... objects) {
       mMap = (GoogleMap) objects[0];
       url = (String) objects[1];
       latLng = (LatLng) objects[2];

        try {
            // Fetching the data from web service
            googleDistanceData = downloadUrl(url);
            Log.d("mylog", "Background task data " + googleDistanceData.toString());
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return googleDistanceData;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        HashMap<String, String> directionList = null;
        DataParser parser = new DataParser();
        directionList = parser.parseDirection(s);
        duration = directionList.get("duration");
        distance = directionList.get("distance");

        Log.d("test", "distance :"+distance+"\n duration :"+duration);

        distance_tv = ((Activity)context).findViewById(R.id.distance_tv);
        duration_tv = ((Activity)context).findViewById(R.id.time_tv);

        distance_tv.setText(distance);
        duration_tv.setText(duration);

    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("mylog", "Downloaded URL: " + data.toString());
            br.close();
        } catch (Exception e) {
            Log.d("mylog", "Exception downloading URL: " + e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
