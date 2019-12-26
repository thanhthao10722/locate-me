package com.example.locateme.Chatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.locateme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddChatroomActivity extends AppCompatActivity {

    private ImageView mOkButton;
    private EditText mEdit_Chatroom;
    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("chatlist");
    private String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chatroom);
        setProperties();
        setEvent();
    }

    public void setProperties() {
//        this.mBackButton = findViewById(R.id.back_button);
        this.mOkButton = findViewById(R.id.done_button);
        this.mEdit_Chatroom = findViewById(R.id.edit_chatroom_name);
    }

    public void setEvent() {
//        mBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToChatroomList();
            }
        });
    }

    public void addToChatroomList() {
        String newNode = dbReference.push().getKey();
        dbReference.child(newNode).child("name").setValue(mEdit_Chatroom.getText().toString());
        dbReference.child(newNode).child("users").child(uId).setValue(1);
        finish();
    }
}
