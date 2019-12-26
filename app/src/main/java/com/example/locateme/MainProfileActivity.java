package com.example.locateme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.locateme.Chatroom.ChatroomListActivity;
import com.example.locateme.Modal.BottomSheetModal;
import com.example.locateme.Util.MapUtil;
import com.example.locateme.model.User;

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

public class MainProfileActivity extends AppCompatActivity implements BottomSheetModal.ActionListener {
    Button btn_Menu;
    ProgressBar progressBar;
    CircleImageView mAvatar;
    private TextView name;
    private TextView phone;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final int GALLERY_REQUEST = 1001;
    private StorageReference storageReference;
    private Uri filePath;
    private User user;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private MapUtil map;
    SimpleDateFormat formatter;
    private String image;
    private FirebaseUser current_user;
    BottomSheetModal modal = new BottomSheetModal();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        setProperties();
        setActionListenerMPP();
        getDatabaseReference();
        loadUser();
    }

    public void setProperties() {
        name = findViewById(R.id.profile_name);
        phone = findViewById(R.id.profile_phone);
        btn_Menu = (Button)findViewById(R.id.btn_Menu);
        mAvatar = findViewById(R.id.profile_image);
        formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        current_user = mAuth.getCurrentUser();
        Log.d("Event","Set PROPERTIES");
    }

    public void setActionListenerMPP() {
        btn_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modal.setActionListener(MainProfileActivity.this);
                modal.show(getSupportFragmentManager(),"modalMenu");
            }
        });

        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        Log.d("Event","setActionListener");
    }

    public void getDatabaseReference() {
        storageReference = FirebaseStorage.getInstance().getReference();
        Log.d("Event","setDB");
    }

    public void loadUser() {
        databaseReference.child("users").child(current_user.getUid()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                phone.setText(user.getPhone());
                UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(user.getName()).build();
                mAuth.getCurrentUser().updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                loadImage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
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
                                    databaseReference.child("users").child(current_user.getUid()).child("photourl").setValue(image);
                                    databaseReference.child("users").child(current_user.getUid()).child("_updated").setValue(formatter.format(new Date()));

                                }
                            });
                            Toast.makeText(MainProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (current_user.getPhotoUrl() != null)
        {
            String uri = current_user.getPhotoUrl().toString();
            Glide.with(this /* context */)
                    .asDrawable()
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.user)
                    .into(mAvatar);
        }
    }
    public void backButton(View v) { finish();
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
                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    uploadImage();
                }break;
            }
        }else {
        }
    }
    @Override
    public void onButtonClick(int id) {
        switch (id) {
            case R.id.btn_chat_profile: {
                modal.dismiss();
                Intent intent = new Intent(MainProfileActivity.this, ChatroomListActivity.class);
                startActivity(intent);
            }break;
            case R.id.btn_add_friend: {
                modal.dismiss();
                Intent intent = new Intent(MainProfileActivity.this, PhoneDirectoriesActivity.class);
                startActivity(intent);
            }break;
            case R.id.btn_update_profile: {
                modal.dismiss();
                Intent intent = new Intent(MainProfileActivity.this, UpdateProfileActivity.class);
                startActivity(intent);
            }break;
            case R.id.btn_map: {
                modal.dismiss();
                Intent intent = new Intent(MainProfileActivity.this, MapActivity.class);
                startActivity(intent);
            }break;
            case R.id.btn_signout: {
                mAuth.signOut();
                modal.dismiss();
                Intent intent = new Intent(MainProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }break;
        }
    }
}