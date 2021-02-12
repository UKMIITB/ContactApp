package com.example.chatlistassignment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlistassignment.R;

import java.util.List;

public class ViewContactRecyclerViewAdapter extends RecyclerView.Adapter<ViewContactRecyclerViewAdapter.ViewHolder> {

    List<String> contactNumbers;
    List<String> contactTypes;

    public ViewContactRecyclerViewAdapter(List<String> contactNumbers, List<String> contactTypes) {
        this.contactNumbers = contactNumbers;
        this.contactTypes = contactTypes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view_contact_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewContactType.setText(contactTypes.get(position));
        holder.textViewContactNumber.setText(contactNumbers.get(position));
    }

    @Override
    public int getItemCount() {
        return contactNumbers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewContactType, textViewContactNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewContactType = itemView.findViewById(R.id.text_view_contact_type);
            textViewContactNumber = itemView.findViewById(R.id.text_view_contact_number);
        }
    }
}
