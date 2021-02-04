package com.example.chatlistassignment.repository.room;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.example.chatlistassignment.model.User;

import java.util.List;
import java.util.Observable;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalRepository {

    private  UserDao userDao;

    public LocalRepository(Context ctx){
        userDao = UserDatabase.getInstance(ctx).userDao();
    }


    public Completable addUser(User user){
        return userDao.addUser(user);
    }

    public Completable deleteUser(User user){
        return userDao.deleteUser(user);
    }

    public Completable updateUser(User user){
        return userDao.updateUser(user);
    }


    public Single<List<User>> getAllUser(int id, int packetSize){
        return userDao.getAllUser(id, packetSize);
    }

    public LiveData<List<User>> queryAllUser(String query){
        return userDao.queryAllUser(query);
    }


}
