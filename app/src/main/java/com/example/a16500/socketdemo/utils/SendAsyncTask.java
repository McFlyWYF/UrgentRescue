package com.example.a16500.socketdemo.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by 16500 on 2019/7/11.
 */

//异步处理类
public class SendAsyncTask extends AsyncTask<String,Void ,Void>{

    //ESP8266的IP和端口号
    private static final String IP = "192.168.4.1";
    private static final int PORT = 8080;

    private Socket client = null;
    private PrintStream out = null;

    @Override
    protected Void doInBackground(String... strings) {

        String str = strings[0];
        try{
            client = new Socket(IP,PORT);
            client.setSoTimeout(5000);
            //获取socket的输出流，发送数据到服务端
            out = new PrintStream(client.getOutputStream());
            out.print(str);
            out.flush();

            if (client == null){
                return null;
            }else {
                out.close();
                client.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
