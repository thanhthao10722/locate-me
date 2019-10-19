package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.helper.MyDB;
import com.example.locateme.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEdit_Name;
    private EditText mEdit_Password;
    String name;
    String password;
    String phone;
    public MyDB db;
    Date date;
    SimpleDateFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        mEdit_Name = (EditText)findViewById(R.id.mEdit_Fullname);
        mEdit_Password = (EditText)findViewById(R.id.mEdit_Password);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone").toString();
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        db = new MyDB(this);
    }
    public void backButton(View v) {
        finish();
    }
    public void registerOnClick(View v)
    {
        name = mEdit_Name.getText().toString();
        password = mEdit_Password.getText().toString();
        if(name.equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Your name must not be blank", Toast.LENGTH_LONG).show();
        }
        else if(password.equals(""))
        {
            Toast.makeText(RegisterActivity.this, "Your password must not be blank", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent success = new Intent(this, LoginActivity.class);
            date = new Date();
            User user = new User(phone, password, name, "active", formatter.format(date), "", "");
            db.writeNewUser(user);
            Toast.makeText(RegisterActivity.this, "Create account successfully", Toast.LENGTH_LONG).show();
            startActivity(success);
        }
    }
}
