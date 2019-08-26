package com.example.testwsocket;

import android.net.Uri;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by AndroidXJ on 2019/8/16.
 */

public class SocketClient extends WebSocketClient {

    public void init() {
        try {
            SocketClient client = new SocketClient(new URI(""));
            client.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public SocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
