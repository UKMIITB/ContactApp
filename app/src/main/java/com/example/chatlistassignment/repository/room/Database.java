package com.example.chatlistassignment.repository.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chatlistassignment.model.Contact;
import com.example.chatlistassignment.model.User;

@androidx.room.Database(entities = {User.class, Contact.class}, version = 2)
public abstract class Database extends RoomDatabase {

    private static Database database;

    public static Database getInstance(Context context) {
        if (database == null)
//            database = Room.databaseBuilder(context, Database.class, "User_Database").build();
            database = Room.databaseBuilder(context, Database.class, "User_Database")
                    .addMigrations(MIGRATION_1_2).build();
        return database;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table 'userdb' add 'date' integer ");
        }
    };

    public abstract UserDao userDao();

    public abstract ContactDao contactDao();
}
