package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InputPhoneNumberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_phonenumber_page);
    }
    public void sendCode(View v) {
        EditText mEdit_phone = (EditText)findViewById(R.id.mEdit_Phone);
        String phone = mEdit_phone.getText().toString();
        if(phone.length() < 9) {
        } else {
            Intent register = new Intent(this,ValidationActivity.class);
            register.putExtra("phone",phone);
            startActivity(register);
        }
    }
    public void backButton(View v) {
        finish();
    }
}
