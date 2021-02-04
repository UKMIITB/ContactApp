package com.example.chatlistassignment.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatlistassignment.ItemClickListener;
import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.User;

import java.util.ArrayList;

public class RecyclerViewAdapter extends PagedListAdapter<User,RecyclerViewAdapter.ViewHolder> {
    ItemClickListener itemClickListener;

//    public RecyclerViewAdapter(Context context, ArrayList<User> userArrayList, ItemClickListener itemClickListener) {
//        this.context = context;
//        this.userArrayList = userArrayList;
//        this.itemClickListener = itemClickListener;
//    }

    public  static DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.get_id() == newItem.get_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    public RecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    public  void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = getItem(position);
        holder.textViewName.setText(user.getName());
        holder.textViewNumber.setText(user.getContactNumber());

        if (user.getProfilePic() == null)
            holder.imageViewProfilePic.setImageResource(R.drawable.ic_baseline_person_24);
        else
            holder.imageViewProfilePic.setImageURI(Uri.parse(user.getProfilePic()));

    }

//    @Override
//    public int getItemCount() {
//        return userArrayList == null ? 0 : userArrayList.size();
//    }
//
//    public void updateData(ArrayList<User> userArrayList) {
//        this.userArrayList = userArrayList;
//        notifyDataSetChanged();
//    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewProfilePic;
        TextView textViewName, textViewNumber;
        Button buttonEdit, buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProfilePic = itemView.findViewById(R.id.image_view_profile_pic);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewNumber = itemView.findViewById(R.id.text_view_number);

            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            itemView.setOnClickListener(this);

            buttonEdit.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClicked(view,getItem(getAdapterPosition()));
        }
    }
}
