package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotActivity extends AppCompatActivity {
    EditText mEdit_newPassword,mEdit_rePassword;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_passsword);
        mEdit_newPassword = (EditText) findViewById(R.id.mEdit_Password);
        mEdit_rePassword = (EditText) findViewById(R.id.mEdit_rePassword);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void changingOnClick(View v){
        String newPassword = mEdit_newPassword.getText().toString();
        String reNewPassword = mEdit_rePassword.getText().toString();
        if(mEdit_newPassword.equals(reNewPassword)){
            Toast.makeText(this,"Your password is changed",Toast.LENGTH_LONG).show();
        }
        Intent changing = new Intent(this,LoginActivity.class);
        this.startActivity(changing);
    }


}
