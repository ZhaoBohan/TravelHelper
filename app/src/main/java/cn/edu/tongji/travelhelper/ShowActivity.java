package cn.edu.tongji.travelhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

public class ShowActivity extends AppCompatActivity {
    MapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_show);

        mMapView = (MapView) findViewById(R.id.bmapView);

        /*Intent intent = getIntent();
        String TextValue = intent.getStringExtra("City");
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(TextValue);*/

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
