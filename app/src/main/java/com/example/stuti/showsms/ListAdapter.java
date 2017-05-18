package com.example.stuti.showsms;

/**
 * Created by stutisrivastava on 18/05/17.
 */


import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<SmsFormat> {

    private final Context context;
    private final List<SmsFormat> smslist;

    public ListAdapter(Context context, List<SmsFormat> smsList) {
        super(context, R.layout.activity_main, smsList);
        this.context = context;
        this.smslist = smsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_main, parent, false);

        TextView senderNumber = (TextView) rowView.findViewById(R.id.textView1);

        senderNumber.setText(smslist.get(position).getName() + "\n"
                + smslist.get(position).getNumber() + "\n" + "\n"
                + smslist.get(position).getBody());

        return rowView;
    }

}