package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.Chatroom.ChatroomListActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private EditText mEditPhone;
    private EditText mEditPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mEditPhone = (EditText)findViewById(R.id.usrusr);
        mEditPassword = (EditText)findViewById(R.id.pswrdd);
        btnLogin = (Button) findViewById(R.id.btn_Login_of_login_page);
        mAuth = FirebaseAuth.getInstance();
        error = findViewById(R.id.error);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEditPhone.getText().toString();
                String password = mEditPassword.getText().toString();
                logIn(phone, password);
            }
        });
    }

    private void logIn(final String phone, final String password)
    {
        mAuth.signInWithEmailAndPassword(phone + "@gmail.com", password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(LoginActivity.this, MainProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    public void moveToProfilePage() {
        Intent intent = new Intent(this, MainProfileActivity.class);
        startActivity(intent);
    }

    public void forgotPasswordOnClick(View view){
        Intent forgotPass = new Intent(this,ForgotActivity.class);
        this.startActivity(forgotPass);
    }
    public void backButton(View v) {
        finish();
    }
    public void moveToRegister(View v) {
        Intent register = new Intent(this,InputPhoneNumberActivity.class);
        startActivity(register);
    }
    @Override
    public void onBackPressed() {

    }
}