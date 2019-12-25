package com.example.locateme.model;

import java.io.Serializable;

public class Friend implements Serializable
{
    public String id_key;
    public String id_value;
    public Friend(String id_key, String id_value)
    {
        this.id_key = id_key;
        this.id_value = id_value;
    }
    public Friend()
    {

    }

    public String getId_key() {
        return id_key;
    }

    public void setId_key(String id_key) {
        this.id_key = id_key;
    }

    public String getId_value() {
        return id_value;
    }

    public void setId_value(String id_value) {
        this.id_value = id_value;
    }
}
