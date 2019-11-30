package com.example.locateme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.locateme.R;
import com.example.locateme.model.Chatroom;

import java.util.ArrayList;


public class ChatroomAdapter extends ArrayAdapter<Chatroom> {
    private Context context;
    private int resource;
    private ArrayList<Chatroom> list,suggestionList;
    public ChatroomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Chatroom> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.suggestionList = new ArrayList<Chatroom>(list);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chatroom_adapter,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.chatroom_name);
            viewHolder.tvMemNumber = (TextView) convertView.findViewById(R.id.chatroom_member);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Chatroom chatroom = list.get(position);
        viewHolder.tvName.setText(chatroom.getName());
        viewHolder.tvMemNumber.setText(chatroom.getMemNumber()+" members");
        return convertView;
    }
    public class ViewHolder {
        TextView tvName,tvMemNumber;
    }
}
