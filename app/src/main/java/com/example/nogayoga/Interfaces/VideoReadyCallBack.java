package com.example.nogayoga.Interfaces;

import com.example.nogayoga.Models.Video;

public interface VideoReadyCallBack {
    void videoReady(Video video);
    void error();
}
