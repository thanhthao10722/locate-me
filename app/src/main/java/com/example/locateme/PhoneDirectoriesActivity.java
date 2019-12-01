package com.example.locateme;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.Adapter.ContactAdapter;
import com.example.locateme.model.Contact;
import com.example.locateme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhoneDirectoriesActivity extends AppCompatActivity {

    private ArrayList<Contact> contactList;
    private ArrayList<User> userList;
    private ArrayList<Contact> suggestionList;
    private ArrayList<String> friendList;
    private ListView mListViewContacts;
    private ContactAdapter adapter;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_directories);
        initComponent();
        mAuth = FirebaseAuth.getInstance();
        loadContacts();
    }

    private void initComponent() {
        contactList = new ArrayList<Contact>();
        userList = new ArrayList<User>();
        suggestionList = new ArrayList<Contact>();
        friendList = new ArrayList<String>();
        adapter = new ContactAdapter(this, R.layout.contact_adapter, suggestionList);
        mListViewContacts = findViewById(R.id.contact_list);
        mListViewContacts.setAdapter(adapter);
    }

    private void loadContacts() {
        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phoneCursor.moveToNext()) {
            String name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactList.add(new Contact(name, phoneNumber));
        }
        FirebaseUser current_user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.child(current_user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot item: dataSnapshot.getChildren())
                    friendList.add(item.getValue(String.class));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren())
                    userList.add(item.getValue(User.class));
                for (User user : userList) {
                    if (friendList.contains(user.getId()))
                    {
                        userList.remove(user);
                    }

                }
                for (User user : userList) {
                    for (Contact contact : contactList) {
                        if (user.getPhone().equals(contact.getPhone())) {
                            Contact suggestion = new Contact(user.getName(), contact.getPhone());
                            suggestion.setId(user.getId());
                            suggestion.setImageSource(user.getPhotourl());
                            suggestionList.add(suggestion);
                        }
                    }
                }
                System.out.print("================="+ suggestionList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




    }

    private void addFriend(View v) {
    }
}
