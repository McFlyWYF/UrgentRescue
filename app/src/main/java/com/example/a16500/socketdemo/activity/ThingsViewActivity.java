package com.example.a16500.socketdemo.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.a16500.socketdemo.R;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import rorbin.q.radarview.RadarData;
import rorbin.q.radarview.RadarView;
import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;

public class ThingsViewActivity extends AppCompatActivity {

    RadarView mRadarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_view);

        getSupportActionBar().hide();

        mRadarView = findViewById(R.id.radarView);

        radarView();
    }

    public void radarView(){
        getSupportActionBar().hide();

        mRadarView.setEmptyHint("无数据");

        List<Integer> layerColor = new ArrayList<>();
        Collections.addAll(layerColor, 0x3300bcd4, 0x3303a9f4, 0x335677fc, 0x333f51b5, 0x33673ab7);
        mRadarView.setLayerColor(layerColor);

        List<String> vertexText = new ArrayList<>();
        Collections.addAll(vertexText, "食物", "紧急事件", "医疗", "生活用品", "水");
        mRadarView.setVertexText(vertexText);

        List<Integer> res = new ArrayList<>();
        Collections.addAll(res, R.mipmap.power, R.mipmap.agile, R.mipmap.speed,
                R.mipmap.intelligence, R.mipmap.spirit);
        mRadarView.setVertexIconResid(res);

//        List<Float> values = new ArrayList<>();
//        Collections.addAll(values, 3f, 6f, 2f, 7f, 5f);
//        RadarData data = new RadarData(values);
//        mRadarView.addData(data);

        List<Float> values2 = new ArrayList<>();
        Collections.addAll(values2, 7f, 1f, 4f, 2f, 8f);
        RadarData data2 = new RadarData(values2);
        data2.setValueTextEnable(true);
        data2.setVauleTextColor(Color.WHITE);
        data2.setValueTextSize(dp2px(10));
        data2.setLineWidth(dp2px(1));
        mRadarView.addData(data2);
    }

    private float dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
