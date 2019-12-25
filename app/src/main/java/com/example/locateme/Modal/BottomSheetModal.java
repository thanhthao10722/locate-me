package com.example.locateme.Modal;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.locateme.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetModal extends BottomSheetDialogFragment {
    private ActionListener mActionListioner;
    Button mChatButton,mAddFriendButton,mUpdateProfileButton,mMapButton,mSignoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_modal,container,false);
        loadProperties(view);
        setOnClickListener(view);


        return view;
    }

    public interface ActionListener {
        void onButtonClick(int id);
    }

    public void setActionListener(ActionListener actionListener) {
        this.mActionListioner = actionListener;
    }

    private void loadProperties(View view) {
        mChatButton = view.findViewById(R.id.btn_chat_profile);
        mAddFriendButton = view.findViewById(R.id.btn_add_friend);
        mMapButton = view.findViewById(R.id.btn_map);
        mUpdateProfileButton = view.findViewById(R.id.btn_update_profile);
        mSignoutButton = view.findViewById(R.id.btn_signout);
    }

    private void setOnClickListener(View view) {
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.btn_chat_profile);
                }
            }
        });
        mAddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.btn_add_friend);
                }
            }
        });
        mUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.btn_update_profile);
                }
            }
        });
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.btn_map);
                }
            }
        });
        mSignoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.btn_signout);
                }
            }
        });
    }
}
