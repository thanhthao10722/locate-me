package com.example.locateme.Modal;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.locateme.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetModal extends BottomSheetDialogFragment {
    private ActionListener mActionListioner;
    LinearLayout mChatButton,mAddFriendButton,mUpdateProfileButton,mMapButton,mSignoutButton;

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
        mChatButton = view.findViewById(R.id.chat_profile_lnLayout);
        mAddFriendButton = view.findViewById(R.id.add_friend_lnLayout);
        mMapButton = view.findViewById(R.id.map_lnLayout);
        mUpdateProfileButton = view.findViewById(R.id.update_profile_lnLayout);
        mSignoutButton = view.findViewById(R.id.signout_lnLayout);
    }

    private void setOnClickListener(View view) {
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.chat_profile_lnLayout);
                }
            }
        });
        mAddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.add_friend_lnLayout);
                }
            }
        });
        mUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.update_profile_lnLayout);
                }
            }
        });
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.map_lnLayout);
                }
            }
        });
        mSignoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionListioner != null) {
                    mActionListioner.onButtonClick(R.id.signout_lnLayout);
                }
            }
        });
    }
}
