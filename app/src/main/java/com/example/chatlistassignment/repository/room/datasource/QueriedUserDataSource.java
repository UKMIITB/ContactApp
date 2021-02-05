package com.example.chatlistassignment.repository.room.datasource;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.repository.room.LocalRepository;

import io.reactivex.disposables.CompositeDisposable;

public class QueriedUserDataSource extends ItemKeyedDataSource<Integer, User> {

    private LocalRepository repository;
    private CompositeDisposable compositeDisposable;
    private String query;

    public QueriedUserDataSource(Context ctx, CompositeDisposable compositeDisposable, String query) {
        repository = new LocalRepository(ctx);
        this.compositeDisposable = compositeDisposable;
        this.query = query;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<User> callback) {
//        compositeDisposable.add(repository.queryAllUser(query).subscribe(users -> {
//            Log.d("TAG", "loadInitial QueriedUserDataSource:  userList Size  " + users.size());
//            callback.onResult(users);
//        }, throwable -> {
//            Log.e("TAG", "loadInitial QueriedUserDataSource:  error in data source");
//        }));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {
//        compositeDisposable.add(repository.getAllUser(params.key, params.requestedLoadSize).subscribe(users -> {
//            callback.onResult(users);
//        }, throwable -> {
//            Log.e("TAG", "loadInitial:  error in data source");
//        }));
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
