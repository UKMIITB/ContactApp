package com.example.chatlistassignment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import java.util.List;


public class ChatListFragment extends Fragment implements ItemClickListener {

    RecyclerView recyclerViewChatList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<User> userArrayList;

    ArrayList<User> queryArrayList;

    FragmentViewModel fragmentViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
        userArrayList = new ArrayList<>();
        queryArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        init(view);

        observeForDbChanges();
        observeQueryString();

        return view;
    }

    private void observeQueryString() {
        fragmentViewModel.getQueryString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String query) {
                Log.d("TAG", "Inside ChatListFragment: " + query);
                queryChatList(query);
            }
        });
    }

    private void queryChatList(String query) {
        query = "%" + query + "%";

        fragmentViewModel.queryAllUser(getContext(), query).observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                queryArrayList.clear();
                queryArrayList = (ArrayList<User>) users;
                recyclerViewAdapter.updateData(queryArrayList);
            }
        });
    }

    private void observeForDbChanges() {
        fragmentViewModel.getAllUser(getContext()).observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userArrayList.clear();
                userArrayList = (ArrayList<User>) users;
                recyclerViewAdapter.updateData(userArrayList);
            }
        });
    }

    private void init(View view) {
        recyclerViewChatList = view.findViewById(R.id.recyclerview_chat_list);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), userArrayList, this);

        recyclerViewChatList.setLayoutManager(layoutManager);
        recyclerViewChatList.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onItemClicked(View view, int position) {
        switch (view.getId()) {
            case R.id.button_delete:
                fragmentViewModel.deleteUser(userArrayList.get(position), getContext());
                break;
            case R.id.button_edit:
                Intent intentEditUserInfoActivity = new Intent(getContext(), EditUserInfoActivity.class);
                intentEditUserInfoActivity.putExtra("User", userArrayList.get(position));
                startActivity(intentEditUserInfoActivity);
                break;
            default:
                Intent intentDetailedUserInfoActivity = new Intent(getContext(), DetailedUserInfoActivity.class);
                intentDetailedUserInfoActivity.putExtra("User", userArrayList.get(position));
                startActivity(intentDetailedUserInfoActivity);
                break;
        }
    }
}