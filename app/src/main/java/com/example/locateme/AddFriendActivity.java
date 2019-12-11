package com.example.locateme;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.Adapter.AddFriendAdapter;
import com.example.locateme.Adapter.ContactAdapter;
import com.example.locateme.model.AddFriend;
import com.example.locateme.model.Contact;
import com.example.locateme.model.User;


import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
    private ArrayList<AddFriend> addFriends;
    private ArrayList<AddFriend> suggestionList;
    private ArrayList<String> friendList;
    private ListView mListViewFriends;
    private AddFriendAdapter adapter;
    private Button btn_accept,btn_decline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend_list);
        initComponent();
        loadData();
    }

    private void initComponent(){
        btn_accept = findViewById(R.id.btn_accept);
        btn_decline = findViewById(R.id.btn_decline);
        addFriends = new ArrayList<AddFriend>();
        suggestionList = new ArrayList<AddFriend>();
        friendList = new ArrayList<String>();
        adapter = new AddFriendAdapter(this, R.layout.add_friend_adapter, suggestionList);
        mListViewFriends = findViewById(R.id.add_friend_list);
        mListViewFriends.setAdapter(adapter);
    }

    private void loadData(){
        AddFriend addFriend = new AddFriend("Thinh","09123");
        addFriends.add(addFriend);
    }
}
