package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText mEditPhone;
    private EditText mEditPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mEditPhone = (EditText)findViewById(R.id.usrusr);
        mEditPassword = (EditText)findViewById(R.id.pswrdd);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEditPhone.getText().toString();
                String password = mEditPassword.getText().toString();
                Log.d("Error DMM","Login");
                if(phone.matches("") || password.matches("")) {
                    Toast.makeText(LoginActivity.this, "The data is missing!", Toast.LENGTH_LONG).show();
                }
                logIn(phone, password);
            }
        });
        Intent intent = getIntent();
        if(intent!=null) {
            Bundle bundle = intent.getBundleExtra("Success");
            if(bundle!=null) {
                newUser = (User)bundle.getSerializable("NewUser");
            }
        }
    }

    public void logIn(final String phone, final String password)
    {
        mAuth.signInWithEmailAndPassword(phone + "@gmail.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            final String uId = mAuth.getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                            databaseReference.child(uId).addListenerForSingleValueEvent(   new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.exists()) {
                                        if(newUser!=null) {
                                            newUser.setId(uId);
                                            databaseReference.child(uId).setValue(newUser);
                                        }
                                    }
                                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Your password or phone number is incorrect.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

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