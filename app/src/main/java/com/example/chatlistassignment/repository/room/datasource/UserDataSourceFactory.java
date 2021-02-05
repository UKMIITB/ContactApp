package com.example.chatlistassignment.repository.room.datasource;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.chatlistassignment.model.User;

import io.reactivex.disposables.CompositeDisposable;

public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {
    private Context ctx;
    private UserDataSource userDataSource;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<UserDataSource> userDataSourceMutableLiveData = new MutableLiveData<>();

    public UserDataSourceFactory(Context ctx, CompositeDisposable compositeDisposable) {
        this.ctx = ctx;
        this.compositeDisposable = compositeDisposable;
    }

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        if (userDataSource == null) {
            userDataSource = new UserDataSource(ctx, compositeDisposable);
            userDataSourceMutableLiveData.postValue(userDataSource);
        }
        return userDataSource;
    }
}
