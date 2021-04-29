package com.hrd.whereismybus;

public class stops_pojo {

    String stop_name;
    Integer stop_no;

    public stops_pojo(String stop_name, Integer stop_no) {
        this.stop_name = stop_name;
        this.stop_no = stop_no;
    }

    public stops_pojo() {

    }


    /*public String getLatitude() {
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
    }*/

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public Integer getStop_no() {
        return stop_no;
    }

    public void setStop_no(Integer stop_no) {
        this.stop_no = stop_no;
    }
}

