package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ValidationActivity extends AppCompatActivity {
    private EditText mEdit_Phone;
    private EditText mEdit_Code;
    private String phone;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_phonenumber_page);
        mEdit_Phone = (EditText)findViewById(R.id.mEdit_Phone);
        mEdit_Code = (EditText)findViewById(R.id.mEdit_Code);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone").toString();
        mEdit_Phone.setText(phone);
    }
    public void backButton(View v) {
        finish();
    }
    public void onConfirmButton(View v) {
        code = mEdit_Code.getText().toString();
        if(code.length() > 0) {
            Intent register = new Intent(this,RegisterActivity.class);
            register.putExtra("phone",phone);
            startActivity(register);
        }
    }
}
