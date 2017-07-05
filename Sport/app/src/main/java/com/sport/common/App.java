package com.sport.common;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;


public class App extends Application {
    private static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "b4095923fdf763c4a9c21b35f76035ba");
        CrashReport.initCrashReport(getApplicationContext(), "900033810", false);
    }

    public static App getInstance(){
        if(app==null){
            app=new App();
        }
        return app;
    }
}
