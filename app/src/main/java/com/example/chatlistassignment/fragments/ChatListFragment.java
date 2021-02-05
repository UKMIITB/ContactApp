package com.example.chatlistassignment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlistassignment.ItemClickListener;
import com.example.chatlistassignment.R;
import com.example.chatlistassignment.activities.DetailedUserInfoActivity;
import com.example.chatlistassignment.activities.EditUserInfoActivity;
import com.example.chatlistassignment.adapters.RecyclerViewAdapter;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

import java.util.ArrayList;


public class ChatListFragment extends Fragment implements ItemClickListener {

    RecyclerView recyclerViewChatList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    ArrayList<User> queryArrayList;

    FragmentViewModel fragmentViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = ViewModelProviders.of(this).get(FragmentViewModel.class);
        queryArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        init(view);
        fragmentViewModel.init();
        observeForDbChanges();
        observeQueryString();

        return view;
    }

    private void observeQueryString() {
        if (fragmentViewModel != null) {
            fragmentViewModel.getQueryString().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String query) {
                    Log.d("TAG", "Inside ChatListFragment: " + query);
                    queryChatList(query);
                }
            });
        }

    }

    private void queryChatList(String query) {
        query = "%" + query + "%";

        fragmentViewModel.queryInit(query);

        fragmentViewModel.queriedUserList.observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                recyclerViewAdapter.submitList(users);
            }
        });
    }

    private void observeForDbChanges() {

        fragmentViewModel.userList.observe(this, new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                recyclerViewAdapter.submitList(users);
            }
        });

    }

    private void init(View view) {
        recyclerViewChatList = view.findViewById(R.id.recyclerview_chat_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewAdapter = new RecyclerViewAdapter();

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewChatList);
        recyclerViewChatList.setLayoutManager(layoutManager);
        recyclerViewChatList.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onItemClicked(View view, User user) {
        switch (view.getId()) {
            case R.id.button_delete:
                fragmentViewModel.deleteUser(user);
                break;
            case R.id.button_edit:
                Intent intentEditUserInfoActivity = new Intent(getContext(), EditUserInfoActivity.class);
                intentEditUserInfoActivity.putExtra("User", user);
                startActivity(intentEditUserInfoActivity);
                break;
            default:
                Intent intentDetailedUserInfoActivity = new Intent(getContext(), DetailedUserInfoActivity.class);
                intentDetailedUserInfoActivity.putExtra("User", user);
                startActivity(intentDetailedUserInfoActivity);
                break;
        }
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // fragmentViewModel.deleteUser(userArrayList.get(viewHolder.getAdapterPosition()));
        }
    };
}