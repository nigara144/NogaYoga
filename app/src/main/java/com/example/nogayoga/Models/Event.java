package com.example.nogayoga.Models;

import android.util.Log;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {
    private String typeName;
    private String time;
    private String uid;
    private Boolean isJoined;
    private List<String> userUids;
    private String eventDate;

    public Event(){

    }

    public Event(String typeName, String time, String uid, List<String> userUids, Boolean isJoined) {
        this.typeName = typeName;
        this.time = time;
        this.uid = uid;
        this.isJoined = isJoined;
        this.userUids = userUids;
    }

    public Event(String typeName, String time, String uid, String eventDate) {
        this.typeName = typeName;
        this.time = time;
        this.uid = uid;
        this.eventDate = eventDate;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public List<String> getUserUids() {
        return userUids;
    }

    public void setUserUids(List<String> userUids) {
        this.userUids = userUids;
    }

    public Boolean getJoined() {
        return isJoined;
    }

    public void setJoined(Boolean joined) {
        isJoined = joined;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, Object> toJson(String date){
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("typeName",typeName);
        map.put("time",time);
        map.put("uid",uid);
        map.put("date", date);
//        String mapsData = gson.toJson(map);
        return map;
    }

    public Event fromJson(String mapsData){
        Gson gson = new Gson();
        Log.d("MapData", mapsData);
        return gson.fromJson(mapsData, Event.class);
    }

    @Override
    public String toString() {
        return "Event{" +
                "type='" + typeName + '\'' +
                ", time=" + time +
                ", uid=" + uid +
                ", isJoined=" + isJoined +
                ", userUids=" + userUids +
                '}';
    }
}
