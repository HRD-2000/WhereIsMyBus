package com.hrd.whereismybus.Pojo;

import android.content.Intent;

public class route_pojo {

    String Name,phone_no,s_location,e_location,profile;
    Integer Route_id;

    public route_pojo(String name, String phone_no, String s_location, String e_location, String profile) {
        Name = name;
        this.phone_no = phone_no;
        this.s_location = s_location;
        this.e_location = e_location;
        this.profile = profile;
    }

    public Integer getRoute_id() {
        return Route_id;
    }

    public void setRoute_id(Integer route_id) {
        Route_id = route_id;
    }

    public route_pojo() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getS_location() {
        return s_location;
    }

    public void setS_location(String s_location) {
        this.s_location = s_location;
    }

    public String getE_location() {
        return e_location;
    }

    public void setE_location(String e_location) {
        this.e_location = e_location;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
