package com.avijit.jobseeker.retroclient.com.avijit.jobseeker.retroclient.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Avijit on 11/28/2019.
 */
//id	name	email	mobile	type	profile	fcm_id	points	ip_address	status	date_registered

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("type")
    private String type;

    @SerializedName("profile")
    private String profile;

    @SerializedName("fcm_id")
    private String fcm_id;

    @SerializedName("points")
    private String points;

    @SerializedName("ip_address")
    private String ip_address;

    @SerializedName("status")
    private String status;

    @SerializedName("date_registered")
    private String date_registered;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_registered() {
        return date_registered;
    }

    public void setDate_registered(String date_registered) {
        this.date_registered = date_registered;
    }
}
