package com.example.chatlistassignment.di.module;

import android.content.Context;

import androidx.room.Room;

import com.example.chatlistassignment.repository.LocalRepository;
import com.example.chatlistassignment.repository.room.ContactDao;
import com.example.chatlistassignment.repository.room.Database;
import com.example.chatlistassignment.repository.room.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static Database provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, Database.class, "user_database")
                .build();
    }

    @Provides
    @Singleton
    public static UserDao provideUserDao(Database db) {
        return db.userDao();
    }

    @Provides
    @Singleton
    public static ContactDao provideContactDao(Database db) {
        return db.contactDao();
    }

    @Provides
    @Singleton
    public static LocalRepository provideLocalRepository(UserDao userDao, ContactDao contactDao) {
        return new LocalRepository(userDao, contactDao);
    }
}
