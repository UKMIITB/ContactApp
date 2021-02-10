package com.example.chatlistassignment.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

import java.util.Calendar;

public class EditUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName, editTextNumber;
    Button buttonDatePicker, buttonSave;
    TextView textViewBirthday;
    ImageView imageViewProfilePic, imageViewEditProfilePic;

    User user;

    FragmentViewModel fragmentViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        user = (User) getIntent().getSerializableExtra("User");

        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);

        init();
    }

    private void init() {
        editTextName = findViewById(R.id.edit_text_name);
        editTextNumber = findViewById(R.id.edit_text_contact_number);
        buttonDatePicker = findViewById(R.id.button_date_picker);
        buttonSave = findViewById(R.id.button_save);
        textViewBirthday = findViewById(R.id.text_view_birthday);
        imageViewProfilePic = findViewById(R.id.image_view_profile_pic);
        imageViewEditProfilePic = findViewById(R.id.image_view_edit_profile_pic);

        buttonDatePicker.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        imageViewProfilePic.setOnClickListener(this);
        imageViewEditProfilePic.setOnClickListener(this);

        editTextName.setText(user.getName());
        editTextNumber.setText(user.getContactNumber());

        String DOBBuilder = "Date of Birth: " + user.getDateOfBirth();
        textViewBirthday.setText(DOBBuilder);

        if (user.getProfilePic() != null)
            Glide.with(this)
                    .load(Uri.parse(user.getProfilePic()))
                    .error(R.drawable.ic_baseline_person_24)
                    .into(imageViewProfilePic);
        else
            Glide.with(this)
                    .load(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .into(imageViewProfilePic);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_date_picker:
                buttonDatePickerClicked();
                break;
            case R.id.button_save:
                saveButtonClicked();
                break;
            case R.id.image_view_profile_pic:
                showProfilePic();
                break;
        }
    }


    private void buttonDatePickerClicked() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1; // As Jan starts from 0
                String date = "Date of Birth:" + dayOfMonth + "/" + month + "/" + year;
                textViewBirthday.setText(date);
            }
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveButtonClicked() {
        String userName = editTextName.getText().toString();
        String contactNumber = editTextNumber.getText().toString();

        if (userName.equals("") || contactNumber.equals("")) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        String birthDateString = textViewBirthday.getText().toString();
        int indexOfColon = birthDateString.indexOf(":");
        String birthDate = birthDateString.substring(indexOfColon + 1);

        user.setName(userName);
        user.setContactNumber(contactNumber);
        user.setDateOfBirth(birthDate);

        fragmentViewModel.updateUser(user);

        onBackPressed();
    }


    private void showProfilePic() {
        Log.d("TAG", "Show profilepic called from, edituseractivity: " + user.getProfilePic());
        Intent intentProfilePicActivity = new Intent(this, ProfilePicActivity.class);
        intentProfilePicActivity.putExtra("User", user);
        startActivity(intentProfilePicActivity);
    }
}