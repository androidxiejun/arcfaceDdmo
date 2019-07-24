package com.example.testleakcanary;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by AndroidXJ on 2019/7/12.
 */

public class LeakApplication extends Application {
    private RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
//        mRefWatcher= setupLeakCanary();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
//    private RefWatcher setupLeakCanary(){
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }
//    public static  RefWatcher getLeak(Context context){
//        LeakApplication leakApplication = (LeakApplication) context.getApplicationContext();
//        return leakApplication.mRefWatcher;
//    }
}
