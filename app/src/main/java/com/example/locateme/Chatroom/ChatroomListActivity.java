package com.example.locateme.Chatroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.locateme.Adapter.ChatroomAdapter;
import com.example.locateme.R;
import com.example.locateme.model.Chatroom;
import com.example.locateme.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatroomListActivity extends AppCompatActivity {

    private ListView mLv_Chatroom;
    private ImageView mBackButton;
    private ChatroomAdapter adapter;
    private ArrayList<Chatroom> list;
    private DatabaseReference dbReference;
    private FloatingActionButton mAddChatroom_Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);
        setProperties();
        setChatroomAdapter();
    }

    public void dataInit() {
        Chatroom cr1 = new Chatroom("abc01","Hoi Tam Ke");
        cr1.addMembers(new User());
        list.add(cr1);
    }

    public void setProperties() {
        this.mLv_Chatroom = findViewById(R.id.Chatroom_listview);
        this.mBackButton = findViewById(R.id.back_button);
        mAddChatroom_Btn = findViewById(R.id.add_chatroom_button);
    }

    public void setChatroomAdapter() {
        dataInit();
        getChatroomList();
        adapter = new ChatroomAdapter(this,R.layout.chatroom_adapter,list);
        mLv_Chatroom.setAdapter(adapter);

    }

    public void getChatroomList() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }

    public void setEvent() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAddChatroom_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatroomListActivity.this,AddChatroomActivity.class);
            }
        });
        mLv_Chatroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chatroomId = list.get(position).getId();
            }
        });
    }
}
