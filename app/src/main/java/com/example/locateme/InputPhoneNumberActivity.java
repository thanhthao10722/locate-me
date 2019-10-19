package com.example.locateme;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class InputPhoneNumberActivity extends AppCompatActivity {
    Button btn_send;
    EditText mEdit_phone;
    int MY_PERMISSION_REQUEST_SEND_SMS=1;
    String SENT = "SMS SENT";
    String DELIVERED = "SMS DELIVERED";
    PendingIntent sentPI, deliverPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_phonenumber_page);
        btn_send = findViewById(R.id.btn_sendCode);
        mEdit_phone =  findViewById(R.id.mEdit_Phone);

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliverPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        btn_send.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                SendSMS(view);
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                switch ((getResultCode()))
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(InputPhoneNumberActivity.this,"SMS sent", Toast.LENGTH_LONG);
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(InputPhoneNumberActivity.this,"Generic Fail", Toast.LENGTH_LONG);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(InputPhoneNumberActivity.this,"No service", Toast.LENGTH_LONG);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(InputPhoneNumberActivity.this,"No PDU", Toast.LENGTH_LONG);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(InputPhoneNumberActivity.this,"Radio Off", Toast.LENGTH_LONG);
                        break;
                }
            }
        };
        smsDeliveredReceiver = new BroadcastReceiver()
            {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                switch ((getResultCode()))
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(InputPhoneNumberActivity.this,"SMS sent", Toast.LENGTH_LONG);
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(InputPhoneNumberActivity.this,"SMS not delivered!", Toast.LENGTH_LONG);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(InputPhoneNumberActivity.this,"No service", Toast.LENGTH_LONG);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(InputPhoneNumberActivity.this,"No PDU", Toast.LENGTH_LONG);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(InputPhoneNumberActivity.this,"Radio Off", Toast.LENGTH_LONG);
                        break;
                }
            }
        };
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(smsDeliveredReceiver);
        unregisterReceiver(smsSentReceiver);
    }
    public void SendSMS(View v)
    {
        String phone = mEdit_phone.getText().toString();
        Random random = new Random();
        String code = Integer.toString(random.nextInt(9999));
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSION_REQUEST_SEND_SMS);
        }
        else
        {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, code, sentPI, deliverPI);
        }
    }
    public void backButton(View v) {
        finish();
    }
}
