package com.example.chatlistassignment.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chatlistassignment.model.User;

@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase userDatabase;

    public static UserDatabase getInstance(Context context) {
        if (userDatabase == null)
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, "User_Database").build();
        return userDatabase;
    }

    public abstract UserDao userDao();
}
