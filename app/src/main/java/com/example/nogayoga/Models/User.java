package com.example.nogayoga.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    public static String fullName, email;
    public static List<Event> events;
    public static int count = 0;


    public User(){

    }

    public User(String fullName, String email, List<Event> events, int count){
        this.fullName = fullName;
        this.email = email;
        this.events = events;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(){
        this.count++;
    }

    public void reduceCount(){
        this.count--;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        User.events = events;
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
        map.put("events", events);
        map.put("count", count);
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
                ", events='" + events + '\'' +
                '}';
    }

}
