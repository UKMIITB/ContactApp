package com.example.chatlistassignment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlistassignment.ItemClickListener;
import com.example.chatlistassignment.R;
import com.example.chatlistassignment.activities.EditUserInfoActivity;
import com.example.chatlistassignment.adapters.ChatListRecyclerViewAdapter;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

import java.util.ArrayList;
import java.util.List;


public class ChatListFragment extends Fragment implements ItemClickListener {

    RecyclerView recyclerViewChatList;
    RecyclerView.LayoutManager layoutManager;
    ChatListRecyclerViewAdapter chatListRecyclerViewAdapter;

    FragmentViewModel fragmentViewModel;

    boolean multiSelectStatus = false;

    ArrayList<User> deleteUserList;
    List<User> currentUserList;

    boolean isFragmentActive = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
        deleteUserList = new ArrayList<>();
        currentUserList = new ArrayList<>();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        init(view);
        fragmentViewModel.init();
        observeForDbChanges();
        observeQueryString();
        observeMultiSelectStatus();

        return view;
    }

    private void observeMultiSelectStatus() {
        FragmentViewModel.getIsMultiSelectOn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                multiSelectStatus = aBoolean;
            }
        });
    }

    private void observeQueryString() {
        if (fragmentViewModel != null) {
            fragmentViewModel.getQueryString().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String query) {
                    if (isFragmentActive)
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
                chatListRecyclerViewAdapter.submitList(users);
            }
        });
    }

    private void observeForDbChanges() {

        fragmentViewModel.userList.observe(getViewLifecycleOwner(), new Observer<PagedList<User>>() {
            @Override
            public void onChanged(PagedList<User> users) {
                currentUserList = users.snapshot();
                chatListRecyclerViewAdapter.submitList(users);
            }
        });

    }

    private void init(View view) {
        recyclerViewChatList = view.findViewById(R.id.recyclerview_chat_list);
        layoutManager = new LinearLayoutManager(getContext());
        chatListRecyclerViewAdapter = new ChatListRecyclerViewAdapter(this);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewChatList);
        recyclerViewChatList.setLayoutManager(layoutManager);
        recyclerViewChatList.setAdapter(chatListRecyclerViewAdapter);

    }

    @Override
    public void onItemClicked(View view, User user) {
        if (multiSelectStatus) {
            if (!deleteUserList.contains(user)) {
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.grey));
                deleteUserList.add(user);
            } else {
                deleteUserList.remove(user);
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.light_grey));

                if (deleteUserList.size() == 0)
                    fragmentViewModel.setIsMultiSelect(false);
            }

        } else {
            Intent intentEditUserInfoActivity = new Intent(getContext(), EditUserInfoActivity.class);
            intentEditUserInfoActivity.putExtra("User", user);
            startActivity(intentEditUserInfoActivity);
        }
    }


    @Override
    public void onItemLongClicked(View view, User user, int index) {
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.grey));
        deleteUserList.add(user);
        fragmentViewModel.setIsMultiSelect(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.multi_select_delete) {
            for (User user : deleteUserList) {
                fragmentViewModel.deleteUser(user);
            }
            deleteUserList.clear();
        }
        return super.onOptionsItemSelected(item);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            fragmentViewModel.deleteUser(currentUserList.get(viewHolder.getAdapterPosition()));
        }
    };

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        isFragmentActive = menuVisible;
    }
}