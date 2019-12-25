package com.example.locateme.Chatroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import com.example.locateme.Adapter.ChatroomAdapter;
import com.example.locateme.R;
import com.example.locateme.helper.MyDB;
import com.example.locateme.model.Chatroom;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
    private String userId;
    private FloatingActionButton mAddChatroom_Btn;
    private DatabaseReference databaseReference;
    private ProgressDialog dialog;
    private MyDB db;
    private final String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private SearchView searchView;

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
        loadIntent();
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

    public void setProperties() {
        this.mLv_Chatroom = findViewById(R.id.Chatroom_listview);
        this.mBackButton = findViewById(R.id.back_button);
        mAddChatroom_Btn = findViewById(R.id.add_chatroom_button);
        searchView = findViewById(R.id.searchbox);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Filter",newText);
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    public void setChatroomAdapter() {
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
                if(!listFriend.contains(chatroom))
                    listFriend.add(chatroom);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void getChatroomList() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("chatlist");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()) {
//                    for( DataSnapshot i : dataSnapshot.getChildren()) {
//                        checkId(i);
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                checkId(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                checkId(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                checkIdToRemove(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void checkIdToRemove(DataSnapshot i) {
        for ( DataSnapshot y : i.child("users").getChildren()) {
            if(y.getKey().equals(uId))
            {
                Chatroom chatroom =  new Chatroom();
                chatroom.setName(i.child("name").getValue().toString());
                chatroom.setId(i.getKey());
                if(listFriend.contains(chatroom))
                    listFriend.remove(chatroom);
            }
        }
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
