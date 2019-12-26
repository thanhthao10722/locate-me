package com.example.locateme.helper;

import android.content.Context;

import com.example.locateme.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyDB {
    private DatabaseReference databaseReference;
    private Context context;

    public MyDB(Context context) {
        this.context = context;
    }

    public void writeNewUser(User user) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String userId = databaseReference.push().getKey();
        databaseReference.child("user").child(userId).setValue(user);
    }

}
