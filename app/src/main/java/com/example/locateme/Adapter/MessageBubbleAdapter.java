package com.example.locateme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locateme.MapActivity;
import com.example.locateme.R;
import com.example.locateme.Util.MapUtil;
import com.example.locateme.model.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageBubbleAdapter extends RecyclerView.Adapter<MessageBubbleAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Message> messagesList;
    private final int SELF_MESSAGE = 121;
    private final int OTHER_MESSAGE = 212;
    private final int LOCATION_MESSAGE = 121212;
    private final int SELF_LOCATION = 12321;


    public MessageBubbleAdapter(Context context, ArrayList<Message> list) {
        this.context = context;
        this.messagesList = list;
    }

    @NonNull
    @Override
    public MessageBubbleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SELF_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.right_message,parent,false);
            return new MessageBubbleAdapter.ViewHolder(view);
        } else if(viewType == OTHER_MESSAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.left_message,parent,false);
            return new MessageBubbleAdapter.ViewHolder(view);
        } else if (viewType == LOCATION_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.location_message,parent,false);
            return new MessageBubbleAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.mylocation_message,parent,false);
            return new MessageBubbleAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Message message = messagesList.get(position);
        if(getItemViewType(position) == SELF_MESSAGE) {
            holder.tv_Content = holder.view.findViewById(R.id.right_message_content);
            holder.tv_Content.setText(message.getContent());
        } else if(getItemViewType(position) == OTHER_MESSAGE) {
            holder.tv_Content = holder.view.findViewById(R.id.left_message_content);
            holder.tv_Content.setText(message.getContent());
            holder.tv_Name = holder.view.findViewById(R.id.left_message_user);
            holder.tv_Name.setText(message.getUserName());
        } else if(getItemViewType(position) == LOCATION_MESSAGE){
            holder.tv_Content = holder.view.findViewById(R.id.location_message_content);
            holder.tv_Content.setText(message.getContent());
            holder.tv_Name = holder.view.findViewById(R.id.left_message_user);
            holder.tv_Name.setText(message.getUserName());
            holder.tv_Content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra("Flag", "LOCATION");
                    intent.putExtra("Latitude", message.getLatitude());
                    intent.putExtra("Longitude", message.getLongitude());
                    context.startActivity(intent);
                }
            });

        } else {
            holder.tv_Content = holder.view.findViewById(R.id.mylocation_message_content);
            holder.tv_Content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra("Flag", "MYLOCATION");
                    intent.putExtra("Latitude", message.getLatitude());
                    intent.putExtra("Longitude", message.getLongitude());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_Content;
        public TextView tv_Name;
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesList.get(position).getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) && !messagesList.get(position).isLatLng()) {
            return SELF_MESSAGE;
        } else if(messagesList.get(position).isLatLng() && !messagesList.get(position).getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return LOCATION_MESSAGE;
        } else if(messagesList.get(position).isLatLng() && messagesList.get(position).getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return SELF_LOCATION;
        } else {
            return OTHER_MESSAGE;
        }
    }
}
