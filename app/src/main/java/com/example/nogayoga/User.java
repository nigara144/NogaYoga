package com.example.nogayoga;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class User {

    public static String fullName, email;

    public User(){

    }

    public User(String fullName, String email){
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String toJson(){
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("fullName",fullName);
        map.put("email",email);
        String mapsData = gson.toJson(map);
        return mapsData;
    }

    public static User fromJson(String mapsData){
        Gson gson = new Gson();
        Log.d("MapData", mapsData);
        return gson.fromJson(mapsData, User.class);
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
