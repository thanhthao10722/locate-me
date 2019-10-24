package com.example.locateme.model;

public class Admin
{
    private String username;
    private String password;
    private String name;
    private String _created;
    private String _deleted;
    private String _updated;

    public Admin(String username, String password, String name, String _created, String _deleted, String _updated) {
        this.username = username;
        this.password = password;
        this.name = name;
        this._created = _created;
        this._deleted = _deleted;
        this._updated = _updated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
