package com.example.locateme.Chatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.locateme.Adapter.ChatroomAdapter;
import com.example.locateme.R;
import com.example.locateme.helper.MyDB;
import com.example.locateme.model.Chat;
import com.example.locateme.model.Chatroom;
import com.example.locateme.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatroomListActivity extends AppCompatActivity {

    private ListView mLv_Chatroom;
    private ImageView mBackButton;
    private ChatroomAdapter adapter;
    private ArrayList<Chatroom> listFriend = new ArrayList<Chatroom>();
    private DatabaseReference dbReference;
    private String userId;
    private FloatingActionButton mAddChatroom_Btn;
    private DatabaseReference databaseReference;
    private ArrayList<User> users;
    private ProgressDialog dialog;
    private MyDB db;
    private final String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String currentPhone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom_list);

        db = new MyDB(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading...");
        dialog.setMessage("Wait...");
        dialog.setCancelable(true);
        dialog.show();
        users = new ArrayList<>();
        loadIntent();

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int i = 0;
//                for(DataSnapshot item : dataSnapshot.getChildren())
//                {
//                    User user = item.getValue(User.class);
//                    users.add(user);
//
//                    if(item.getKey().equals(uId)) {
//                        currentPhone = user.getPhone();
//                    }
//                }
//                dialog.dismiss();
//                loadIntent();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    private void loadIntent() {
        Intent intent = getIntent();
        if(intent != null) {
            userId = intent.getStringExtra("user_id");
            setProperties();
            setEvent();
            setChatroomAdapter();
        }else {
            finish();
        }
    }


    public void dataInit() {
        Log.d("currentPhone", currentPhone);
        ArrayList<String> phonesInDirectory = new ArrayList<>();
        phonesInDirectory.add("0865389417");
        phonesInDirectory.add("0963443189");
        phonesInDirectory.add("0935336719");
        for (User user : users) {
            Log.d("TEST", user.phone);
//            if(phonesInDirectory.contains(user.phone) && !phonesInDirectory.contains(currentPhone)) {
                Chatroom cr = new Chatroom(currentPhone
                        + "-" + user.phone, user.name);
                listFriend.add(cr);
//            }
        }
        Log.d("lsFR", listFriend.toString());
    }

    public void setProperties() {
        this.mLv_Chatroom = findViewById(R.id.Chatroom_listview);
        this.mBackButton = findViewById(R.id.back_button);
        mAddChatroom_Btn = findViewById(R.id.add_chatroom_button);
    }

    public void setChatroomAdapter() {
        dataInit();
        getChatroomList();
        adapter = new ChatroomAdapter(this,R.layout.chatroom_adapter,listFriend);
        mLv_Chatroom.setAdapter(adapter);

    }

    private void checkId(DataSnapshot i) {
        for ( DataSnapshot y : i.child("users").getChildren()) {
            if(y.getKey().equals(uId))
            {
                Chatroom chatroom =  new Chatroom();
                chatroom.setName(i.child("name").getValue().toString());
                chatroom.setId(i.getKey());
                listFriend.add(chatroom);
            }
        }
    }

    public void getChatroomList() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("chatlist");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for( DataSnapshot i : dataSnapshot.getChildren()) {
                        checkId(i);
                    }
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                startActivity(intent);
            }
        });
        mLv_Chatroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chatroomId = listFriend.get(position).getId();
                Intent intent = new Intent(ChatroomListActivity.this,MainActivityChat.class);
                intent.putExtra("chatroomId",chatroomId);
                startActivity(intent);
            }
        });
    }
}
