package com.example.nogayoga.Interfaces;

import com.example.nogayoga.Models.Event;

public interface EventReadyCallBack {
    void eventReady(Event event);
    void error();
}
