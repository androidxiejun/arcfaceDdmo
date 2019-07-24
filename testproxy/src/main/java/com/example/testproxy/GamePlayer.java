package com.example.testproxy;

import android.util.Log;

/**
 * Created by AndroidXJ on 2019/7/22.
 */

public class GamePlayer implements IGamePlayer {
    private String name;

    public GamePlayer(IGamePlayer _IgamePlayer, String _name) {
        try {
            if (_IgamePlayer == null) {
                throw new Exception("GamePlayer can not be null!!!");
            } else {
                this.name = _name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void login() {
        Log.d("proxy", "用户：" + name + "  已登录游戏");
    }

    @Override
    public void killBoss() {
        Log.d("proxy", "用户：" + name + "  开始打BOSS!!!");
    }

    @Override
    public void upgrade() {
        Log.d("proxy", "用户：" + name + "  升级了！！！");
    }
}
