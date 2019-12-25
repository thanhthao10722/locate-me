package com.example.locateme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.locateme.R;
import com.example.locateme.model.Chatroom;

import java.util.ArrayList;


public class ChatroomAdapter extends ArrayAdapter<Chatroom> {
    private Context context;
    private int resource;
    private ArrayList<Chatroom> list, temporaryList;

    public ChatroomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Chatroom> inputList) {
        super(context, resource, inputList);
        this.context = context;
        this.resource = resource;
        this.list = inputList;
        this.temporaryList = inputList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chatroom_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.chatroom_name);
            Chatroom chatroom = list.get(position);
            viewHolder.tvName.setText(chatroom.getName());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
    }

    private class ChatroomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = list;
                results.count = list.size();
            }
            else {
                // We perform filtering operation
                ArrayList<Chatroom> nChatroom = new ArrayList<Chatroom>();

                for (Chatroom p : list) {
                    if (p.getName().toUpperCase()
                            .startsWith(constraint.toString().toUpperCase()))
                        nChatroom.add(p);
                }

                results.values = nChatroom;
                results.count = nChatroom.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                list = (ArrayList<Chatroom>)results.values;
                notifyDataSetChanged();
            }
        }
    }
}
