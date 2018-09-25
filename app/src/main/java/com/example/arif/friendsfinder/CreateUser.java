package com.example.arif.friendsfinder;

public class CreateUser {
    public String name;
    public String email;
    public String password;
    public String code;
    public String issharing;
    public String lat;
    public String lng;
    public String imgUri;

    public CreateUser(){

    }

    public CreateUser(String name, String email, String password, String code, String issharing, String lat, String lng, String imgUri) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.issharing = issharing;
        this.lat = lat;
        this.lng = lng;
        this.imgUri = imgUri;
    }
}
