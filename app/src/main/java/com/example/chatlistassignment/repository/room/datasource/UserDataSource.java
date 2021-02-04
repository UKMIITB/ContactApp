package com.example.chatlistassignment.repository.room.datasource;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.repository.room.LocalRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class UserDataSource extends ItemKeyedDataSource<Integer, User> {

    private LocalRepository repository;
    private CompositeDisposable compositeDisposable;

    public UserDataSource(Context ctx, CompositeDisposable compositeDisposable){
        repository = new LocalRepository(ctx);
        this.compositeDisposable  = compositeDisposable;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<User> callback) {
        compositeDisposable.add(repository.getAllUser(1, params.requestedLoadSize).subscribe( users -> {
            Log.d("TAG", "loadInitial:  userList Size  "+users.size());
            callback.onResult(users);
        }, throwable -> {
            Log.e("TAG", "loadInitial:  error in data source" );
        }));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
        compositeDisposable.add(repository.getAllUser(params.key,params.requestedLoadSize).subscribe( users -> {
            callback.onResult(users);
        }, throwable -> {
            Log.e("TAG", "loadInitial:  error in data source" );
        }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        return item.get_id();
    }
}
