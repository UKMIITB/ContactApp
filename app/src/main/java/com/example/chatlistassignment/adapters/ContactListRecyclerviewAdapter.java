package com.example.chatlistassignment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.Contact;

public class ContactListRecyclerviewAdapter extends PagedListAdapter<Contact, ContactListRecyclerviewAdapter.ViewHolder> {

    public static DiffUtil.ItemCallback<Contact> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contact>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.get_id().equals(newItem.get_id());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.equals(newItem);
        }
    };

    public ContactListRecyclerviewAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = getItem(position);

        holder.textViewName.setText(contact.getName());
        holder.textViewNumber.setText(contact.getNumber().get(0));

        Glide.with(holder.imageViewProfilePic.getContext())
                .load(R.drawable.ic_baseline_person_24)
                .into(holder.imageViewProfilePic);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewNumber;
        ImageView imageViewProfilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfilePic = itemView.findViewById(R.id.image_view_profile_pic);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewNumber = itemView.findViewById(R.id.text_view_number);
        }
    }
}
