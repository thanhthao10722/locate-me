package com.example.locateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.locateme.Adapter.AddToChatroomAdapter;
import com.example.locateme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {
    private ArrayList<User> friendList = new ArrayList<User>();
    private ArrayList<String> friendsId = new ArrayList<>();
    private ListView mLvFriendList;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private AddToChatroomAdapter adapter;
    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("users");
    private ProgressDialog dialog;
    private String chatroomId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading your friend list");
        dialog.setMessage("Please wait ...");
        dialog.setCancelable(false);
        dialog.show();

        loadIntent();

        mLvFriendList = findViewById(R.id.friend_list_listview);
        adapter = new AddToChatroomAdapter(this,R.layout.add_to_chatroom_adapter,friendList);
        mLvFriendList.setAdapter(adapter);
        loadFriendListId();
    }

    private void loadIntent() {
        Intent intent = getIntent();
        if(intent != null) {
            chatroomId = intent.getStringExtra("ChatroomId");
        }
    }

    private void loadFriendListId() {
        dbReference.child(userId).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        friendsId.add(i.getKey());
                    }
                    loadFriendList();
                } else {
                    dialog.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void loadFriendList() {
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        if (friendsId.contains(i.getKey())) {
                            friendList.add(i.getValue(User.class));
                        }
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
}
