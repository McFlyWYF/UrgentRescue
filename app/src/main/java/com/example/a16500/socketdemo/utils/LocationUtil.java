package com.example.a16500.socketdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.a16500.socketdemo.R;

/**
 * Created by 16500 on 2019/7/11.
 */

public class LocationUtil implements AMapLocationListener {

    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption clientOption;
    private ILocationCallBack callBack;

    public void startLocate(Context context) {


        aMapLocationClient = new AMapLocationClient(context);

        //设置回调监听
        aMapLocationClient.setLocationListener(this);

        //初始化定位参数
        clientOption = new AMapLocationClientOption();
        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        clientOption.setNeedAddress(true);
        clientOption.setOnceLocation(false);

        //设置是否强制刷新wifi
        clientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置
        clientOption.setMockEnable(false);
        //设置定位间隔
        clientOption.setInterval(2000);

        aMapLocationClient.setLocationOption(clientOption);
        aMapLocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功完成回调
                String country = aMapLocation.getCountry();
                String province = aMapLocation.getProvince();
                String city = aMapLocation.getCity();
                String district = aMapLocation.getDistrict();
                String street = aMapLocation.getStreet();
                double lat = aMapLocation.getLatitude();
                double lgt = aMapLocation.getLongitude();

                callBack.callBack(country + province + city + district + street, lat, lgt, aMapLocation);
            } else {
                Log.e("AMapError", "location Error" + aMapLocation.getErrorCode() + ", errorInfo" + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 自定义图标
     *
     * @param str
     * @param lat
     * @param lgt
     * @return
     */
    public MarkerOptions getMarkerOption(String str, double lat, double lgt) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        markerOptions.position(new LatLng(lat, lgt));
        markerOptions.snippet("纬度：" + lat + "  经度：" + lgt);
        markerOptions.period(100);
        return markerOptions;
    }

    public interface ILocationCallBack {
        void callBack(String str, double lat, double lgt, AMapLocation aMapLocation);
    }

    public void setLocationCallBack(ILocationCallBack callBack){
        this.callBack = callBack;
    }
}
