package com.example.a16500.socketdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a16500.socketdemo.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    TextView login_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login_tv = findViewById(R.id.login_up_tv);
        login_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_up_tv:
                finish();
                break;
        }
    }
}
