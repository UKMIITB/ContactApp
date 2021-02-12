package com.example.chatlistassignment.interfaces;

import android.view.View;

import com.example.chatlistassignment.model.Contact;
import com.example.chatlistassignment.model.User;

public interface ItemClickListener {
    void onItemClicked(View view, User user);

    void onItemLongClicked(View view, User user, int index);
}
