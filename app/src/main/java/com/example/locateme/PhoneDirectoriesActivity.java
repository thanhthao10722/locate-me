package com.example.locateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.locateme.Adapter.ContactAdapter;
import com.example.locateme.model.Contact;
import com.example.locateme.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.UnresolvedAddressException;
import java.util.ArrayList;

public class PhoneDirectoriesActivity extends AppCompatActivity {

    private ArrayList<String> contactList;
    private ArrayList<User> userList;
    private ArrayList<String> friendList;
    private ListView mListViewContacts;
    private ContactAdapter adapter;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private FloatingActionButton btn_addFriend;
    private FirebaseUser current_user;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_phone_directories);
            initComponent();
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        loadContacts();
    }
    private void initComponent() {
        btn_addFriend = findViewById(R.id.btn_addFriend);
        contactList = new ArrayList<String>();
        userList = new ArrayList<User>();
        friendList = new ArrayList<String>();
        adapter = new ContactAdapter(this, R.layout.contact_adapter, userList);
        mListViewContacts = findViewById(R.id.contact_list);
        mListViewContacts.setAdapter(adapter);
    }

    public void  requestFriend(View v) {
        RelativeLayout vwParentRow = (RelativeLayout) v.getParent();
        int index = (int) mListViewContacts.getPositionForView(vwParentRow);
        User addFriend = userList.get(index);

        databaseReference.child(addFriend.getId()).child("friendrequest").child(current_user.getUid()).setValue(current_user.getUid());
        databaseReference.child(current_user.getUid()).child("invatationsent").child(addFriend.getId()).setValue(addFriend.getId());
        userList.remove(userList.get(index));
        adapter.notifyDataSetChanged();
    }
    private void loadContacts() {
        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phoneCursor.moveToNext()) {
            String name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactList.add(phoneNumber);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.child(current_user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                    if(dataSnapshot.exists())
                        friendList.add(item.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference.child(current_user.getUid()).child("friendrequest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                    if(dataSnapshot.exists())
                        friendList.add(item.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference.child(current_user.getUid()).child("invatationsent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                    if(dataSnapshot.exists())
                        friendList.add(item.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Log.d("FRIEND LIST", friendList.toString());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren())
                    userList.add(item.getValue(User.class));
                for(int i = 0; i < userList.size(); i++)
                {
                   if(friendList.contains(userList.get(i).getId()))
                   {
                       userList.remove(userList.get(i));
                   }
                }
                for (int i = 0; i<userList.size(); i++)
                {
                    if (!contactList.contains(userList.get(i).getPhone()) || userList.get(i).getId().equals(current_user.getUid()))
                    {
                        userList.remove(userList.get(i));
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void onClickAddFriend(View v) {
        Intent intent = new Intent(PhoneDirectoriesActivity.this, AddFriendActivity.class);
        startActivity(intent);
    }
}
