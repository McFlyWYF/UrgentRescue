package com.example.a16500.socketdemo.sosppeople.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a16500.socketdemo.activity.StaticVar;
import com.example.a16500.socketdemo.activity.WeatherSearchActivity;
import com.example.a16500.socketdemo.utils.MoblieServer;
import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.utils.SendAsyncTask;
import com.example.a16500.socketdemo.utils.SosJs;
import com.example.a16500.socketdemo.utils.StaticClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 16500 on 2019/8/28.
 */

public class SosFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private TextView send_tv, receive_tv;
    private Button send, query_weather;

    private CheckBox foods, water, medical, life_thing, urgent_thing;

    private Map<String, String> info = new HashMap<String, String>();

    private int uid;

    private int message;

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
                    receive_tv.setText("求救信息是：" + msg.obj + "  ");
                    Toast.makeText(getActivity(), "求救信息是：" + msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                StringBuilder builder = new StringBuilder();
                StringBuilder builder1 = new StringBuilder();

                if (info.size() == 0) {
                    send_tv.setText("没有需要的物资！！！");
                    Toast.makeText(getActivity(), "没有需要的物资", Toast.LENGTH_SHORT).show();
                } else {
                    for (String key : info.keySet()) {
                        builder.append(info.get(key));
                        Log.d("info", String.valueOf(info.keySet()));

                        if (info.get(key).equals("食物、")) {
                            builder1.append(1);
                        }
                        if (info.get(key).equals("水、")) {
                            builder1.append(2);
                        }
                        if (info.get(key).equals("医疗、")) {
                            builder1.append(3);
                        }
                        if (info.get(key).equals("生活用品、")) {
                            builder1.append(4);
                        }
                        if (info.get(key).equals("紧急事件、")) {
                            builder1.append(5);
                        }
                    }
                    send_tv.setText(builder);

                    Log.d("info", String.valueOf(builder1));

                    uid = StaticVar.uid;

                    DecimalFormat df = new DecimalFormat("0.000000");
                    double lat = Double.parseDouble(df.format(StaticVar.lat));
                    double lgtt = Double.parseDouble(df.format(StaticVar.lgtt));

                    Log.d("uid", String.valueOf(uid));
                    Log.d("lgtt", String.valueOf(lgtt));
                    Log.d("lat", String.valueOf(lat));

                    message = Integer.parseInt(String.valueOf(builder1));
                    onSend(uid, message, lgtt, lat);
                    Toast.makeText(getActivity(), "所需要的物资是：" + builder, Toast.LENGTH_SHORT).show();
                }

                String str = String.valueOf(message);
                char[] strChar = str.toCharArray();
                String result = "";
                for (int i = 0; i < strChar.length; i++) {
                    result += Integer.toHexString(strChar[i]) + "";
                }
                Log.d("dataBinary",result);
                new SendAsyncTask().execute(result);
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
                }
                break;

            case R.id.water_cb:
                if (b) {
                    info.put("water", "水、");
                } else {
                    info.remove("water");
                }
                break;

            case R.id.medical_cb:
                if (b) {
                    info.put("medical", "医疗、");
                } else {
                    info.remove("medical");
                }
                break;

            case R.id.life_thing_cb:
                if (b) {
                    info.put("life", "生活用品、");
                } else {
                    info.remove("life");
                }
                break;

            case R.id.urgent_thing:
                if (b) {
                    info.put("thing", "紧急事件、");
                } else {
                    info.remove("thing");
                }
                break;

            default:
                break;

        }
    }

    protected void onSend(int uid, int message, double longitude, double latitude) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        Request request = builder.get().url(StaticClass.sendMsgUrl + "?uid=" + uid + "&message=" + message + "&longitude=" + longitude + "&latitude=" + latitude).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("res=: ", res);
                Gson gson = new Gson();
                SosJs sosJs = gson.fromJson(res, new TypeToken<SosJs>() {

                }.getType());
                if (sosJs.getCode() == 1) {

                } else if (sosJs.getCode() == 0) {

                }
            }
        });

    }
}
