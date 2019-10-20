package com.example.locateme.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.locateme.LoginActivity;
import com.example.locateme.ProfileActivity;
import com.example.locateme.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MyDB {
    private DatabaseReference databaseReference;
    private Context context;

    public MyDB(Context context) {
        this.context = context;
    }

    public void writeNewUser(User user) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String userId = databaseReference.push().getKey();
        databaseReference.child("users").child(userId).setValue(user);
    }
}
