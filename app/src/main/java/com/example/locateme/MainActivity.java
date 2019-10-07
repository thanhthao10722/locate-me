package com.example.locateme;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
    }
    public void moveToLogin(View v) {
        Intent login = new Intent(this,LoginActivity.class);
        this.startActivity(login);
    }
    public void backButton(View v) {
        finish();
    }
}
