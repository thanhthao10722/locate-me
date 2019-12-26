package com.example.locateme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.Util.MapUtil;
import com.example.locateme.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private MapUtil map;
    private ImageView mIcAddress;
    DatabaseReference databaseReference;
    TextView noti;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);
        mEdit_Phone = findViewById(R.id.mEdit_Phone);
        mEdit_Name = findViewById(R.id.mEdit_Name);
        mEdit_Address = findViewById(R.id.mEdit_Address);
        mButton_Update = findViewById(R.id.btn_update);
        mButton_Password = findViewById(R.id.btn_changePassword);
        mIcAddress = findViewById(R.id.ic_address);
        map = new MapUtil(UpdateProfileActivity.this);
        noti = findViewById(R.id.material_text_button);
        mIcAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.loadLocation();
                fetchAddress();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        databaseReference.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);
                mEdit_Phone.setText(user.getPhone());
                mEdit_Name.setText(currentUser.getDisplayName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        setEvent();

        fetchAddress();
    }
    public void setmButton_Update() {
        final String name = mEdit_Name.getText().toString();
        if(name.equals(""))
            noti.setText("Please fill all the blanks");
        else
            {
                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                currentUser.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                databaseReference.child(currentUser.getUid()).child("name").setValue(name);
            finish();
        }

    }
    public void setmButton_Password() {
        Intent sent_intent = new Intent(this,ChangePasswordActivity.class);
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
        Intent intent = new Intent(this,MainProfileActivity.class);
        startActivity(intent);
    }
    public void fetchAddress() {
        if(map.currentLocation !=null) {
            mEdit_Address.setText(map.getSubAddress());
        } else {
            Toast.makeText(this,"Problems with your GPS system",Toast.LENGTH_LONG).show();
        }
    }
}
