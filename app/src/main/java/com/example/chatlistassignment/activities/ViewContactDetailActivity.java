package com.example.chatlistassignment.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlistassignment.R;
import com.example.chatlistassignment.adapters.ViewContactRecyclerViewAdapter;
import com.example.chatlistassignment.model.Contact;

public class ViewContactDetailActivity extends AppCompatActivity {

    TextView textViewName;
    RecyclerView recyclerViewContactList;
    ViewContactRecyclerViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_detail);

        init();
    }

    private void init() {
        contact = (Contact) getIntent().getSerializableExtra("Contact");

        textViewName = findViewById(R.id.text_view_name);
        textViewName.setText(contact.getName());

        recyclerViewContactList = findViewById(R.id.recyclerview_contact_list);

        layoutManager = new LinearLayoutManager(this);
        adapter = new ViewContactRecyclerViewAdapter(contact.getNumber(), contact.getNumberType());

        recyclerViewContactList.setLayoutManager(layoutManager);
        recyclerViewContactList.setAdapter(adapter);
    }
}