package com.example.a16500.socketdemo.utils;

/**
 * Created by 16500 on 2019/9/2.
 */

public class SosJs {

    private int code;
    private String msg;

    public SosJs(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
