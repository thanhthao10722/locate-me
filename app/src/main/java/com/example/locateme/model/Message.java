package com.example.locateme.model;

public class Message {
    private String id;
    private String userId;
    private String content;
    private String userName;
    private boolean isLatLng;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLatLng() {
        return isLatLng;
    }

    public void setLatLng(boolean latLng) {
        isLatLng = latLng;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
