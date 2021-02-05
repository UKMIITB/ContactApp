package com.example.chatlistassignment.repository.room.datasource;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.chatlistassignment.model.User;

import io.reactivex.disposables.CompositeDisposable;

public class QueriedUserDataSourceFactory extends DataSource.Factory<Integer, User> {

    private Context ctx;
    private QueriedUserDataSource queriedUserDataSource;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<QueriedUserDataSource> queriesUserDataSourceMutableLiveData = new MutableLiveData<>();
    private String query;

    public QueriedUserDataSourceFactory(Context ctx, CompositeDisposable compositeDisposable, String query) {
        this.ctx = ctx;
        this.compositeDisposable = compositeDisposable;
        this.query = query;
    }

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        if (queriedUserDataSource == null) {
            queriedUserDataSource = new QueriedUserDataSource(ctx, compositeDisposable, query);
            queriesUserDataSourceMutableLiveData.postValue(queriedUserDataSource);
        }
        return queriedUserDataSource;
    }
}
