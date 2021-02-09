package com.example.chatlistassignment.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.example.chatlistassignment.R;
import com.example.chatlistassignment.adapters.ViewPagerAdapter;
import com.example.chatlistassignment.utils.SyncNativeContacts;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;
import com.google.android.material.tabs.TabLayout;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    SyncNativeContacts syncNativeContacts;

    private final int READ_CONTACT_REQUEST_CODE = 100;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    private void init() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        checkPermissionSyncContacts();

    }

    private void checkPermissionSyncContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT_REQUEST_CODE);
        else
            syncContacts();
    }

    private void syncContacts() {
        syncNativeContacts = new SyncNativeContacts(this);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                syncNativeContacts.syncNativeContacts();
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        Log.d("TAG", "Inside onSubscribe of syncContacts");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Inside onComplete of syncContacts");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.d("TAG", "Inside onError of syncContacts. :" + e.getMessage());
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_CONTACT_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                syncContacts();
            else
                Toast.makeText(this, "Contact Sync failed. Please grant contacts permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);

        setMenu(menu);

        MenuItem searchViewItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchViewItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();

                FragmentViewModel.setQueryString(query);
                Log.d("TAG", "Inside onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                FragmentViewModel.setQueryString(newText);
                Log.d("TAG", "Inside onQueryTextChange: " + newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setMenu(Menu menu) {
        FragmentViewModel.getIsMultiSelectOn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    menu.findItem(R.id.multi_select_delete).setVisible(true);
                    menu.findItem(R.id.search).setVisible(false);
                } else {
                    menu.findItem(R.id.multi_select_delete).setVisible(false);
                    menu.findItem(R.id.search).setVisible(true);
                }
            }
        });
    }
}