package com.example.locateme.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

public class Chat {
    public String chatId = "";
    public String messageId;
    public String senderId;
    public String message = "";
    public String mediaUrl = "";
    public String mediaThumbUrl = "";
    public long timestap;

    @Exclude
    public String localMediaUrl = "";

    @Exclude
    public int id = 0;

    @Exclude
    public long blockTime = 0;

    public Chat() {

    }

    public java.util.Map<String, String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    public void setTimestap(long timestap) {
        this.timestap = timestap;
    }

    @Exclude
    public long getTimestampLong() {
        return timestap;
    }
}


