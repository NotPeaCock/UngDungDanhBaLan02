package com.trungse.ungdungdanhbalan02.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trungse.ungdungdanhbalan02.R;
import com.trungse.ungdungdanhbalan02.model.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;
    private List<Contact> arrayContact;

    public ContactAdapter( Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact,parent,false);
            viewHolder.imgAvatar = convertView.findViewById(R.id.img_avatar);
            viewHolder.tvNumber=(TextView) convertView.findViewById(R.id.tv_number);
            viewHolder.tvName= convertView.findViewById(R.id.tv_name);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contact = arrayContact.get(position);
        viewHolder.tvName.setText(contact.getmName());
        viewHolder.tvNumber.setText(contact.getmNumber());

        if(contact.isMale()){
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.Male);
        }else{
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.Female);
        }

        return convertView;
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvName;
        TextView tvNumber;
    }

}
