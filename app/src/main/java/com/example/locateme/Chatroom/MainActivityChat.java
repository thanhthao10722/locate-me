package com.example.locateme.Chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.FriendListActivity;
import com.example.locateme.MainActivity;
import com.example.locateme.MapActivity;
import com.example.locateme.R;
import com.example.locateme.model.Chat;
import com.example.locateme.model.Chatroom;
import com.example.locateme.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.example.locateme.helper.MyDB;
import com.example.locateme.model.Chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivityChat extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;
    private String chatroomId;
    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("chatlist");
    private MyDB db;
    private Button btn_friendLabel;
    private Button mAddToChatroomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chatroom);

        db = new MyDB(this);

//        btn_friendLabel = findViewById(R.id.add_friend_to_chatroom);
//
//        btn_friendLabel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference dbChatListRef = FirebaseDatabase.getInstance().getReference();
//                String keyFriend = dbChatListRef.child("users").child("uid_current_user").child("friend").child("UID_CLICK").getKey();
//
//                dbChatListRef.child("chatlist").child(chatroomId).child("users").child(keyFriend).setValue(1);
//            }
//        });

        ChatBubbles = new ArrayList<>();
        loadIntent();
        setProperties();
        setEvent();
        receiveNewMessage();
    }
        public void setProperties() {
            listView = (ListView) findViewById(R.id.list_msg);
            btnSend = findViewById(R.id.btn_chat_send);
            editText = (EditText) findViewById(R.id.msg_type);
            mAddToChatroomBtn = findViewById(R.id.add_friend_to_chatroom);
            //set ListView adapter first
            loadChatHistory();
            adapter = new MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles);
            listView.setAdapter(adapter);
        }

        public void setEvent(){
        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivityChat.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list

                    String chatId = dbReference.child(chatroomId).push().getKey();

                    Message message = new Message();
                    message.setId(chatId);
                    message.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    message.setContent(editText.getText().toString());
                    dbReference.child(chatId).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                editText.setText("");
                            }else {

                            }
                        }
                    });

                }
            }
        });
        mAddToChatroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityChat.this, FriendListActivity.class);
                intent.putExtra("ChatroomId",chatroomId);
                startActivity(intent);
            }
        });
    }

    public void loadChatHistory() {

    }

    public void loadIntent(){
        Intent intent = getIntent();
        if(intent != null){
            chatroomId = intent.getStringExtra("chatroomId");
            dbReference = dbReference.child(chatroomId).child("content");
        } else {
            Toast.makeText(this,"Something wrong :v" , Toast.LENGTH_LONG).show();
            finish();
        }
    }
    private void receiveNewMessage() {
        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("new message",dataSnapshot.getValue(Message.class).getContent());
                addNewMessageToListview(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addNewMessageToListview(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void addNewMessageToListview(Message message) {
        Log.d("TESST", message.getUserId());
        if(!message.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            ChatBubble ChatBubble = new ChatBubble(message.getContent(), false);
            ChatBubbles.add(ChatBubble);
            adapter.notifyDataSetChanged();
        }else {
            ChatBubble ChatBubble = new ChatBubble(message.getContent(), true);
            ChatBubbles.add(ChatBubble);
            adapter.notifyDataSetChanged();
        }
    }
}
