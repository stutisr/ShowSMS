package com.example.stuti.showsms;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<SmsFormat> List = new ArrayList<SmsFormat>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cur = getContentResolver().query(uri, null, null, null, null);
        startManagingCursor(cur);

        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                SmsFormat sms = new SmsFormat();
                if (cur.getColumnIndexOrThrow("body") > -1) {
                    sms.setBody(cur
                            .getString(cur.getColumnIndexOrThrow("body"))
                            .toString());
                }
                if (cur.getColumnIndexOrThrow("address") > -1) {
                    sms.setNumber(cur.getString(
                            cur.getColumnIndexOrThrow("address")).toString());
                }

                String name = null;
                name = Search(cur.getString(
                        cur.getColumnIndexOrThrow("address")).toString());

                if (name != " ") {
                    sms.setName(name);
                    List.add(sms);
                }
                cur.moveToNext();
            }
        }
        cur.close();

        setListAdapter(new ListAdapter(this, List));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        SmsFormat sms = (SmsFormat) getListAdapter().getItem(position);
        Log.i("MSG", sms.getBody());

    }

    public String Search(String number) {
        Uri uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String name = " ";

        ContentResolver contentResolver = getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[]{
                        BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME},
                null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup
                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
    }
}
