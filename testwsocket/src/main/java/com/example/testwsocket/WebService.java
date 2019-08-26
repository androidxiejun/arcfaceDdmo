package com.example.testwsocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by AndroidXJ on 2019/8/16.
 */

public class WebService extends Service {

    private final static String WEBSOCKET_URL = "ws://" + "/app/websocket/";

    private static final int INTERVAL_TIME = 5;


    WebSocket mWebSocket;
    Disposable mDisposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDisposable = Observable.interval(0, INTERVAL_TIME, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .doOnError(throwable -> Log.d("WS", throwable.getMessage()))
                .subscribe(aLong -> {
                    //新建client
                    OkHttpClient client = new OkHttpClient.Builder()
                            .build();
                    //构造request对象
                    Request request = new Request.Builder()
                            .url(WEBSOCKET_URL)
                            .build();

                    client.newWebSocket(request, new WebSocketListener() {
                        @Override
                        public void onOpen(WebSocket webSocket, Response response) {
                            super.onOpen(webSocket, response);
                        }

                        @Override
                        public void onMessage(WebSocket webSocket, String text) {
                            super.onMessage(webSocket, text);
                        }

                        @Override
                        public void onClosed(WebSocket webSocket, int code, String reason) {
                            super.onClosed(webSocket, code, reason);
                        }

                        @Override
                        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                            super.onFailure(webSocket, t, response);
                        }
                    });
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
