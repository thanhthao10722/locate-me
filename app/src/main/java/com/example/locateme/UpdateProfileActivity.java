package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UpdateProfileActivity extends AppCompatActivity {
    private TextView mEdit_Phone;
    private EditText mEdit_Name;
    private TextView mEdit_Address;
    private Button mButton_Update;
    private Button mButton_Password;
    private User currentUser;
    String userId;
    private String originalName;
    private String originalPhone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);
        mEdit_Phone = findViewById(R.id.mEdit_Phone);
        mEdit_Name = findViewById(R.id.mEdit_Name);
        mEdit_Address = findViewById(R.id.mEdit_Address);
        mButton_Update = findViewById(R.id.btn_update);
        mButton_Password = findViewById(R.id.btn_changePassword);
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("id");
            loadData();
        }
        setEvent();

    }
    public void setmButton_Update() {
        //update message
        String name = mEdit_Name.getText().toString();
        String phone = mEdit_Phone.getText().toString();
        if(name.equals("") && phone.equals("")){
            Toast.makeText(this,"Please fill all the blank", Toast.LENGTH_LONG).show();
        }
        else if(originalPhone.equals(phone) && originalName.equals(name)) {
                return;
            }
        else {
            //update profile
            finish();
        }

    }
    public void setmButton_Password() {
        Intent sent_intent = new Intent(this,ChangePasswordActivity.class);
        sent_intent.putExtra("id", userId);
        this.startActivity(sent_intent);
    }
    public void setEvent() {
        mButton_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmButton_Password();
            }
        });
        mButton_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmButton_Update();
            }
        });
    }

    public void backButton(View v){
        finish();
    }

    public void loadData() {
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                mEdit_Name.setText(currentUser.getName());
                mEdit_Phone.setText(currentUser.getPhone());
                originalName = currentUser.getName();
                originalPhone = currentUser.getPhone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
