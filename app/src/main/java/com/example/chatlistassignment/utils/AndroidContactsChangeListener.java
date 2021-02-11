package com.example.chatlistassignment.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;

public class AndroidContactsChangeListener {

    private final ContentResolver mContentResolver;
    private static AndroidContactsChangeListener mInstance;
    private static IChangeListener mListener;
    private ContactsChangeObserver changeObserver;

    private final static long CHANGE_UPDATE_TIME = 2000;
    private long mLastTimeChangeProcessed = 0L;

    public interface IChangeListener {
        void onContactsChanged();
    }

    private AndroidContactsChangeListener(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public static AndroidContactsChangeListener getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AndroidContactsChangeListener(context);
        }
        return mInstance;
    }


    public void startContactsObservation(IChangeListener iChangeListener) {
        changeObserver = new ContactsChangeObserver(new Handler(Looper.getMainLooper()));
        mContentResolver.registerContentObserver(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, true, changeObserver);
        mListener = iChangeListener;

    }

    public void stopContactsObservation() {
        mContentResolver.unregisterContentObserver(changeObserver);
        mListener = null;
    }

    private void notifyContactChanged() {
        if (mListener != null) {
            mListener.onContactsChanged();
        }
    }


    private class ContactsChangeObserver extends ContentObserver {


        public ContactsChangeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            long mLastTimeChangeOccurred = System.currentTimeMillis();
            if (mLastTimeChangeOccurred - mLastTimeChangeProcessed > CHANGE_UPDATE_TIME) {
                notifyContactChanged();
                Log.e("TAG", "onChange:Contacts Changed , now call sync  ----------->> ");
                mLastTimeChangeProcessed = System.currentTimeMillis();
            }
        }
    }
}
