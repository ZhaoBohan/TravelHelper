package cn.edu.tongji.travelhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

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
}
