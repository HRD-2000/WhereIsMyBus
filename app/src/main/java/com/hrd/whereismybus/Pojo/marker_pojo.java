package com.hrd.whereismybus.Pojo;

public class marker_pojo {


    public Integer Stop_no;
    String stop_name,latitude,longitude;

    public Integer getStop_no() {
        return Stop_no;
    }

    public void setStop_no(Integer stop_no) {
        Stop_no = stop_no;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
