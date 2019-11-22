package com.example.locateme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.locateme.Chatroom.ChatroomListActivity;
import com.example.locateme.model.User;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {
    Button btn_Menu;
    RelativeLayout myKonten;
    RelativeLayout overbox;
    RelativeLayout main_Profile;
    CircleImageView civ_Home, civ_Map,civ_Friends, civ_Family, civ_Suggest, civ_Exit;
    CircleImageView mAvatar;
    Animation formsmall, formnothing, turn_off_animation ;
    private TextView name;
    private TextView phone;
    private TextView address;
    DatabaseReference databaseReference;
    String idUser;
    private boolean isModalOn = false;
    private final int GALLERY_REQUEST = 1001;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            name = findViewById(R.id.profile_name);
            phone = findViewById(R.id.profile_phone);
            address = findViewById(R.id.profile_location);
            Intent intent = getIntent();
            if(intent!= null) {
                idUser = intent.getStringExtra("id");
                databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                        databaseReference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener()
                        {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            user = dataSnapshot.getValue(User.class);
                            name.setText(user.getName());
                            phone.setText(user.getPhone());
                            loadImage();
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        btn_Menu = (Button)findViewById(R.id.btn_Menu);

        myKonten = (RelativeLayout) findViewById(R.id.modal_menu);
        overbox = (RelativeLayout) findViewById(R.id.overbox);
        main_Profile = (RelativeLayout) findViewById(R.id.main_Profile);

        civ_Home = (CircleImageView) findViewById(R.id.civ_Home);
        civ_Map = (CircleImageView) findViewById(R.id.civ_Map);
        civ_Friends = (CircleImageView) findViewById(R.id.civ_Friends);
        civ_Family = (CircleImageView) findViewById(R.id.civ_Family);
        civ_Suggest = (CircleImageView) findViewById(R.id.civ_Suggest);
        civ_Exit = (CircleImageView) findViewById(R.id.civ_Exit);

        formsmall = AnimationUtils.loadAnimation(this,R.anim.formsmall);
        formnothing = AnimationUtils.loadAnimation(this,R.anim.formnothing);
        turn_off_animation = AnimationUtils.loadAnimation(this,R.anim.turn_off_animation);
        myKonten.setAlpha(0);
        overbox.setAlpha(0);

        mAvatar = findViewById(R.id.profile_image);

        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btn_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overbox.setAlpha(1);
                overbox.startAnimation(formnothing);
                myKonten.setAlpha(1);
                myKonten.startAnimation(formsmall);
                isModalOn = true;
            }
        });


        civ_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this,UpdateProfileActivity.class);
                intent.putExtra("id",idUser);
                startActivity(intent);
            }
        });

        main_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isModalOn) {
                    overbox.setAlpha(0);
                    myKonten.startAnimation(turn_off_animation);
                    ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();
                    ViewCompat.animate(myKonten).setStartDelay(1000).alpha(0).start();
                    isModalOn = false;
                }
            }
        });
        civ_Friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChatroomListActivity.class);
                intent.putExtra("user_id",idUser);
                startActivity(intent);
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private void chooseImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    private void uploadImage() {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + user.getPhone() + "/" + "profile.png");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void loadImage() {
        StorageReference uri = storageReference.child("images/"+user.getPhone()+"/profile.png");
        Glide.with(this /* context */)
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.user)
                .into(mAvatar);
    }
    public void backButton(View v) {
        finish();

    }
//    public void backToLogin(View v){
//        Intent backToLogin = new Intent(this, LoginActivity.class);
//        this.startActivity(backToLogin);
//    }

    public void moveToMap(View v){
        Intent moveToMap = new Intent(this, MapActivity.class);
        this.startActivity(moveToMap);
    }
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (reqCode) {
                case GALLERY_REQUEST :
                {
                    try {
                        filePath = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(filePath);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        mAvatar.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    uploadImage();
                }break;
            }
        }else {
        }
    }
}
