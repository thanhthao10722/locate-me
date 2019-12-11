package com.example.locateme.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.locateme.LoginActivity;
import com.example.locateme.R;
import com.example.locateme.model.Contact;
import com.example.locateme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Currency;
import java.util.List;


public class AddFriendAdapter extends BaseAdapter
{
    public Context context;
    public int layout;
    public List<User> list;

    public AddFriendAdapter(Context context, int layout, List<User> list)
    {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.add_friend_adapter, viewGroup, false);
        // ánh xạ view
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser current_user = mAuth.getCurrentUser();
        final int index = i;
        TextView txtName = view.findViewById(R.id.txt_name);
        TextView txtPhone = view.findViewById(R.id.txt_phone);
        ImageView image = view.findViewById(R.id.img_view);
        Button btn_accept = view.findViewById(R.id.btn_accept);
        Button btn_decline = view.findViewById(R.id.btn_decline);
        User addFriend = list.get(i);
        txtName.setText(addFriend.getName());
        txtPhone.setText(addFriend.getPhone());
        Glide.with(context /* context */)
                .asDrawable()
                .load(addFriend.getPhotourl())
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.user)
                .into(image);
        return view;

    }
}

