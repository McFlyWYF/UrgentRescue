package com.example.a16500.socketdemo.utils;

import com.example.a16500.socketdemo.bean.User;

/**
 * Created by 16500 on 2019/9/2.
 */

public class LoginJs {

    private int code;
    private String msg;
    private User user;

    public LoginJs(int code, String msg, User user) {
        this.code = code;
        this.msg = msg;
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
