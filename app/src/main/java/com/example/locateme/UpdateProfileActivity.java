package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText mEdit_Phone;
    private EditText mEdit_Name;
    private EditText mEdit_Address;
    private Button mButton_Update;
    private Button mButton_Password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mEdit_Phone = findViewById(R.id.mEdit_Phone);
        mEdit_Name = findViewById(R.id.mEdit_Name);
        mEdit_Address = findViewById(R.id.mEdit_Address);
        mButton_Update = findViewById(R.id.btn_update);
        mButton_Password = findViewById(R.id.btn_change_password);
        setEvent();
    }
    public void setmButton_Update() {
        //update message
    }
    public void setmButton_Password() {
        Intent intent = new Intent(this,ChangePasswordActivity.class);
        startActivity(intent);
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
}
