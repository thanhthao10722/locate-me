package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class LoginActivity extends AppCompatActivity {
    private EditText mEditPhone;
    private EditText mEditPassword;
    private Button btnLogin;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mEditPhone = (EditText)findViewById(R.id.usrusr);
        mEditPassword = (EditText)findViewById(R.id.pswrdd);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEditPhone.getText().toString();
                String password = mEditPassword.getText().toString();
                if(phone.matches("") || password.matches("")) {
                    Toast.makeText(LoginActivity.this, "The data is missing!", Toast.LENGTH_LONG).show();
                }
                logIn(phone, password);
            }
        });
    }

    public void logIn(final String phone, final String password)
    {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                boolean check = false;
                ArrayList<User> listUser = new ArrayList<>();
                for(DataSnapshot item : dataSnapshot.getChildren())
                {
                    User user = item.getValue(User.class);
                    listUser.add(user);
                }
                for(User user : listUser)
                {
                    if(user.getStatus().equals("deactive"))
                    {
                        Toast.makeText(LoginActivity.this, "Your account is deactive", Toast.LENGTH_LONG).show();
                    }
                    else if(user.getStatus().equals("active"))
                    {
                        if(user.getPhone().equals(phone) & user.getPassword().equals(password))
                        {
                            check = true;
                        }
                    }
                }
                if(check == true)
                {
                    Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Your password or Your phone number is incorrect.Try again!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void logIn(final String phone, final String password) {
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child(phone).exists()) {
//                    if(!phone.isEmpty()) {
//                        User user = dataSnapshot.child(phone).getValue(User.class);
//                        if(user.getPassword().equals(password)) {
//                            Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(LoginActivity.this, "User is not registered", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "User is not register2", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void forgotPasswordOnClick(View view){
        Intent forgotPass = new Intent(this,ForgotActivity.class);
        this.startActivity(forgotPass);
    }
    public void backButton(View v) {
        Intent register = new Intent(this,MainActivity.class);
        startActivity(register);
    }
    public void moveToRegister(View v) {
        Intent register = new Intent(this,InputPhoneNumberActivity.class);
        startActivity(register);
    }
}
