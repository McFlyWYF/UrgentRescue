package com.example.a16500.socketdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import com.example.a16500.socketdemo.utils.LocationUtil;

import java.util.Arrays;

public class MapActivity extends AppCompatActivity implements LocationSource, View.OnClickListener {

    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener listener = null;//定位监听器

    private LocationUtil locationUtil;

    private Button normal, night, navi, satellite;


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
        init();

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


        LatLng latLng1 = new LatLng(38.016021, 112.441172);
        LatLng latLng2 = new LatLng(38.015021, 112.449172);
        LatLng latLng3 = new LatLng(38.011033, 112.448172);
        LatLng latLng4 = new LatLng(38.013055, 112.443172);
        LatLng latLng5 = new LatLng(38.019088, 112.445172);

        final Marker marker1 = aMap.addMarker(new MarkerOptions().position(latLng1).title("求救者1 ").snippet("水 ").draggable(false).setFlat(true));
        final Marker marker2 = aMap.addMarker(new MarkerOptions().position(latLng2).title("求救者2 ").snippet("医疗，水").draggable(false).setFlat(true));
        final Marker marker3 = aMap.addMarker(new MarkerOptions().position(latLng3).title("求救者3 ").snippet("食物").draggable(false).setFlat(true));
        final Marker marker4 = aMap.addMarker(new MarkerOptions().position(latLng4).title("求救者4 ").snippet("紧急事件 ").draggable(false).setFlat(true));
        final Marker marker5 = aMap.addMarker(new MarkerOptions().position(latLng5).title("求救者5 ").snippet("生活用品").draggable(false).setFlat(true));

        LatLng[] latlngs = new LatLng[500];
        double x = 38.015021;
        double y = 112.449172;

        for (int i = 0; i < 500; i++) {
            double x_ = 0;
            double y_ = 0;
            x_ = Math.random() * 0.5 - 0.25;
            y_ = Math.random() * 0.5 - 0.25;
            latlngs[i] = new LatLng(x + x_, y + y_);
        }

        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(Arrays.asList(latlngs)); // 设置热力图绘制的数据
        // Gradient 的设置可见参考手册
        // 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();

        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        aMap.addTileOverlay(tileOverlayOptions);

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

        }
    }
}
