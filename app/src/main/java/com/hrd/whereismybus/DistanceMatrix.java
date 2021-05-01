package com.hrd.whereismybus;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.hrd.whereismybus.distancematrixhelper.GetDistancesData;

public class DistanceMatrix {

    GoogleMap mMap;
    double startLat,startLng,endLat,endLng;
    Object[] dataTransfer;
    String url;



    public DistanceMatrix(GoogleMap mMap, double startLat, double startLng, double endLat, double endLng) {
        this.mMap = mMap;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;

        dataTransfer = new Object[3];
        url = getDistanceUrl(startLat,startLng,endLat,endLng);
        GetDistancesData  getDistancesData = new GetDistancesData();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = new LatLng(endLat,endLng);

        getDistancesData.execute(dataTransfer);

    }

    private String getDistanceUrl(double startLat,double startLng,double endLat,double endLng) {


        StringBuilder googleDistanceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/distancematrix/json?");
        googleDistanceUrl.append("origins="+startLat+","+startLng);
        googleDistanceUrl.append("&destinations="+endLat+","+endLng);
        googleDistanceUrl.append("&key="+"AIzaSyD1oGZ67zz1tNv3OQh_UlRA_CC6tOFd7nM");

        return googleDistanceUrl.toString();
    }


}
