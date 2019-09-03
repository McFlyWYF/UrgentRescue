package com.example.a16500.socketdemo.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.sosppeople.activity.SosActivity;
import com.example.a16500.socketdemo.utils.LocationJs;
import com.example.a16500.socketdemo.utils.LocationUtil;
import com.example.a16500.socketdemo.utils.StaticClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapActivity extends AppCompatActivity implements LocationSource, View.OnClickListener {

    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener listener = null;//定位监听器

    private LocationUtil locationUtil;

    private Button normal, night, navi, satellite;

    int message;
    private double locationLat, locationLgtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safe_area_fragment);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        normal = findViewById(R.id.normal_btn);
        night = findViewById(R.id.night_btn);
        navi = findViewById(R.id.navi_btn);
        satellite = findViewById(R.id.satellite_btn);

        normal.setOnClickListener(this);
        night.setOnClickListener(this);
        navi.setOnClickListener(this);
        satellite.setOnClickListener(this);

        FloatingActionButton actionButton = findViewById(R.id.location_fresh);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLocation();
                Toast.makeText(MapActivity.this, "正在刷新中", Toast.LENGTH_SHORT).show();
            }
        });

        init();

    }

    //获取求救者位置信息
    protected void onLocation() {
        //拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(StaticClass.locationUrl).build();
        //将request封装为Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MapActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("placeres=: ", res);
                Gson gson = new Gson();
                List<LocationJs> locationJss = gson.fromJson(res, new TypeToken<List<LocationJs>>() {
                }.getType());

                for (int i = 0; i < locationJss.size(); i++) {
                    message = locationJss.get(i).getMessage();
                    locationLat = locationJss.get(i).getLatitude();
                    locationLgtt = locationJss.get(i).getLongitude();

                    //求救者定位点
                    LatLng locationLatLng = new LatLng(locationLat, locationLgtt);
                    final Marker placeMarker = aMap.addMarker(new MarkerOptions().position(locationLatLng).title(String.valueOf(message)).draggable(false).setFlat(true));

                    Log.d("placename", String.valueOf(message));
                    Log.d("placeLgtt", String.valueOf(locationLat));
                    Log.d("placeLat", String.valueOf(locationLgtt));
                }
            }
        });
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        setLocationCallBack();

        //设置定位监听器
        aMap.setLocationSource(this);
        //设置放缩级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        //显示定位层并可触发
        aMap.setMyLocationEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.data_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()) {
            case R.id.things_view:
                Intent intent = new Intent(MapActivity.this, ThingsViewActivity.class);
                startActivity(intent);
                Toast.makeText(MapActivity.this, "物资需求雷达图", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sos_view:
                Intent intent1 = new Intent(MapActivity.this, SosViewActivity.class);
                startActivity(intent1);
                Toast.makeText(MapActivity.this, "求救曲线图", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLocationCallBack() {

        locationUtil = new LocationUtil();
        locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {
                //根据获取的经纬度，将地图移到定位位置
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lgt)));
                listener.onLocationChanged(aMapLocation);

                //添加定位图标
//                aMap.addMarker(locationUtil.getMarkerOption(str,lat,lgt));
            }
        });
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;
        locationUtil.startLocate(getApplicationContext());
    }

    @Override
    public void deactivate() {
        listener = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停地图绘制
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制地图
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.normal_btn:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 设置标准地图模式，aMap是地图控制器对象。
                break;
            case R.id.night_btn:
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);// 设置夜间地图模式，aMap是地图控制器对象。
                break;
            case R.id.satellite_btn:
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象。
                break;
            case R.id.navi_btn:
                aMap.setMapType(AMap.MAP_TYPE_NAVI);// 设置导航地图模式，aMap是地图控制器对象。
                break;
            default:
                break;
        }
    }
}