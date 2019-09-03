package com.example.a16500.socketdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.sosppeople.activity.SosActivity;
import com.example.a16500.socketdemo.utils.LoginJs;
import com.example.a16500.socketdemo.utils.StaticClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        Log.d("userr", userStyle);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String account = accEt.getText().toString().trim();
                String pwd = pwdEt.getText().toString().trim();
                Log.d("loginmsg", account + " " + pwd);
                StaticVar.username = account;
                if (userStyle.equals("sos")) {
                    onLogin(account, pwd);
                } else {
                    onLogin1(account, pwd);
                }
                break;
            case R.id.sign_up_tv:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                intent1.putExtra("user1", userStyle);
                startActivity(intent1);
                break;

            default:
                break;

        }
    }

    //求救人员登录
    protected void onLogin(String account, String password) {
        //拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(StaticClass.loginUrl + "?username=" + account + "&password=" + password).build();
        //将request封装为Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("hahahahres=: ", res);
                Gson gson = new Gson();
                LoginJs loginJs = gson.fromJson(res, new TypeToken<LoginJs>() {
                }.getType());
                int uid = loginJs.getUser().getUid();
                StaticVar.uid = uid;
                if (loginJs.getMsg().equals("登录成功!")) {
                    Intent intent = new Intent(LoginActivity.this, SosActivity.class);
                    startActivity(intent);
                    finish();
                } else if (loginJs.getMsg().equals("登录失败！")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                }
            }
        });
    }

    //救灾人员登录
    protected void onLogin1(final String account, String password) {
        //拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(StaticClass.loginUrll + "?account=" + account + "&password=" + password).build();
        //将request封装为Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("hahahahres=: ", res);
                Gson gson = new Gson();
                LoginJs loginJs = gson.fromJson(res, new TypeToken<LoginJs>() {
                }.getType());
                if (loginJs.getMsg().equals("登录成功!")) {
                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                } else if (loginJs.getMsg().equals("登录失败！")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                }
            }
        });
    }

}
