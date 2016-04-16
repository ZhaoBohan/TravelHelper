package cn.edu.tongji.travelhelper;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by Cloudiee on 2016/4/17.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        // TODO 您的其他初始化流程
        ApiStoreSDK.init(this, "8da8d425b9a8dff6d58889f266a077f0");
        super.onCreate();
    }

}
