package com.example.locateme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.locateme.R;
import com.example.locateme.model.Contact;

import java.util.List;


public class ContactAdapter extends BaseAdapter
{
    public Context context;
    public int layout;
    public List<Contact> list;

    public ContactAdapter(Context context, int layout, List<Contact> list)
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

    public List<Contact> getList() {
        return list;
    }

    public void setList(List<Contact> list) {
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
        view = LayoutInflater.from(context).inflate(R.layout.contact_adapter, viewGroup, false);
        // ánh xạ view
        TextView txtName = view.findViewById(R.id.txt_name);
        TextView txtPhone = view.findViewById(R.id.txt_phone);
        ImageView image = view.findViewById(R.id.img_view);
        Contact contact = list.get(i);
        txtName.setText(contact.getName());
        txtPhone.setText(contact.getPhone());
        Glide.with(context /* context */)
                .asDrawable()
                .load(contact.getImageSource())
                .apply(RequestOptions.circleCropTransform())
                .error(R.drawable.user)
                .into(image);
        return view;
    }
}

