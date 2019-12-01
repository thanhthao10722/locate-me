package com.example.locateme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import com.example.locateme.Adapter.ContactAdapter;
import com.example.locateme.model.Contact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhoneDirectoriesActivity extends AppCompatActivity {

    private ArrayList<Contact> contactsList;
    private ListView mListViewContacts;
    private ContactAdapter adapter;
    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_directories);
        initComponent();
        loadContacts();
    }

    private void initComponent() {
        contactsList = new ArrayList<Contact>();
        adapter = new ContactAdapter(this,R.layout.contact_adapter,contactsList);
        mListViewContacts = findViewById(R.id.contact_list);
        mListViewContacts.setAdapter(adapter);
}

    private void loadContacts() {
        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (phoneCursor.moveToNext()) {
            String name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactsList.add(new Contact(name,phoneNumber));
        }
        adapter.notifyDataSetChanged();
    }

    private void addFriend(View v) {
    }

}
