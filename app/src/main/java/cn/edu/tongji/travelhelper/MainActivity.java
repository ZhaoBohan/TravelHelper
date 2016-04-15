package cn.edu.tongji.travelhelper;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.startSearch);
        button.setOnClickListener(btnOnClick);
    }

    private Button.OnClickListener btnOnClick = new Button.OnClickListener(){
        public void onClick(View v){
            String city;
            EditText edittext = (EditText) findViewById(R.id.cityName);
            city = edittext.getText().toString();
            if (city.equals("")){
                return;
            }
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ShowActivity.class);
            intent.putExtra("City", city);
            MainActivity.this.startActivity(intent);
        }
    };
}