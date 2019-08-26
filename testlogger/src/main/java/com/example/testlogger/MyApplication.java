package com.example.testlogger;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by AndroidXJ on 2019/8/26.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Logger
//        FormatStrategy formatStrategy= PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(true)
//                .methodCount(1)
//                .methodOffset(5)
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
