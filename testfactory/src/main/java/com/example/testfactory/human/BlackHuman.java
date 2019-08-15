package com.example.testfactory.human;

import android.util.Log;

import com.example.testfactory.MainActivity;

/**
 * Created by AndroidXJ on 2019/8/12.
 */

public class BlackHuman implements IHuman {
    @Override
    public void getColor() {
        Log.d(MainActivity.TAG, "黑种人的皮肤是黑色的");
    }

    @Override
    public void talk() {
        Log.d(MainActivity.TAG, "黑种人说话很幽默");
    }
}
