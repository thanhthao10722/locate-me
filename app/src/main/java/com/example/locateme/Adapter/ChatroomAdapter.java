package com.example.locateme.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.locateme.R;
import com.example.locateme.model.Chatroom;

import java.util.ArrayList;


public class ChatroomAdapter extends ArrayAdapter<Chatroom> implements Filterable {
    private Context context;
    private int resource;
    private ArrayList<Chatroom> list,temporaryList;

    public ChatroomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Chatroom> inputList) {
        super(context, resource, inputList);
        this.context = context;
        this.resource = resource;
        this.list = inputList;
        this.temporaryList = inputList;
        Log.d("temporaryList",String.valueOf(temporaryList.size()));
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

    @Override
    public Filter getFilter() {
        return  ChatroomFlter;
    }
    Filter ChatroomFlter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            Log.d("temporaryList",String.valueOf(temporaryList.size()));
            if (constraint == null || constraint.length() == 0) {
                results.values = temporaryList;
                results.count = temporaryList.size();
            }
            else {
                ArrayList<Chatroom> nChatroom = new ArrayList<>();
                for (Chatroom p : temporaryList) {
                    if (p.getName().toLowerCase()
                            .contains(constraint.toString().toLowerCase()))
                        nChatroom.add(p);
                }
                results.values = nChatroom;
                results.count = nChatroom.size();

            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            clear();
            notifyDataSetChanged();
            for (Chatroom i : (ArrayList<Chatroom>)results.values) {
                add(i);
            }
            notifyDataSetInvalidated();
        }
    };
}
