package com.example.chatlistassignment.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatlistassignment.R;
import com.example.chatlistassignment.fragments.DataEntryFragment;
import com.example.chatlistassignment.model.User;

public class EditUserInfoActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        user = (User) getIntent().getSerializableExtra("User");
        startDataEntryFragment();
    }

    private void startDataEntryFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        bundle.putBoolean("IsEditInfoActivity", true);

        DataEntryFragment dataEntryFragment = new DataEntryFragment();
        dataEntryFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame_container, dataEntryFragment)
                .commit();
    }
}