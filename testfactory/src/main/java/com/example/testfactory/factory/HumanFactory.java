package com.example.testfactory.factory;

import android.util.Log;

import com.example.testfactory.MainActivity;
import com.example.testfactory.human.IHuman;

/**
 * Created by AndroidXJ on 2019/8/12.
 */

public class HumanFactory {
    public static  <T extends IHuman> T createHuman(Class<T> c) {
        IHuman human = null;
        try {
            human = (T) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) {
            Log.d(MainActivity.TAG, "人种生成错误1-----------" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.d(MainActivity.TAG, "人种生成错误2-----------" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d(MainActivity.TAG, "人种生成错误3-----------" + e.getMessage());
            e.printStackTrace();
        }
        return (T) human;
    }
}
