package cn.edu.tongji.travelhelper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowActivity extends AppCompatActivity {
    MapView mMapView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_show);

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();




        mMapView = (MapView) findViewById(R.id.bmapView);
        LatLng cenpt = new LatLng(latitude,longitude);
        MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(12).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mMapView.getMap().setMapStatus(mMapStatusUpdate);


        Intent intent = getIntent();
        String city = intent.getStringExtra("City");
        String province = intent.getStringExtra("Province");
        TextView _sProvince = (TextView) findViewById(R.id.sProvince);
        _sProvince.setText(province);


        //Oid Price
        final TextView _sDate = (TextView) findViewById(R.id.sDate);
        final TextView _pP0 = (TextView) findViewById(R.id.pP0);
        final TextView _pP90 = (TextView) findViewById(R.id.pP90);
        final TextView _pP93 = (TextView) findViewById(R.id.pP93);
        final TextView _pP97 = (TextView) findViewById(R.id.pP97);
        Parameters para = new Parameters();
        para.put("prov", province);
        ApiStoreSDK.execute("http://apis.baidu.com/showapi_open_bus/oil_price/find",
                ApiStoreSDK.GET,
                para,
                new ApiCallBack(){
                    public void onSuccess(int status, String responseString){
                        Log.i("oriPrice", "onSuccess");
                        Log.i("oriPrice", responseString);
                        try{
                            JSONObject jsonObject = new JSONObject(responseString);
                            String str = jsonObject.getString("showapi_res_body");
                            JSONObject jsonObject1 = new JSONObject(str);
                            JSONArray jsonArray = jsonObject1.getJSONArray("list");
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
                                _sDate.setText(jsonObjectSon.getString("ct"));
                                _pP0.setText(jsonObjectSon.getString("p0"));
                                _pP90.setText(jsonObjectSon.getString("p90"));
                                _pP93.setText(jsonObjectSon.getString("p93"));
                                _pP97.setText(jsonObjectSon.getString("p97"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.i("oriPrice", "finish");
                    }
                    public void onComplete() {
                        Log.i("oriPrice", "onComplete");
                    }
                    public void onError(int status, String responseString, Exception e){
                        Log.i("oriPrice", "onError, status: " + status);
                        Log.i("oriPrice", "errMsg: " + (e == null ? "" : e.getMessage()));
                    }
                }
        );







        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();
        tabhost.addTab(tabhost.newTabSpec("one").setIndicator("Weather").setContent(R.id.linearLayout));
        tabhost.addTab(tabhost.newTabSpec("two").setIndicator("Bus").setContent(R.id.linearLayout2));
        tabhost.addTab(tabhost.newTabSpec("three").setIndicator("Transport").setContent(R.id.linearLayout3));
        tabhost.addTab(tabhost.newTabSpec("four").setIndicator("Map").setContent(R.id.linearLayout4));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


}
