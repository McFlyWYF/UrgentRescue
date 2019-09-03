package com.example.a16500.socketdemo.sosppeople.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.example.a16500.socketdemo.activity.LoginActivity;
import com.example.a16500.socketdemo.activity.SosViewActivity;
import com.example.a16500.socketdemo.activity.StaticVar;
import com.example.a16500.socketdemo.activity.ThingsViewActivity;
import com.example.a16500.socketdemo.sosppeople.activity.SosActivity;
import com.example.a16500.socketdemo.utils.LocationJs;
import com.example.a16500.socketdemo.utils.LocationUtil;
import com.example.a16500.socketdemo.R;
import com.example.a16500.socketdemo.utils.LoginJs;
import com.example.a16500.socketdemo.utils.PlaceJs;
import com.example.a16500.socketdemo.utils.StaticClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 16500 on 2019/8/28.
 */

public class SafeAreaFragment extends Fragment implements View.OnClickListener, LocationSource, AMapLocationListener {

    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener listener = null;//定位监听器

    private LocationUtil locationUtil;

    private Button normal, night, navi, satellite;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    AMapLocationClient mlocationClient;

    //避难场所经纬度
    double placeLat, placeLgtt;
    String placeName;

    int message;
    private double locationLat, locationLgtt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.safe_area_fragment, container, false);

        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        normal = view.findViewById(R.id.normal_btn);
        night = view.findViewById(R.id.night_btn);
        navi = view.findViewById(R.id.navi_btn);
        satellite = view.findViewById(R.id.satellite_btn);


        normal.setOnClickListener(this);
        night.setOnClickListener(this);
        navi.setOnClickListener(this);
        satellite.setOnClickListener(this);

        setHasOptionsMenu(true);

        mlocationClient = new AMapLocationClient(getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);

        //启动定位
        mlocationClient.startLocation();
        init();
        onPlace();

        return view;
    }


    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        setLocationCallBack();

        //设置定位监听器
        aMap.setLocationSource(this);
        //设置放缩级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //显示定位层并可触发
        aMap.setMyLocationEnabled(true);


        LatLng latLng1 = new LatLng(38.016021, 112.441172);
        LatLng latLng2 = new LatLng(38.015021, 112.449172);
        LatLng latLng3 = new LatLng(38.011033, 112.448172);
        LatLng latLng4 = new LatLng(38.013055, 112.443172);
        LatLng latLng5 = new LatLng(38.019088, 112.445172);

//        final Marker marker1 = aMap.addMarker(new MarkerOptions().position(latLng1).title("求救者1 ").snippet("水 ").draggable(false).setFlat(true));
//        final Marker marker2 = aMap.addMarker(new MarkerOptions().position(latLng2).title("求救者2 ").snippet("医疗，水").draggable(false).setFlat(true));
//        final Marker marker3 = aMap.addMarker(new MarkerOptions().position(latLng3).title("求救者3 ").snippet("食物").draggable(false).setFlat(true));
//        final Marker marker4 = aMap.addMarker(new MarkerOptions().position(latLng4).title("求救者4 ").snippet("紧急事件 ").draggable(false).setFlat(true));
//        final Marker marker5 = aMap.addMarker(new MarkerOptions().position(latLng5).title("求救者5 ").snippet("生活用品").draggable(false).setFlat(true));

    }

    public void setLocationCallBack() {

        locationUtil = new LocationUtil();
        locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {
                //根据获取的经纬度，将地图移到定位位置
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lgt)));
                listener.onLocationChanged(aMapLocation);
            }
        });
    }


    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;
        locationUtil.startLocate(getContext().getApplicationContext());
    }

    @Override
    public void deactivate() {
        listener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        //暂停地图绘制
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁地图
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.data_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()) {
            case R.id.things_view:
                Intent intent = new Intent(getActivity(), ThingsViewActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "物资需求雷达图", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sos_view:
                Intent intent1 = new Intent(getActivity(), SosViewActivity.class);
                startActivity(intent1);
                Toast.makeText(getActivity(), "求救曲线图", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                StaticVar.lat = amapLocation.getLatitude();//获取纬度
                StaticVar.lgtt = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    //获取避难场所
    protected void onPlace() {
        //拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(StaticClass.placeUrl).build();
        //将request封装为Call
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "请求服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("placeres=: ", res);
                Gson gson = new Gson();
                List<PlaceJs> placeJss = gson.fromJson(res, new TypeToken<List<PlaceJs>>() {
                }.getType());
                Log.d("place", placeJss.toArray().toString());

                for (int i = 0; i < placeJss.size(); i++) {
                    placeName = placeJss.get(i).getSname();
                    placeLat = placeJss.get(i).getSlatitude();
                    placeLgtt = placeJss.get(i).getSlongitude();

                    //避难场所定位点
                    LatLng placeLatLng = new LatLng(placeLat, placeLgtt);
                    final Marker placeMarker = aMap.addMarker(new MarkerOptions().position(placeLatLng).title(placeName).draggable(false).setFlat(true));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.place_icon);
                    placeMarker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));

                    Log.d("placename", placeName);
                    Log.d("placeLgtt", String.valueOf(placeLgtt));
                    Log.d("placeLat", String.valueOf(placeLat));
                }
            }
        });
    }

}
