package com.example.testbuilder.builder;

import com.example.testbuilder.superman.SuperMan;

/**
 * Created by AndroidXJ on 2019/8/13.
 */

public abstract class SuperManBuilder {
    protected final SuperMan superMan = new SuperMan();

    public void setBody(String body) {
        this.superMan.setBody(body);
    }

    public void setTalent(String talent) {
        this.superMan.setTalent(talent);
    }

    public void setSymbol(String symbol) {
        this.superMan.setSymbol(symbol);
    }

    public abstract SuperMan getSuperMan();
}
