package com.example.locateme.Util;

import android.content.Context;
import android.content.UriMatcher;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class RealtimeChatUtil {
    private Context context;
    private Socket chatSocket;
    public RealtimeChatUtil(Context context) {
        this.context = context;
        try{
            chatSocket = IO.socket("http://chat.socket.io");
        }catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chatSocket.connect();
    }
    public void sendMessage(String message) {

    }
}
