package com.example.chatlistassignment.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.User;

public class DetailedUserInfoActivity extends AppCompatActivity {

    ImageView imageViewProfilePic;
    TextView textViewName, textViewNumber, textViewBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_user_info);

        User user = (User) getIntent().getSerializableExtra("User");
        init();
        setData(user);
    }

    private void setData(User user) {

        if (user.getProfilePic() == null)
            imageViewProfilePic.setImageResource(R.drawable.ic_baseline_person_24);
        else
            imageViewProfilePic.setImageURI(Uri.parse(user.getProfilePic()));

        String nameBuilder = "Name: " + user.getName();
        String numberBuilder = "Contact No: " + user.getContactNumber();
        String birthdayBuilder = "Date of Birth: " + user.getDateOfBirth();

        textViewName.setText(nameBuilder);
        textViewNumber.setText(numberBuilder);
        textViewBirthday.setText(birthdayBuilder);
    }

    private void init() {
        imageViewProfilePic = findViewById(R.id.image_view_profile_pic);
        textViewName = findViewById(R.id.text_view_name);
        textViewNumber = findViewById(R.id.text_view_number);
        textViewBirthday = findViewById(R.id.text_view_birthday);
    }
}