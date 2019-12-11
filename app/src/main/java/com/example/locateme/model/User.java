package com.example.locateme.model;

import java.io.Serializable;

public class User implements Serializable {
    public String phone;
    public String password;
    public String name;
    public String status;
    public String _created;
    public String _deleted;
    public String _updated;
    public String photourl;
    public String id;
    public Friend friend;
    public Friend friendrequest;
    public Friend invatationsent;

    public User() {}

//    public User(User user) {
//        this.phone = user.phone;
//        this.password = user.password;
//        this.name = user.name;
//        this.status = user.status;
//        this._created = user._created;
//        this._deleted = user._deleted;
//        this._updated = user._updated;
//    }

    public User(String phone, String password, String name, String status, String _created, String _deleted, String _updated, String id, Friend friend, Friend request, Friend sent) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.status = status;
        this._created = _created;
        this._deleted = _deleted;
        this._updated = _updated;
        this.id = id;
        this.friend = friend;
        this.friendrequest = request;
        this.invatationsent = sent;
    }

    public User(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String get_created() {
        return _created;
    }

    public void set_created(String _created) {
        this._created = _created;
    }

    public String get_deleted() {
        return _deleted;
    }

    public void set_deleted(String _deleted) {
        this._deleted = _deleted;
    }

    public String get_updated() {
        return _updated;
    }

    public void set_updated(String _updated) {
        this._updated = _updated;
    }

}