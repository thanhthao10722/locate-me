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
        String userId = mDatabase.push().getKey();
        User user = new User("0963443189", "1806", "Thanh Thao", true, "", "", "");
        mDatabase.child("users").child(userId).setValue(user);
    }
}
