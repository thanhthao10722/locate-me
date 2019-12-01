package com.example.locateme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
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
import com.example.locateme.Util.MapUtil;
import com.example.locateme.model.User;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private boolean isModalOn = false;
    private final int GALLERY_REQUEST = 1001;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private User user;
    private FirebaseAuth mAuth;
    private MapUtil map;
    private boolean isNameChanging = false;
    private RelativeLayout name_layout;
    SimpleDateFormat formatter;
    private String image;
    private FirebaseUser current_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.profile_name);
        phone = findViewById(R.id.profile_phone);
        address = findViewById(R.id.profile_location);
        mAuth = FirebaseAuth.getInstance();
        map = new MapUtil(ProfileActivity.this);
        name_layout=findViewById(R.id.profile_name_layout);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        current_user = mAuth.getCurrentUser();
        databaseReference.child(current_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                phone.setText(user.getPhone());
                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(user.getName()).build();
                current_user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                loadImage();
            String location = map.getAddress();
            address.setText(location);
        }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
    }
});
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

        civ_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isModalOn) {
                    finish();
                }
            }
        });

        civ_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isModalOn) {
                    moveToMap(v);
                }
            }
        });

        civ_Family.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isModalOn) {
                    Intent intent = new Intent(ProfileActivity.this,ChatroomListActivity.class);
                    startActivity(intent);
                }
            }
        });
        civ_Home.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isModalOn) {
                    Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                    startActivity(intent);
                }
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
                if(isNameChanging) {
                    name.setEnabled(false);
                    isNameChanging = false;
                }
            }
        });
        civ_Suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isModalOn) {
                    Intent intent = new Intent(ProfileActivity.this, PhoneDirectoriesActivity.class);
                    startActivity(intent);
                }

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

    private void uploadImage()
    {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + user.getPhone() + "/" + "profile.png");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            progressDialog.dismiss();
                            StorageMetadata data = taskSnapshot.getMetadata();
                            Task<Uri> url = ref.getDownloadUrl();
                            url.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    image = uri.toString();
                                    final FirebaseUser current_user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(image)).build();
                                    current_user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                        }
                                    });
                                    databaseReference.child(current_user.getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            if(dataSnapshot.exists())
                                            {
                                                User user = dataSnapshot.getValue(User.class);
                                                user.setPhotourl(image);
                                                user.set_updated(formatter.format(new Date()));
                                                databaseReference.child(current_user.getUid()).setValue(user);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            });
                            databaseReference.child(current_user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if(dataSnapshot.exists())
                                    {
                                        User user = new User();
                                        user = dataSnapshot.getValue(User.class);
                                        user.setPhotourl(image);
                                        user.set_updated(formatter.format(new Date()));
                                        databaseReference.child(current_user.getUid()).setValue(user);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
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

    private void loadImage()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user.getPhotoUrl() != null)
        {
            String uri = user.getPhotoUrl().toString();
            Glide.with(this /* context */)
                    .asDrawable()
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.user)
                    .into(mAvatar);
        }
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
    private void updateName() {

    }
}