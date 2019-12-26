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

    public User() {}

    public User(String phone, String password, String name, String status, String _created, String _deleted, String _updated, String id) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.status = status;
        this._created = _created;
        this._deleted = _deleted;
        this._updated = _updated;
        this.id = id;
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