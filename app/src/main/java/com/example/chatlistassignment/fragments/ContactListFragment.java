package com.example.chatlistassignment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlistassignment.R;
import com.example.chatlistassignment.adapters.ContactListRecyclerviewAdapter;
import com.example.chatlistassignment.model.Contact;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

public class ContactListFragment extends Fragment {

    RecyclerView recyclerViewContactList;
    ContactListRecyclerviewAdapter recyclerviewAdapter;
    RecyclerView.LayoutManager layoutManager;
    FragmentViewModel fragmentViewModel;

    boolean isFragmentActive = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        init(view);

        fragmentViewModel.contactInit();
        observeContactDB();

        observeQueryString();
        return view;
    }


    private void init(View view) {
        recyclerViewContactList = view.findViewById(R.id.recyclerview_contact_list);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerviewAdapter = new ContactListRecyclerviewAdapter();

        recyclerViewContactList.setLayoutManager(layoutManager);
        recyclerViewContactList.setAdapter(recyclerviewAdapter);
    }

    private void observeContactDB() {
        fragmentViewModel.contactList.observe(getViewLifecycleOwner(), new Observer<PagedList<Contact>>() {
            @Override
            public void onChanged(PagedList<Contact> contacts) {
                recyclerviewAdapter.submitList(contacts);
            }
        });
    }

    private void observeQueryString() {
        fragmentViewModel.getQueryString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String query) {
                if(isFragmentActive)
                    queryContactList(query);
            }
        });
    }

    private void queryContactList(String query)
    {

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        isFragmentActive = menuVisible;
    }
}