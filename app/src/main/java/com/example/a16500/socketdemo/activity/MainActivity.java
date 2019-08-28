package com.example.a16500.socketdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.utils.MoblieServer;
import com.example.a16500.socketdemo.utils.SendAsyncTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView send_tv, receive_tv;
    private Button send, map;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //开启服务器
        MoblieServer moblieServer = new MoblieServer();
        moblieServer.setHandler(handler);
        new Thread(moblieServer).start();
    }

    private void initView() {

        send_tv = findViewById(R.id.send_tv);
        receive_tv = findViewById(R.id.receive_tv);
        send = findViewById(R.id.send_btn);
        content = findViewById(R.id.send_content_et);
        map = findViewById(R.id.map_btn);

        content.setOnClickListener(this);
        send.setOnClickListener(this);
        map.setOnClickListener(this);

        receive_tv.setMovementMethod(new ScrollingMovementMethod());

        int line = receive_tv.getLineCount();
        if (line > 3) {//超出屏幕自动滚动显示(3是当前页面显示的最大行数)
            int offset = receive_tv.getLineCount() * receive_tv.getLineHeight();
            receive_tv.scrollTo(0, offset - receive_tv.getHeight() + receive_tv.getLineHeight());
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                String str = content.getText().toString().trim();
                new SendAsyncTask().execute(str);
                send_tv.setText("发送的信息是：" + str);
                break;


            case R.id.map_btn:
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    receive_tv.append("wifi发送的数据是：" + msg.obj + "  ");
                    Toast.makeText(MainActivity.this, "接收到的信息是：" + msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
