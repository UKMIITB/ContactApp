package com.example.chatlistassignment.repository.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chatlistassignment.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Insert
    Completable addUser(User user);

    @Delete
    Completable deleteUser(User user);

    @Query("select * from userdb WHERE _id >= :id LIMIT :size")
    Single<List<User>> getAllUser(int id, int size);

    @Update
    Completable updateUser(User user);

    @Query("select * from userdb where name like :query or contactNumber like :query")
    LiveData<List<User>> queryAllUser(String query);
}
