package com.example.locateme.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chatroom implements Serializable {
    private String id;
    private String name;
    private ArrayList<String> members;
    public Chatroom(String id,String name) {
        this.id = id;
        this.name = name;
        this.members = new ArrayList<String>();
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

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public void addMembers(String memberId) {
        this.members.add(memberId);
    }

    public int getMemNumber() {
        return this.members.size();
    }
}
