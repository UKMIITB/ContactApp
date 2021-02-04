package com.example.chatlistassignment.repository.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chatlistassignment.model.User;

@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase userDatabase;

    public static UserDatabase getInstance(Context context) {
        if (userDatabase == null)
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, "User_Database").build();
//        userDatabase = Room.databaseBuilder(context, UserDatabase.class, "User_Database")
//                .addMigrations(MIGRATION_1_2).build();
        return userDatabase;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //database.execSQL();
        }
    };

    public abstract UserDao userDao();
}
