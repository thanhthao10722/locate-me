package com.example.locateme.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chatroom implements Serializable {
    private String id;
    private String name;
    private ArrayList<User> members;
    public Chatroom(String id,String name) {
        this.id = id;
        this.name = name;
        this.members = new ArrayList<User>();
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

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public void addMembers(User member) {
        this.members.add(member);
    }

    public int getMemNumber() {
        return this.members.size();
    }
}
