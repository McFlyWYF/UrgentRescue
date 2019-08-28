package com.example.a16500.socketdemo.sosppeople.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a16500.socketdemo.activity.WeatherSearchActivity;
import com.example.a16500.socketdemo.utils.MoblieServer;
import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.utils.SendAsyncTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 16500 on 2019/8/28.
 */

public class SosFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private TextView send_tv, receive_tv;
    private Button send, query_weather;

    private CheckBox foods, water, medical, life_thing, urgent_thing;

    private Map<String, String> info = new HashMap<String, String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sos_fragment, container, false);

        //开启服务器
        MoblieServer moblieServer = new MoblieServer();
        moblieServer.setHandler(handler);
        new Thread(moblieServer).start();

        send_tv = view.findViewById(R.id.send_tv);
        receive_tv = view.findViewById(R.id.receive_tv);
        send = view.findViewById(R.id.send_btn);
        query_weather = view.findViewById(R.id.query_weather);

        foods = view.findViewById(R.id.food_cb);
        water = view.findViewById(R.id.water_cb);
        medical = view.findViewById(R.id.medical_cb);
        life_thing = view.findViewById(R.id.life_thing_cb);
        urgent_thing = view.findViewById(R.id.urgent_thing);

        foods.setOnCheckedChangeListener(this);
        water.setOnCheckedChangeListener(this);
        medical.setOnCheckedChangeListener(this);
        life_thing.setOnCheckedChangeListener(this);
        urgent_thing.setOnCheckedChangeListener(this);

        send.setOnClickListener(this);
        query_weather.setOnClickListener(this);
//        map.setOnClickListener(this);

        receive_tv.setMovementMethod(new ScrollingMovementMethod());

        int line = receive_tv.getLineCount();
        if (line > 3) {//超出屏幕自动滚动显示(3是当前页面显示的最大行数)
            int offset = receive_tv.getLineCount() * receive_tv.getLineHeight();
            receive_tv.scrollTo(0, offset - receive_tv.getHeight() + receive_tv.getLineHeight());
        }

        return view;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    receive_tv.append("wifi发送的数据是：" + msg.obj + "  ");
                    Toast.makeText(getActivity(), "接收到的信息是：" + msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                StringBuilder builder = new StringBuilder();
                if (info.size() == 0) {
                    send_tv.setText("没有需要的物资！！！");
                    Toast.makeText(getActivity(), "没有需要的物资", Toast.LENGTH_SHORT).show();
                } else {
                    for (String key : info.keySet()) {
                        builder.append(info.get(key));
                    }
                    send_tv.setText(builder);
                    Toast.makeText(getActivity(), "所需要的物资是：" + builder, Toast.LENGTH_SHORT).show();

                }
                new SendAsyncTask().execute(String.valueOf(builder));
                break;

            case R.id.query_weather:
                Intent intent = new Intent(getActivity(), WeatherSearchActivity.class);
                startActivity(intent);
//                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.food_cb:
                if (b) {
                    info.put("food", "食物、");
                } else {
                    info.remove("food");
                    ;
                }
                break;

            case R.id.water_cb:
                if (b) {
                    info.put("water", "水、");
                } else {
                    info.remove("water");
                    ;
                }
                break;

            case R.id.medical_cb:
                if (b) {
                    info.put("medical", "医疗 、");
                } else {
                    info.remove("medical");
                    ;
                }
                break;

            case R.id.life_thing_cb:
                if (b) {
                    info.put("life", "生活用品、");
                } else {
                    info.remove("life");
                    ;
                }
                break;

            case R.id.urgent_thing:
                if (b) {
                    info.put("thing", "紧急事件、");
                } else {
                    info.remove("thing");
                    ;
                }
                break;

            default:
                break;

        }
    }
}
