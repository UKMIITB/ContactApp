package com.example.chatlistassignment.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.chatlistassignment.R;

public class ProfilePicFragment extends Fragment {

    ImageView imageViewProfilePic;
    String profilePicPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_pic, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        imageViewProfilePic = view.findViewById(R.id.image_view_profile_pic);

        if (getArguments() != null)
            profilePicPath = getArguments().getString("ProfilePic");
        else
            profilePicPath = null;

        showProfilePic();
    }

    private void showProfilePic() {
        if (profilePicPath == null)
            Glide.with(getContext())
                    .load(R.drawable.ic_baseline_person_24)
                    .centerCrop()
                    .into(imageViewProfilePic);
        else
            Glide.with(getContext())
                    .load(Uri.parse(profilePicPath))
                    .centerCrop()
                    .into(imageViewProfilePic);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (menuVisible && getActivity() != null)
            getActivity().setTitle("Profile Pic");
    }
}