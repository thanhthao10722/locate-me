package com.example.locateme.model;

public class Contact {
    private String name;

    public Contact(String name,String phone) {
        this.name = name;
        this.number = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;
}
