package com.example.a16500.socketdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a16500.socketdemo.R;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;

public class SosViewActivity extends AppCompatActivity {
    SuitLines suitLines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos_view);

        getSupportActionBar().hide();

        suitLines = findViewById(R.id.suitlines);

        List<Unit> lines = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            lines.add(new Unit(new SecureRandom().nextInt(48), i + ""));
        }
        suitLines.setLineForm(true);
        suitLines.feedWithAnim(lines);
    }
}
