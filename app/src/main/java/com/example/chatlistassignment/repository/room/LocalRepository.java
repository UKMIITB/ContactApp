package com.example.chatlistassignment.repository.room;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.example.chatlistassignment.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalRepository {

    private UserDao userDao;

    public LocalRepository(Context ctx) {
        userDao = UserDatabase.getInstance(ctx).userDao();
    }


    public Completable addUser(User user) {
        return userDao.addUser(user);
    }

    public Completable deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    public Completable updateUser(User user) {
        return userDao.updateUser(user);
    }


    public DataSource.Factory<Integer,User> getAllUser() {
        return userDao.getAllUser();
    }

    public Single<List<User>> queryAllUser(String query) {
        return userDao.queryAllUser(query);
    }


}
