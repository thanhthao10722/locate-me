package com.example.locateme;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.locateme.model.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    Button btn_Menu;
    RelativeLayout myKonten;
    RelativeLayout overbox;
    RelativeLayout main_Profile;
    CircleImageView civ_Home, civ_Map,civ_Friends, civ_Family, civ_Suggest, civ_Exit;
    Animation formsmall, formnothing, turn_off_animation ;
    private TextView name;
    private TextView phone;
    private TextView address;
    DatabaseReference databaseReference;
    String idUser;
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
                            User user = dataSnapshot.getValue(User.class);
                            name.setText(user.getName());
                            phone.setText(user.getPhone());
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


        btn_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overbox.setAlpha(1);
                overbox.startAnimation(formnothing);
                myKonten.setAlpha(1);
                myKonten.startAnimation(formsmall);
            }
        });

        civ_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overbox.setAlpha(0);
                myKonten.startAnimation(turn_off_animation);
                ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(myKonten).setStartDelay(1000).alpha(0).start();

            }
        });

        main_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overbox.setAlpha(0);
                myKonten.startAnimation(turn_off_animation);
                ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(myKonten).setStartDelay(1000).alpha(0).start();
            }
        });

    }
    public void backButton(View v) {
        finish();

    }
    public void backToLogin(View v){
        Intent backToLogin = new Intent(this, LoginActivity.class);
        this.startActivity(backToLogin);
    }

    public void moveToMap(View v){
        Intent moveToMap = new Intent(this, MapActivity.class);
        this.startActivity(moveToMap);
    }

}
