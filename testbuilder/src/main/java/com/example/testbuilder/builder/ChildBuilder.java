package com.example.testbuilder.builder;

import com.example.testbuilder.superman.SuperMan;

/**
 * Created by AndroidXJ on 2019/8/13.
 */

public class ChildBuilder extends SuperManBuilder {
    @Override
    public SuperMan getSuperMan() {
        super.setBody("强壮的身躯");
        super.setSymbol("小S的标志");
        super.setTalent("上天入地");
        return super.superMan;
    }
}
