package com.example.testbuilder.director;

import com.example.testbuilder.builder.AdultBuilder;
import com.example.testbuilder.builder.ChildBuilder;
import com.example.testbuilder.superman.SuperMan;

/**
 * Created by AndroidXJ on 2019/8/13.
 */

public class SuperManDirector {
    private static AdultBuilder mAdultBuilder = new AdultBuilder();
    private static ChildBuilder mChildBuilder = new ChildBuilder();

    public static SuperMan getAdultMan() {
        return mAdultBuilder.getSuperMan();
    }

    public static SuperMan getChildMan() {
        return mChildBuilder.getSuperMan();
    }

}
