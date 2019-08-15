package com.example.testbuilder.builder;

import com.example.testbuilder.superman.SuperMan;

/**
 * Created by AndroidXJ on 2019/8/13.
 */

public class AdultBuilder extends SuperManBuilder {
    @Override
    public SuperMan getSuperMan() {
        super.setBody("强壮的身躯");
        super.setSymbol("大S的标志");
        super.setTalent("镭射光线");
        return super.superMan;
    }
}
