package com.example.locateme.helper;

import android.content.Context;

import com.example.locateme.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyDB
{
    private DatabaseReference mDatabase;
    private Context context;

    public MyDB(Context context) {
       this.context = context;
    }

    public void writeNewUser(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //String userId = mDatabase.push().getKey();
        User user = new User("0708539115", "2411", "Thuy Duong", true, "", "", "");
        mDatabase.child("users").child("0708539115").setValue(user);
    }
}
