package com.example.a16500.socketdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.utils.RegisterJs;
import com.example.a16500.socketdemo.utils.StaticClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextView login_tv;
    EditText accountEdt, pwdEdt;
    Button registerBtn;

    OkHttpClient client;

    private String userStyle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login_tv = findViewById(R.id.login_up_tv);
        accountEdt = findViewById(R.id.reg_account_et);
        pwdEdt = findViewById(R.id.reg_pwd_et);
        registerBtn = findViewById(R.id.register_btn);

        login_tv.setOnClickListener(this);
        accountEdt.setOnClickListener(this);
        pwdEdt.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_up_tv:
                finish();
                break;

            case R.id.register_btn:

                if (accountEdt.getText().toString().trim().isEmpty() || pwdEdt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入完整的信息", Toast.LENGTH_SHORT).show();
                    accountEdt.setText("");
                    pwdEdt.setText("");
                } else {
                    String username = accountEdt.getText().toString().trim();
                    String pwd = pwdEdt.getText().toString().trim();

                    Intent intent1 = getIntent();
                    userStyle1 = intent1.getStringExtra("user1");
                    Log.d("userregister",userStyle1);
                    if (userStyle1.equals("sos")){
                        onSignup(username,pwd);
                    }else {
                        onSignup1(username,pwd);

                    }

                }

        }
    }

    //求救人员注册
    protected void onSignup(String username, String password) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        Request request = builder.get().url(StaticClass.registerUrl + "?username=" + username + "&password=" + password).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();

                Gson gson = new Gson();
                RegisterJs registerJs = gson.fromJson(res, new TypeToken<RegisterJs>() {
                }.getType());
                if (registerJs.getCode() == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (registerJs.getCode() == 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "该账号已经存在，请重新注册！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    //救灾人员注册
    protected void onSignup1(String username, String password) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        Request request = builder.get().url(StaticClass.registerUrll + "?account=" + username + "&password=" + password).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();

                Gson gson = new Gson();
                RegisterJs registerJs = gson.fromJson(res, new TypeToken<RegisterJs>() {
                }.getType());
                if (registerJs.getCode() == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (registerJs.getCode() == 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "该账号已经存在，请重新注册！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
