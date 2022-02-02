package com.example.nogayoga.Models;

public class Video {
    private String name;
    private String duration;
    private String photoUrl;
    private String link;

    public Video(){

    }

    public Video(String name, String duration, String photoUrl, String link) {
        this.name = name;
        this.duration = duration;
        this.photoUrl = photoUrl;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
