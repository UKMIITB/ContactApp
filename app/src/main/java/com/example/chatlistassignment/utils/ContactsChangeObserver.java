package com.example.chatlistassignment.utils;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class ContactsChangeObserver extends ContentObserver {



    public ContactsChangeObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.e("TAG", "onChange:Contacts Changed , now call sync  ----------->> " );
    }
}
