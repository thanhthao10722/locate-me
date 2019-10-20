package com.example.locateme;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    Button btn_Menu;
    RelativeLayout myKonten;
    RelativeLayout overbox;
    CircleImageView civ_Home, civ_Map,civ_Friends, civ_Family, civ_Suggest, civ_Exit;
    Animation formsmall, formnothing ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Menu = (Button)findViewById(R.id.btn_Menu);

        myKonten = (RelativeLayout) findViewById(R.id.modal_menu);
        overbox = (RelativeLayout) findViewById(R.id.overbox);
        civ_Home = (CircleImageView) findViewById(R.id.civ_Home);
        civ_Map = (CircleImageView) findViewById(R.id.civ_Map);
        civ_Friends = (CircleImageView) findViewById(R.id.civ_Friends);
        civ_Family = (CircleImageView) findViewById(R.id.civ_Family);
        civ_Suggest = (CircleImageView) findViewById(R.id.civ_Suggest);
        civ_Exit = (CircleImageView) findViewById(R.id.civ_Exit);

        formsmall = AnimationUtils.loadAnimation(this,R.anim.formsmall);
        formnothing = AnimationUtils.loadAnimation(this,R.anim.formnothing);
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
    }

    public void backButton(View v) {
        finish();
    }
}
