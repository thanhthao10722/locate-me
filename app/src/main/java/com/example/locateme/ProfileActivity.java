package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.model.User;

public class ProfileActivity extends AppCompatActivity {
    private TextView name;
    private TextView phone;
    private TextView address;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.profile_name);
        phone = findViewById(R.id.profile_phone);
        address = findViewById(R.id.profile_location);
        Intent intent = getIntent();
        if(intent!= null) {
            Bundle bundle = intent.getBundleExtra("login");
            User user = (User)bundle.getSerializable("user");
            name.setText(user.getName());
            phone.setText(user.getPhone());
        }
    }
}
