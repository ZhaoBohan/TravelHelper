package cn.edu.tongji.travelhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        String TextValue = intent.getStringExtra("City");
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(TextValue);
    }
}
