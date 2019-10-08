package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText mEditPhone;
    private EditText mEditPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mEditPhone = (EditText)findViewById(R.id.mEdit_Phone);
        mEditPassword = (EditText)findViewById(R.id.mEdit_Password);
        Intent intent =getIntent();
        if(intent!=null) {
            String name = intent.getStringExtra("name");
            String phone = intent.getStringExtra("phone");
            String password = intent.getStringExtra("password");
            if(name != null && phone != null && password != null) {
                Toast.makeText(this,phone + "  " + password,Toast.LENGTH_LONG).show();
                mEditPhone.setText(phone);
                mEditPassword.setText(password);
            }
        }
    }
    public void loginOnClick(View v) {
        String phone = mEditPhone.getText().toString();
        String password = mEditPassword.getText().toString();
        Log.d("mess",phone + "  " + password);
        if(phone.equals("0123456789") && password.equals("123456")) {
            Intent success = new Intent(this,ProfileActivity.class);
            Toast.makeText(this,"login success",Toast.LENGTH_LONG).show();
            success.putExtra("phone",phone);
            success.putExtra("name","Thinh");
            success.putExtra("location","Danang - Vietnam");
            startActivity(success);
        }
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
