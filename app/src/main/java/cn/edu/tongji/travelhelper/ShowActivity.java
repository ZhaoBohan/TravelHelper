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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
//import com.thinkland.sdk.android.Parameters;

import org.json.JSONArray;
import org.json.JSONObject;






public class ShowActivity extends AppCompatActivity {
    MapView mMapView = null;
    private Context mContext;
    String city;
    String province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_show);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(btnOnClick);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(btnOnClick2);
        mContext = this;

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
        city = intent.getStringExtra("City");
        province = intent.getStringExtra("Province");
        TextView _sProvince = (TextView) findViewById(R.id.sProvince);
        _sProvince.setText(province);
        TextView _sCity = (TextView) findViewById(R.id.sCity);
        _sCity.setText(city);


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
                                Log.i("oriPrice", "Finish");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
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


        //Weather
        final TextView _dayWeather = (TextView) findViewById(R.id.dayWeather);
        final TextView _nightWeather = (TextView) findViewById(R.id.nightWeather);
        final TextView _hum = (TextView) findViewById(R.id.hum);
        final TextView _pop = (TextView) findViewById(R.id.pop);
        final TextView _maxTem = (TextView) findViewById(R.id.maxTem);
        final TextView _minTem = (TextView) findViewById(R.id.minTem);
        final TextView _wind = (TextView) findViewById(R.id.wind);
        final TextView _windWay = (TextView) findViewById(R.id.windWay);
        final TextView _dayWeather2 = (TextView) findViewById(R.id.dayWeather2);
        final TextView _nightWeather2 = (TextView) findViewById(R.id.nightWeather2);
        final TextView _hum2 = (TextView) findViewById(R.id.hum2);
        final TextView _pop2 = (TextView) findViewById(R.id.pop2);
        final TextView _maxTem2 = (TextView) findViewById(R.id.maxTem2);
        final TextView _minTem2 = (TextView) findViewById(R.id.minTem2);
        final TextView _wind2 = (TextView) findViewById(R.id.wind2);
        final TextView _windWay2 = (TextView) findViewById(R.id.windWay2);
        Parameters para2 = new Parameters();
        para2.put("city", city);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free",
                ApiStoreSDK.GET,
                para2,
                new ApiCallBack(){
                    public void onSuccess(int status, String responseString){
                        Log.i("Weather", "onSuccess");
                        Log.i("Weather", responseString);
                        try{
                            String str = "";
                            JSONObject jsonObject = new JSONObject(responseString);
                            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
                            JSONObject jsonObject1 = (JSONObject) jsonArray.opt(0);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("daily_forecast");
                            JSONObject jsonObject2 = (JSONObject) jsonArray1.opt(0);   //Today
                            _hum.setText("湿度:"+jsonObject2.getString("hum"));
                            _pop.setText("降水率:"+jsonObject2.getString("pop"));
                            str = jsonObject2.getString("cond");
                            JSONObject jsonObject3 = new JSONObject(str);              //Today cond
                            _dayWeather.setText("日间:"+jsonObject3.getString("txt_d"));
                            _nightWeather.setText("夜间:"+jsonObject3.getString("txt_n"));
                            str = jsonObject2.getString("wind");
                            JSONObject jsonObject4 = new JSONObject(str);              //Today wind
                            _wind.setText("风力:"+jsonObject4.getString("sc"));
                            _windWay.setText("风向:"+jsonObject4.getString("dir"));
                            str = jsonObject2.getString("tmp");                        //Today tmp
                            JSONObject jsonObject5 = new JSONObject(str);
                            _maxTem.setText("最高温:"+jsonObject5.getString("max"));
                            _minTem.setText("最低温:"+jsonObject5.getString("min"));

                            JSONObject jsonObject6 = (JSONObject) jsonArray1.opt(1);   //Tomorrow
                            _hum2.setText("湿度:"+jsonObject6.getString("hum"));
                            _pop2.setText("降水率:"+jsonObject6.getString("pop"));
                            str = jsonObject6.getString("cond");
                            JSONObject jsonObject7 = new JSONObject(str);              //Tomorrow cond
                            _dayWeather2.setText("日间:"+jsonObject7.getString("txt_d"));
                            _nightWeather2.setText("夜间:"+jsonObject7.getString("txt_n"));
                            str = jsonObject6.getString("wind");
                            JSONObject jsonObject8 = new JSONObject(str);              //Tomorrow wind
                            _wind2.setText("风力:"+jsonObject8.getString("sc"));
                            _windWay2.setText("风向:"+jsonObject8.getString("dir"));
                            str = jsonObject6.getString("tmp");                        //Tomorrow tmp
                            JSONObject jsonObject9 = new JSONObject(str);
                            _maxTem2.setText("最高温:"+jsonObject9.getString("max"));
                            _minTem2.setText("最低温:"+jsonObject9.getString("min"));
                            Log.i("Weather", "Finish");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    public void onComplete() {
                        Log.i("Weather", "onComplete");
                    }
                    public void onError(int status, String responseString, Exception e){
                        Log.i("Weather", "onError, status: " + status);
                        Log.i("Weather", "errMsg: " + (e == null ? "" : e.getMessage()));
                    }
                }
        );







        TabHost tabhost = (TabHost) findViewById(R.id.tabHost);
        tabhost.setup();
        tabhost.addTab(tabhost.newTabSpec("one").setIndicator("Weather").setContent(R.id.linearLayout));
        tabhost.addTab(tabhost.newTabSpec("two").setIndicator("Spot").setContent(R.id.linearLayout2));
        tabhost.addTab(tabhost.newTabSpec("three").setIndicator("Oil").setContent(R.id.linearLayout3));
        tabhost.addTab(tabhost.newTabSpec("four").setIndicator("Plane").setContent(R.id.linearLayout4));
        tabhost.addTab(tabhost.newTabSpec("five").setIndicator("Map").setContent(R.id.linearLayout5));
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

    //AirPlane XML
    /*
    private Button.OnClickListener btnOnClick = new Button.OnClickListener(){
        public void onClick(View v){
            String planeNum;
            String planeDate;
            EditText editText = (EditText) findViewById(R.id.editText);
            EditText editText2 = (EditText) findViewById(R.id.editText2);
            planeNum = editText.getText().toString();
            planeDate = editText2.getText().toString();
            if (planeNum.equals("") || planeDate.equals("")){
                return;
            }

            final TextView _company = (TextView) findViewById(R.id.company);
            final TextView _qf = (TextView) findViewById(R.id.qf);
            final TextView _fromWeather = (TextView) findViewById(R.id.fromWeather);
            final TextView _dd = (TextView) findViewById(R.id.dd);
            final TextView _toWeather = (TextView) findViewById(R.id.toWeather);
            final TextView _jhqfTime = (TextView) findViewById(R.id.jhqfTime);
            final TextView _jhddTime = (TextView) findViewById(R.id.jhddTime);
            final TextView _state = (TextView) findViewById(R.id.state);
            final TextView _djk = (TextView) findViewById(R.id.djk);
            final TextView _zjgt = (TextView) findViewById(R.id.zjgt);
            final TextView _xlzp = (TextView) findViewById(R.id.xlzp);

            com.thinkland.sdk.android.Parameters params = new com.thinkland.sdk.android.Parameters();
            params.add("name", planeNum);
            params.add("date", planeDate);
            params.add("dtype", "xml");
            JuheData.executeWithAPI(mContext, 20, "http://apis.juhe.cn/plan/snew", JuheData.GET, params, new DataCallBack() {
                        @Override
                        public void onSuccess(int statusCode, String responseString) {
                            Log.i("Plane", responseString);
                            try {
                                JAXBContext ctx = JAXBContext.newInstance(root.class);
                                Unmarshaller unmarshaller = ctx.createUnmarshaller();
                                root planeData = (root) unmarshaller.unmarshal(new StringReader(responseString));
                                _company.setText(planeData.getResult().getInfo().getCompany());
                                _qf.setText("起飞:"+planeData.getResult().getInfo().getFrom());
                                _fromWeather.setText("起点天气:"+planeData.getResult().getInfo().getFrom_weather());
                                _dd.setText("到达:"+planeData.getResult().getInfo().getTo());
                                _toWeather.setText("终点天气:"+planeData.getResult().getInfo().getTo_weather());
                                _jhqfTime.setText("计划起飞时间:"+planeData.getResult().getInfo().getQftime());
                                _jhddTime.setText("计划到达时间:"+planeData.getResult().getInfo().getDdtime());
                                _state.setText("航班状态:"+planeData.getResult().getList().getItem().getState());
                                _djk.setText("登机口:"+planeData.getResult().getList().getItem().getDjk());
                                _zjgt.setText("值机柜台:"+planeData.getResult().getList().getItem().getZjgt());
                                _xlzp.setText("行李闸口:"+planeData.getResult().getList().getItem().getXlzp());
                            } catch (JAXBException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFinish() {
                            Log.i("Plane", "Finish");
                        }

                        @Override
                        public void onFailure(int statusCode, String responseString, Throwable throwable) {
                            Log.i("Plane", throwable.getMessage());
                        }
                    }
            );
        }
    };*/

    //AirPlane JSON
    private Button.OnClickListener btnOnClick = new Button.OnClickListener(){
        public void onClick(View v){
            String planeNum;
            String planeDate;
            EditText editText = (EditText) findViewById(R.id.editText);
            EditText editText2 = (EditText) findViewById(R.id.editText2);
            planeNum = editText.getText().toString();
            planeDate = editText2.getText().toString();
            if (planeNum.equals("") || planeDate.equals("")){
                return;
            }

            final TextView _company = (TextView) findViewById(R.id.company);
            final TextView _qf = (TextView) findViewById(R.id.qf);
            final TextView _fromWeather = (TextView) findViewById(R.id.fromWeather);
            final TextView _dd = (TextView) findViewById(R.id.dd);
            final TextView _toWeather = (TextView) findViewById(R.id.toWeather);
            final TextView _jhqfTime = (TextView) findViewById(R.id.jhqfTime);
            final TextView _jhddTime = (TextView) findViewById(R.id.jhddTime);
            final TextView _state = (TextView) findViewById(R.id.state);
            final TextView _djk = (TextView) findViewById(R.id.djk);
            final TextView _zjgt = (TextView) findViewById(R.id.zjgt);
            final TextView _xlzp = (TextView) findViewById(R.id.xlzp);

            com.thinkland.sdk.android.Parameters params = new com.thinkland.sdk.android.Parameters();
            params.add("name", planeNum);
            params.add("date", planeDate);
            params.add("dtype", "json");
            JuheData.executeWithAPI(mContext, 20, "http://apis.juhe.cn/plan/snew", JuheData.GET, params, new DataCallBack() {
                        @Override
                        public void onSuccess(int statusCode, String responseString) {
                            Log.i("Plane", responseString);
                            try{
                                JSONObject jsonObject = new JSONObject(responseString);
                                String str = jsonObject.getString("result");
                                JSONObject jsonObjectSon = new JSONObject(str);     //result
                                str = jsonObjectSon.getString("info");
                                JSONObject jsonObjectInfo = new JSONObject(str);
                                JSONArray jsonArray = jsonObjectSon.getJSONArray("list");
                                JSONObject jsonObjectList = (JSONObject) jsonArray.opt(0);
                                _company.setText(jsonObjectInfo.getString("company"));
                                _qf.setText("起飞:"+jsonObjectInfo.getString("from"));
                                _fromWeather.setText("起点天气:"+jsonObjectInfo.getString("from_weather"));
                                _dd.setText("到达:"+jsonObjectInfo.getString("to"));
                                _toWeather.setText("终点天气:"+jsonObjectInfo.getString("to_weather"));
                                _jhqfTime.setText("计划起飞时间:"+jsonObjectInfo.getString("qftime"));
                                _jhddTime.setText("计划到达时间:"+jsonObjectInfo.getString("ddtime"));
                                _state.setText("航班状态:"+jsonObjectList.getString("state"));
                                _djk.setText("登机口:"+jsonObjectList.getString("djk"));
                                _zjgt.setText("值机柜台:"+jsonObjectList.getString("zjgt"));
                                _xlzp.setText("行李闸口:"+jsonObjectList.getString("xlzp"));
                                Log.i("Plane", "Finish");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFinish() {
                            Log.i("Plane", "Finish");
                        }

                        @Override
                        public void onFailure(int statusCode, String responseString, Throwable throwable) {
                            Log.i("Plane", throwable.getMessage());
                        }
                    }
            );


        }
    };


    //spot
    private Button.OnClickListener btnOnClick2 = new Button.OnClickListener(){
        public void onClick(View v){
            String spotName;
            EditText editText = (EditText) findViewById(R.id.spotName);
            spotName = editText.getText().toString();
            final TextView sAddress = (TextView) findViewById(R.id.sAddress);
            final TextView sDistrict = (TextView) findViewById(R.id.sDistrict);
            final TextView sType = (TextView) findViewById(R.id.sType);
            Parameters parameters = new Parameters();
            parameters.put("keyWord", spotName);
            parameters.put("cityName", city);
            parameters.put("number", "1");
            parameters.put("page", "1");
            parameters.put("output", "json");
            ApiStoreSDK.execute("http://apis.baidu.com/apistore/point/search",
                    ApiStoreSDK.GET,
                    parameters,
                    new ApiCallBack(){
                        public void onSuccess(int status, String responseString){
                            Log.i("spot", "onSuccess");
                            Log.i("spot", responseString);
                            try{
                                JSONObject jsonObject = new JSONObject(responseString);
                                JSONArray jsonArray = jsonObject.getJSONArray("pointList");
                                JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(0);
                                sAddress.setText(jsonObjectSon.getString("address"));
                                sDistrict.setText(jsonObjectSon.getString("district"));
                                sType.setText(jsonObjectSon.getString("type"));
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        public void onComplete() {
                            Log.i("spot", "onComplete");
                        }
                        public void onError(int status, String responseString, Exception e){
                            Log.i("spot", "onError, status: " + status);
                            Log.i("spot", "errMsg: " + (e == null ? "" : e.getMessage()));
                        }
                    }
            );
        }
    };

}
