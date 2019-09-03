package com.example.a16500.socketdemo.utils;

/**
 * Created by 16500 on 2019/9/2.
 */

public class StaticClass {

    private static String ip = "10.0.116.34";

    //求救人员注册url
    public static String registerUrl = "http://" + ip + ":8080/user/register";

    //救灾人员注册url
    public static String registerUrll = "http://" + ip + ":8080/rescue/register";

    //求救人员登录url
    public static String loginUrl = "http://" + ip + ":8080/user/login";

    //救灾人员登录url
    public static String loginUrll = "http://" + ip + ":8080/rescue/login";

    //发送求救信息
    public static String sendMsgUrl = "http://" + ip + ":8080/sos/sendSos";

    //获取避难场所
    public static String placeUrl = "http://" + ip + ":8080/shelter/queryShelter";

    //获取求救者位置信息
    public static String locationUrl = "http://" + ip + ":8080/sos/queryAllSos";

}
