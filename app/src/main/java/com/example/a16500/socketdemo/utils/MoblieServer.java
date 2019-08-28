package com.example.a16500.socketdemo.utils;

import android.os.Handler;
import android.os.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by 16500 on 2019/7/11.
 */

//接收消息的服务器
public class MoblieServer implements Runnable{

    private ServerSocket serverSocket;
    private DataInputStream in;
    private byte[] receive;

    private Handler handler = new Handler();

    public MoblieServer() {

    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);//手机端开启的服务器端口号
            while (true){
                Socket client = serverSocket.accept();
                in = new DataInputStream(client.getInputStream());
                receive = new byte[50];
                in.read(receive);
                in.close();

                Message message = new Message();
                message.what = 1;
                message.obj = new String(receive);
                handler.sendMessage(message);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
