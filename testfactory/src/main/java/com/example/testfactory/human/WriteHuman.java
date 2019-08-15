package com.example.testfactory.human;

import android.util.Log;

import com.example.testfactory.MainActivity;

/**
 * Created by AndroidXJ on 2019/8/12.
 */

public class WriteHuman implements IHuman {
    @Override
    public void getColor() {
        Log.d(MainActivity.TAG, "白种人的皮肤是白色的");
    }

    @Override
    public void talk() {
        Log.d(MainActivity.TAG, "白种人说话听不懂");
    }
}
