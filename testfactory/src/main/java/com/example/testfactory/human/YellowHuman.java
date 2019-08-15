package com.example.testfactory.human;

import android.util.Log;

import com.example.testfactory.MainActivity;

/**
 * Created by AndroidXJ on 2019/8/12.
 */

public class YellowHuman implements IHuman {
    @Override
    public void getColor() {
        Log.d(MainActivity.TAG,"黄种人的皮肤是黄色的");
    }

    @Override
    public void talk() {
        Log.d(MainActivity.TAG,"黄种人的说话很耿直");
    }
}
