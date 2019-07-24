package com.example.testproxy;

/**
 * Created by AndroidXJ on 2019/7/22.
 */

public class GamePlayerProxy implements IGamePlayer {
    private IGamePlayer mIGamePlayer;

    public GamePlayerProxy(String name) {
        mIGamePlayer = new GamePlayer(this, name);
    }


    @Override
    public void login() {
        mIGamePlayer.login();
    }

    @Override
    public void killBoss() {
        mIGamePlayer.killBoss();
    }

    @Override
    public void upgrade() {
        mIGamePlayer.upgrade();
    }
}
