package com.example.chatlistassignment.utils;

import android.database.ContentObserver;
import android.os.Handler;

public class ContactsChangeListener extends ContentObserver {

    public ContactsChangeListener(Handler handler) {
        super(handler);
    }
}
