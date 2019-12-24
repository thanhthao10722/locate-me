package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ValidationActivity extends AppCompatActivity {
    private EditText mEdit_Phone;
    private EditText mEdit_Code;
    private String phone;
    private String code;
    private TextView noti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_phonenumber_page);
        mEdit_Phone = (EditText)findViewById(R.id.mEdit_Phone);
        mEdit_Code = (EditText)findViewById(R.id.mEdit_Code);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");
        mEdit_Phone.setText(phone);
    }
    public void backButton(View v) {
        finish();
    }
    public void onConfirmButton(View v)
    {
        String otpCode = mEdit_Code.getText().toString();
        if(otpCode.length() > 0)
        {
            if(otpCode.equals(code))
            {
                Intent register = new Intent(this, RegisterActivity.class);
                register.putExtra("phone",phone);
                startActivity(register);
            }
            else
            {
//                Toast.makeText(ValidationActivity.this, "Your OTP code is not correct. Please try again!", Toast.LENGTH_LONG).show();
                noti.setText("Your OTP code is not correct. Please try again.");
            }
        }
        else
        {
//            Toast.makeText(ValidationActivity.this, "Please enter code to validate your account", Toast.LENGTH_LONG);
            noti.setText("Please enter code to validate your account.");
        }
    }
}
