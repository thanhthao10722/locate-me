package com.example.locateme.Chatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.locateme.R;

public class AddChatroomActivity extends AppCompatActivity {

    private ImageView mBackButton,mOkButton;
    private EditText mEdit_Chatroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chatroom);
        setProperties();
        setEvent();
    }

    public void setProperties() {
        this.mBackButton = findViewById(R.id.back_button);
        this.mOkButton = findViewById(R.id.done_button);
        this.mEdit_Chatroom = findViewById(R.id.edit_chatroom_name);
    }

    public void setEvent() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToChatroomList();
            }
        });
    }

    public void addToChatroomList() {
        finish();
    }
}
