package com.example.locateme.model;

public class Contact {
    private String name;
    private String phone;
    private String id;
    private String imageSource;

    public Contact(String name,String phone) {
        this.name = name;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
