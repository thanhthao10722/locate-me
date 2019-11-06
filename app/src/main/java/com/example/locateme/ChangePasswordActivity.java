package com.example.locateme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText mEdit_OldPassword,mEdit_NewPassword,mEdit_Validation;
    private Button mButton_Change;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        mEdit_OldPassword = findViewById(R.id.mEdit_OldPassword);
        mEdit_NewPassword = findViewById(R.id.mEdit_Password);
        mEdit_Validation = findViewById(R.id.mEdit_rePassword);
        mButton_Change = findViewById(R.id.btn_change_password);
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
        String oldPassword = mEdit_OldPassword.getText().toString();
        String newPassword = mEdit_NewPassword.getText().toString();
        String validation = mEdit_Validation.getText().toString();
        if(oldPassword.length() < 0 || newPassword.length() < 0 || validation.length() < 0) {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG).show();
        }else {
            //
        }
    }
}
