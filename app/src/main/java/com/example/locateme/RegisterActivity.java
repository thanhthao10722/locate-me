package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.helper.MyDB;
import com.example.locateme.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEdit_Name;
    private EditText mEdit_Password;
    String name;
    String password;
    String phone;
    public MyDB db;
    private TextView error;
    Date date;
    SimpleDateFormat formatter;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        mEdit_Name = (EditText)findViewById(R.id.mEdit_Fullname);
        mEdit_Password = (EditText)findViewById(R.id.mEdit_Password);
        error = findViewById(R.id.material_text_button);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        mAuth = FirebaseAuth.getInstance();
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
            error.setText("Your name must not be blank");
        }
        else if(password.equals(""))
        {
            error.setText("Your password must not be blank");
        }
        else
        {
            date = new Date();
            final User user = new User(phone, password, name, "active", formatter.format(date), "", "", "");
            mAuth.createUserWithEmailAndPassword(phone + "@gmail.com", password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                error.setText("Success");
                                createUserInDatabase(user);
                            } else {
                                error.setText("Fail");
                            }
                        }
                    });
        }
    }
    private void createUserInDatabase(final User newUser) {
        mAuth.signInWithEmailAndPassword(phone + "@gmail.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            final String uId = mAuth.getCurrentUser().getUid();
                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
                            databaseReference.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.exists()) {
                                        if(newUser!=null) {
                                            newUser.setId(uId);
                                            databaseReference.child(uId).setValue(newUser);
                                        }
                                    }

                                    moveToLogin();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                        else {
                            error.setText("Your password or phone number is incorrect.");
                        }
                    }
                });
    }
    private void moveToLogin() {
        Intent intent = new Intent(this, MainProfileActivity.class);
        startActivity(intent);
    }
}
