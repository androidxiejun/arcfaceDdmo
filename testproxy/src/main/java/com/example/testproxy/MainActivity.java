package com.example.testproxy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IGamePlayer gamePlayer = new GamePlayerProxy("666");
        gamePlayer.login();
        gamePlayer.killBoss();
        gamePlayer.upgrade();
    }
}
