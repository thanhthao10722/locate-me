package com.example.locateme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.locateme.R;
import com.example.locateme.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private ArrayList<Contact> contactList;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> contactList) {
        super(context, resource, contactList);
        this.context = context;
        this.resource = resource;
        this.contactList = contactList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.contact_adapter,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextViewName = convertView.findViewById(R.id.contact_name);
            viewHolder.mTextViewPhone = convertView.findViewById(R.id.contact_phone);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = contactList.get(position);
        viewHolder.mTextViewName.setText(contact.getName());
        viewHolder.mTextViewPhone.setText(contact.getNumber());
        return convertView;
    }
    public class ViewHolder {
        TextView mTextViewName,mTextViewPhone;

    }
}
