package com.example.locateme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.Adapter.AddFriendAdapter;
import com.example.locateme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
    private ArrayList<String> friendList;
    private ListView mListViewFriends;
    private AddFriendAdapter adapter;
    private Button btn_accept, btn_decline;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private FirebaseUser current_user;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend_list);
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        initComponent();
        loadData();
    }

    private void initComponent() {
        btn_accept = findViewById(R.id.btn_accept);
        btn_decline = findViewById(R.id.btn_decline);
        friendList = new ArrayList<String>();
        userList = new ArrayList<User>();
        adapter = new AddFriendAdapter(this, R.layout.add_friend_adapter, userList);
        mListViewFriends = findViewById(R.id.add_friend_list);
        mListViewFriends.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
    }

    public void acceptFriend(View v) {
        RelativeLayout vwParentRow = (RelativeLayout) v.getParent();
        int index = (int) mListViewFriends.getPositionForView(vwParentRow);
        User addFriend = userList.get(index);
        databaseReference.child(current_user.getUid()).child("friend").child(addFriend.getId()).setValue(addFriend.getId());
                databaseReference.child(current_user.getUid()).child("friendrequest").child(addFriend.getId()).removeValue();
                databaseReference.child(addFriend.getId()).child("friend").child(current_user.getUid()).setValue(current_user.getUid());
                userList.remove(userList.get(index));
                adapter.notifyDataSetChanged();
    }
    public void declineFriend(View v) {
        RelativeLayout vwParentRow = (RelativeLayout) v.getParent();
        int index = (int) mListViewFriends.getPositionForView(vwParentRow);
        User addFriend = userList.get(index);
        databaseReference.child(current_user.getUid()).child("friendrequest").child(addFriend.getId()).removeValue();
        userList.remove(userList.get(index));
        adapter.notifyDataSetChanged();
    }
    private void loadData() {
        databaseReference.child(current_user.getUid()).child("friendrequest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                    if (dataSnapshot.exists()) {
                        friendList.add(item.getValue(String.class));
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                    userList.add(item.getValue(User.class));
                for (int i = 0; i < userList.size(); i++) {
                    if (friendList.size() == 0) {
                        userList.remove(userList.get(i));
                        i--;
                    } else {
                        if (!friendList.contains(userList.get(i).getId()) || userList.get(i).equals(current_user.getUid())) {
                            userList.remove(userList.get(i));
                            i--;
                        }
                    }

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
