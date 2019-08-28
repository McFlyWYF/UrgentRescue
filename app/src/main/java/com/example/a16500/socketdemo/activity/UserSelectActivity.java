package com.example.a16500.socketdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.a16500.socketdemo.R;

public class UserSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button sosP, rescueP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userselect);

        sosP = findViewById(R.id.sos_person_btn);
        rescueP = findViewById(R.id.rescue_person_btn);

        sosP.setOnClickListener(this);
        rescueP.setOnClickListener(this);
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
            default:
                break;

        }
    }
}
