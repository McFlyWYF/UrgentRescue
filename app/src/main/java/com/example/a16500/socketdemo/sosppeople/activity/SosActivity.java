package com.example.a16500.socketdemo.sosppeople.activity;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.sosppeople.fragment.MyFragment;
import com.example.a16500.socketdemo.sosppeople.fragment.SafeAreaFragment;
import com.example.a16500.socketdemo.sosppeople.fragment.SosFragment;


public class SosActivity extends AppCompatActivity{

    private SosFragment msosFragment;
    private SafeAreaFragment mmapFragment;
    private MyFragment myFragment;

    private Fragment fragments[];
    private int lastfragment;

    BottomNavigationView bottomNavigationView;

    private void initFragment(){
        msosFragment = new SosFragment();
        mmapFragment = new SafeAreaFragment();
        myFragment = new MyFragment();

        fragments = new Fragment[]{msosFragment,mmapFragment,myFragment};

        lastfragment = 0;

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,msosFragment).show(msosFragment).commit();
        bottomNavigationView = findViewById(R.id.tb);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.sos_message:
                    if (lastfragment != 0){
                        switchFragment(lastfragment,0);
                        lastfragment = 0;
                    }
                    return true;
                case R.id.safe_area:
                    if (lastfragment != 1){
                        switchFragment(lastfragment,1);
                        lastfragment = 1;
                    }
                    return true;
                case R.id.my_message:
                    if (lastfragment != 2){
                        switchFragment(lastfragment,2);
                        lastfragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    private void switchFragment(int lastfragment, int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上一个
        if (fragments[i].isAdded() == false){
            transaction.add(R.id.fragment_container,fragments[i]);
        }
        transaction.show(fragments[i]).commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

       initFragment();
    }
}
