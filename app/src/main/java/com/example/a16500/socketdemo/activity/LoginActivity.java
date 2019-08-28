package com.example.a16500.socketdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.sosppeople.activity.SosActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginBtn;
    private EditText accEt, pwdEt;
    private TextView registerTv;

    public String userStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void init() {
        loginBtn = findViewById(R.id.login_btn);
        accEt = findViewById(R.id.login_account_et);
        pwdEt = findViewById(R.id.login_pwd_et);
        registerTv = findViewById(R.id.sign_up_tv);

        loginBtn.setOnClickListener(this);
        accEt.setOnClickListener(this);
        pwdEt.setOnClickListener(this);
        registerTv.setOnClickListener(this);

        Intent intentt = getIntent();
        userStyle = intentt.getStringExtra("user");
        Log.d("userr",userStyle);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:

                if (userStyle.equals("sos")){
                    Intent intent = new Intent(LoginActivity.this, SosActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }else {
                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }


            case R.id.sign_up_tv:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;

            default:
                break;

        }
    }
}
