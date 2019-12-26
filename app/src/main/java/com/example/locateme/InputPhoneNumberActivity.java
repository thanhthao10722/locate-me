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
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.locateme.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InputPhoneNumberActivity extends AppCompatActivity {
    Button btn_send;
    EditText mEdit_phone;
    int MY_PERMISSION_REQUEST_SEND_SMS=1;
    String SENT = "SMS SENT";
    String DELIVERED = "SMS DELIVERED";
    PendingIntent sentPI, deliverPI;
    TextView noti;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_phonenumber_page);
        btn_send = findViewById(R.id.btn_sendCode);
        mEdit_phone =  findViewById(R.id.mEdit_Phone);
        noti = findViewById(R.id.noti);

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
    public boolean isValid(String phoneNo)
    {
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;
    }
    public void SendSMS(View v)
    {
        final String phone = mEdit_phone.getText().toString();
        final String code = Integer.toString((int) Math.floor(((Math.random() * 899999) + 100000)));
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSION_REQUEST_SEND_SMS);
        }
        else
        {
            if (isValid(phone))
            {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        boolean check = false;
                        ArrayList<User> listUser = new ArrayList<>();
                        for(DataSnapshot item : dataSnapshot.getChildren())
                        {
                            User user = item.getValue(User.class);
                            listUser.add(user);
                        }
                        for(User user : listUser)
                        {
                            if(user.getPhone().equals(phone) & user.getStatus().equals("active"))
                            {
                                check = true;
                            }
                        }
                        if(check == true)
//                            Toast.makeText(InputPhoneNumberActivity.this, "Your phone number is registered with another account!", Toast.LENGTH_LONG).show();
                            noti.setText("Your phone number is registered with another account.");
                        else
                        {
                            StringBuilder sb = new StringBuilder(phone);
                            String phone_format = sb.deleteCharAt(0).toString();
                            phone_format = "84" + phone_format;
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage(phone_format, null, "Your verification passcode is " + code, sentPI, deliverPI);
                            Intent intent = new Intent(getApplicationContext(), ValidationActivity.class);
                            intent.putExtra("phone", mEdit_phone.getText().toString());
                            intent.putExtra("code", code);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
            }
            else {
//                Toast.makeText(InputPhoneNumberActivity.this, "Your phone number does not exist", Toast.LENGTH_LONG).show();
                noti.setText("Your phone number does not exist.");
            }
        }
    }
    public boolean isExisted(ArrayList<User> listUser, String phone)
    {
        boolean check = false;
        for (User user : listUser)
        {
            if(user.phone.equals(phone))
                check = true;
        }
        return check;
    }
    public void backButton(View v) {
        finish();
    }
}
