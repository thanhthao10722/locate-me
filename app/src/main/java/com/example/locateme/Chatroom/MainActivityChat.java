package com.example.locateme.Chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.locateme.R;
import com.example.locateme.helper.MyDB;
import com.example.locateme.model.Chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivityChat extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    boolean myMessage = true;
    private List<ChatBubble> ChatBubbles;
    private ArrayAdapter<ChatBubble> adapter;
    private String chatroomId;
    private MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chatroom);

        db = new MyDB(this);

        ChatBubbles = new ArrayList<>();
        loadIntent();
        setProperties();
        setEvent();
    }
        public void setProperties() {
            listView = (ListView) findViewById(R.id.list_msg);
            btnSend = findViewById(R.id.btn_chat_send);
            editText = (EditText) findViewById(R.id.msg_type);

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
                    ChatBubble ChatBubble = new ChatBubble(editText.getText().toString(), false);

                    Chat chat = new Chat();
                    chat.chatId = chatroomId;
                    chat.message = editText.getText().toString();
                    db.writeNewMesseage(chat);

                    ChatBubbles.add(ChatBubble);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                    if (myMessage) {
                        myMessage = false;
                    } else {
                        myMessage = true;
                    }
                }
            }
        });
    }

    public void loadChatHistory() {

    }

    public void loadIntent(){
        Intent intent = getIntent();
        if(intent != null){
            chatroomId = intent.getStringExtra("chatroomId");
        } else {
            Toast.makeText(this,"Something wrong :v" , Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
