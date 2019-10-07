package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEdit_Name;
    private EditText mEdit_Password;
    String name;
    String password;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        mEdit_Name = (EditText)findViewById(R.id.mEdit_Fullname);
        mEdit_Password = (EditText)findViewById(R.id.mEdit_Password);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone").toString();
    }
    public void backButton(View v) {
        finish();
    }
    public void registerOnClick(View v) {
        name = mEdit_Name.getText().toString();
        password = mEdit_Password.getText().toString();
        Intent success = new Intent(this,LoginActivity.class);
        success.putExtra("name",name);
        success.putExtra("password",password);
        success.putExtra("phone",phone);
        startActivity(success);
    }
}
