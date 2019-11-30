package com.example.locateme.helper;

import android.util.Log;

public class MessingService {

    public void onNewToken(String token) {
        Log.d("TAG_TEST", "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
