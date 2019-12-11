package com.example.locateme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.locateme.R;
import com.example.locateme.model.User;

import java.util.ArrayList;

public class AddToChatroomAdapter extends ArrayAdapter<User> {
    public Context context;
    public int layout;
    public ArrayList<User> list;
    public AddToChatroomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = layout;
        this.list = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.add_to_chatroom_adapter,parent,false);
            viewHolder = new AddToChatroomAdapter.ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.txt_phone);
            ImageView image = convertView.findViewById(R.id.img_view);
            User user = list.get(position);
            viewHolder.tvName.setText(user.getName());
            viewHolder.tvPhone.setText(user.getPhone());
            Glide.with(context /* context */)
                    .asDrawable()
                    .load(user.getPhotourl())
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.user)
                    .into(image);
        } else {
            viewHolder = (AddToChatroomAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    public class ViewHolder {
        TextView tvName,tvPhone;
    }
}
