package com.example.chatlistassignment.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatlistassignment.fragments.ChatListFragment;
import com.example.chatlistassignment.fragments.ContactListFragment;
import com.example.chatlistassignment.fragments.DataEntryFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 1:
                return new DataEntryFragment();
            case 2:
                return new ContactListFragment();
            default:
                return new ChatListFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 1:
                return "DATA ENTRY";
            case 2:
                return "CONTACT LIST";
            default:
                return "CHAT LIST";
        }
    }
}
