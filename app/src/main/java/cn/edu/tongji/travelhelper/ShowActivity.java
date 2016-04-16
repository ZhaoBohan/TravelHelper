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
import android.widget.TabHost;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        String httpUrl3 = "http://apis.baidu.com/showapi_open_bus/oil_price/find";
        String httpArg3 = "prov=" + province;
        String jsonResult3 = request3(httpUrl3, httpArg3);
        String str;

        TextView _sDate = (TextView) findViewById(R.id.sDate);
        if (_sDate != null) {
            if(jsonResult3!=null){
                _sDate.setText(jsonResult3);
            }
            else{
                _sDate.setText("No Data");
            }
        }
        /*
        try{
            JSONObject jsonObject3 = new JSONObject(jsonResult3);
            str = jsonObject3.getString("ct");
            TextView _sDate = (TextView) findViewById(R.id.sDate);
            _sDate.setText(str);
        }catch (JSONException e){
            e.printStackTrace();
        }
*/

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

    public static String request3(String httpUrl3, String httpArg3){
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl3 = httpUrl3 + "?" + httpArg3;
        try{
            URL url = new URL(httpUrl3);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", "8da8d425b9a8dff6d58889f266a077f0");
            connection.connect();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null){
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
