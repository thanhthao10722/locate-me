package com.example.locateme.model;

public class User {
    public String phone;
    public String password;
    public String name;
    public boolean status;
    public String _created;
    public String _deleted;
    public String _updated;

    public User()
    {
    }

    public User(String phone, String password, String name, boolean status, String _created, String _deleted, String _updated) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.status = status;
        this._created = _created;
        this._deleted = _deleted;
        this._updated = _updated;
    }

    public String getPhone() {
        return phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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