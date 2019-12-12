package com.example.locateme.Chatroom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locateme.Adapter.MessageBubbleAdapter;
import com.example.locateme.FriendListActivity;
import com.example.locateme.MapActivity;
import com.example.locateme.NoticeDialog;
import com.example.locateme.R;
import com.example.locateme.Util.MapUtil;
import com.example.locateme.model.Message;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.example.locateme.helper.MyDB;
import com.example.locateme.model.Chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class  MainActivityChat extends AppCompatActivity {

    private RecyclerView messageView;
    private View btnSend;
    private EditText editText;
    private ArrayList<Message> ChatBubbles;
    private MessageBubbleAdapter adapter;
    private String chatroomId;
    private DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("chatlist");
    private MyDB db;
    private ImageView mAddToChatroomBtn;
    private ImageButton btnLocation;
    private NoticeDialog noticeDialog;
    private MapUtil mapUtil;
    private Double lat = 0.0;
    private Double lng = 0.0;
    private String userName;
    private TextView txtFriendName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chatroom);

        db = new MyDB(this);

        ChatBubbles = new ArrayList<>();
        loadIntent();
        setProperties();
        setEvent();
        receiveNewMessage();
    }

    public void setProperties() {
        messageView = findViewById(R.id.list_msg);
        messageView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        messageView.setLayoutManager(layoutManager);

        btnSend = findViewById(R.id.btn_chat_send);
        btnLocation = findViewById(R.id.btn_location);
        editText = (EditText) findViewById(R.id.msg_type);
        mAddToChatroomBtn = findViewById(R.id.add_friend_to_chatroom);
        noticeDialog = new NoticeDialog(this);
        txtFriendName = findViewById(R.id.txt_friendName);
        mapUtil = new MapUtil(this);
        //set ListView adapter first
        loadChatHistory();
        adapter = new MessageBubbleAdapter(this, ChatBubbles);
        messageView.setAdapter(adapter);

        final ProgressDialog myProgress = new ProgressDialog(this);
        myProgress.setTitle("Map Loading...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);

        //show progress
        myProgress.show();
        FirebaseDatabase.getInstance().getReference().child("chatlist").child(chatroomId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String friendName = dataSnapshot.getValue(String.class);
                txtFriendName.setText(friendName);
                myProgress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setEvent(){
    //event for button SEND
    btnSend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (editText.getText().toString().trim().equals("")) {
                Toast.makeText(MainActivityChat.this, "Please input some text...", Toast.LENGTH_SHORT).show();
            } else {
                //add message to list

                String chatId = dbReference.child(chatroomId).push().getKey();

                Message message = new Message();
                message.setId(chatId);
                message.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                message.setContent(editText.getText().toString());
                message.setLatLng(false);
                message.setUserName(userName);
                dbReference.child(chatId).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            editText.setText("");
                        }else {

                        }
                    }
                });

            }
        }
    });
    mAddToChatroomBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivityChat.this, FriendListActivity.class);
            intent.putExtra("ChatroomId",chatroomId);
            startActivity(intent);
        }
    });
    btnLocation.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            noticeDialog.setNotification("Bạn muốn chia sẻ vị trí hiện tại ?", "Đồng ý", "Hủy", null);
            noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    String chatId = dbReference.child(chatroomId).push().getKey();
                    LatLng latLng = mapUtil.getLocation();
                    lat = latLng.latitude;
                    lng = latLng.longitude;

                    Message message = new Message();
                    message.setId(chatId);
                    message.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    message.setLatLng(true);
                    message.setContent(mapUtil.getAddress());
                    message.setLatitude(lat);
                    message.setLongitude(lng);
                    dbReference.child(chatId).setValue(message);

//                        ChatBubble ChatBubble = new ChatBubble(mapUtil.getAddress(), false);
//                        ChatBubbles.add(ChatBubble);
//                        adapter.notifyDataSetChanged();
                }
            });
            noticeDialog.show();
            }
        });
    }

    public void loadChatHistory() {

    }

    public void loadIntent(){
        Intent intent = getIntent();
        if(intent != null){
            chatroomId = intent.getStringExtra("chatroomId");
            dbReference = dbReference.child(chatroomId).child("content");
        } else {
            Toast.makeText(this,"Something wrong :v" , Toast.LENGTH_LONG).show();
            finish();
        }
    }
    private void receiveNewMessage() {
        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addNewMessageToListview(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("new message2",dataSnapshot.getValue(Message.class).getContent());
                addNewMessageToListview(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addNewMessageToListview(Message message) {
        if(message.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && !message.isLatLng()) {
            ChatBubbles.add(message);
            adapter.notifyDataSetChanged();
        }else if (!message.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            ChatBubbles.add(message);
            adapter.notifyDataSetChanged();
        }
    }
    public void drawLocation(View v) {
        int position = messageView.getChildAdapterPosition(v);
        Message message = ChatBubbles.get(position);
        Intent intent = new Intent(this,MapActivity.class);
        intent.putExtra("Flag","LOCATION");
        intent.putExtra("Latitude",message.getLatitude());
        intent.putExtra("Longitude",message.getLongitude());
        startActivity(intent);
    }
}
