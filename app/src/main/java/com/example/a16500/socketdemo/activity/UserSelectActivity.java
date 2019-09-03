package com.example.a16500.socketdemo.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.sosppeople.activity.SosActivity;

public class UserSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sosP, rescueP, enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userselect);

        // 得到TextView控件对象
        TextView textView = findViewById(R.id.user_app_name);
        // 将字体文件保存在assets/fonts/目录下，www.linuxidc.com创建Typeface对象
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "font/OpenSans-Regular.ttf");
        // 应用字体
        textView.setTypeface(typeFace);


        sosP = findViewById(R.id.sos_person_btn);
        rescueP = findViewById(R.id.rescue_person_btn);
        enter = findViewById(R.id.enter);

        sosP.setOnClickListener(this);
        rescueP.setOnClickListener(this);

        enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sos_person_btn:
                Intent intent = new Intent(UserSelectActivity.this, LoginActivity.class);
                intent.putExtra("user", "sos");
                startActivity(intent);
                finish();
                break;
            case R.id.rescue_person_btn:
                Intent intent1 = new Intent(UserSelectActivity.this, LoginActivity.class);
                intent1.putExtra("user", "rescue");
                startActivity(intent1);
                finish();
                break;

            case R.id.enter:
                Intent intent2 = new Intent(UserSelectActivity.this, SosActivity.class);
                startActivity(intent2);
                finish();
                break;
            default:
                break;

        }
    }
}
