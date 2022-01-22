package com.example.nogayoga.Interfaces;

import com.example.nogayoga.Models.User;

public interface UserReadyCallBack {
    void userReady(User user);//when user details are ready from fire base
    void error();
}
