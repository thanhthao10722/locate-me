package com.example.locateme.helper;

import android.content.Context;

import com.example.locateme.model.Chat;
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
        databaseReference.child("users").child(userId).setValue(user);
    }

    public void writeNewMesseage(Chat chatMessageModel) {
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference()
                .child("messages").child(chatMessageModel.chatId);
        String key = messageRef.push().getKey();
        chatMessageModel.messageId = key;
        messageRef.child(key).setValue(chatMessageModel);
    }
}
