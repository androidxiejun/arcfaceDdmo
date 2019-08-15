package com.example.testfactory.factory;

import com.example.testfactory.human.IHuman;

/**
 * Created by AndroidXJ on 2019/8/12.
 */

public abstract class AHumanFactory {
    public abstract <T extends IHuman> T createHuman(Class<T> c);
}
