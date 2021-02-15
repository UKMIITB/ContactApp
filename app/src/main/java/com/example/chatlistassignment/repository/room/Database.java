package com.example.chatlistassignment.repository.room;

import androidx.room.RoomDatabase;

import com.example.chatlistassignment.model.Contact;
import com.example.chatlistassignment.model.User;

@androidx.room.Database(entities = {User.class, Contact.class}, version = 2, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract ContactDao contactDao();
}
