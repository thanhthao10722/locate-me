package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locateme.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity
{
    private EditText mEdit_OldPassword,mEdit_NewPassword,mEdit_Validation;
    private Button mButton_Change;
    private String userId;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        mEdit_OldPassword = findViewById(R.id.mEdit_OldPassword);
        mEdit_NewPassword = findViewById(R.id.mEdit_Password);
        mEdit_Validation = findViewById(R.id.mEdit_rePassword);
        mButton_Change = findViewById(R.id.btn_change_password);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        mAuth = FirebaseAuth.getInstance();
        addEvent();
    }
    public void addEvent() {
        mButton_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
    }
    public void updatePassword() {
        final String oldPassword = mEdit_OldPassword.getText().toString();
        final String newPassword = mEdit_NewPassword.getText().toString();
        final String validation = mEdit_Validation.getText().toString();
        if(oldPassword.length() < 0 || newPassword.length() < 0 || validation.length() < 0) {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        }else
            {
                final FirebaseUser current_user = mAuth.getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

                databaseReference.child(current_user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.exists())
                        {
                            User user = new User();
                            user = dataSnapshot.getValue(User.class);
                            if(oldPassword.equals(user.password))
                            {
                                if(newPassword.equals(validation))
                                {
                                    user.setPassword(newPassword);
                                    databaseReference.child(current_user.getUid()).setValue(user);
                                    current_user.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ChangePasswordActivity.this, "Update password succesfully", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                                else
                                {

                                    Toast.makeText(ChangePasswordActivity.this, "Confirm your password failed", Toast.LENGTH_LONG).show();

                                }
                            }
                            else
                            {
                                Toast.makeText(ChangePasswordActivity.this, "Your password is incorrect", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        }
    }
}
