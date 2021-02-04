package com.example.chatlistassignment.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

import java.util.Calendar;

public class EditUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName;
    Button buttonDatePicker, buttonSave;
    TextView textViewBirthday, textViewNumber;

    FragmentViewModel fragmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        User user = (User) getIntent().getSerializableExtra("User");

        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);

        init(user);
    }

    private void init(User user) {
        editTextName = findViewById(R.id.edit_text_name);
        textViewNumber = findViewById(R.id.text_view_number);
        buttonDatePicker = findViewById(R.id.button_date_picker);
        buttonSave = findViewById(R.id.button_save);
        textViewBirthday = findViewById(R.id.text_view_birthday);

        buttonDatePicker.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        editTextName.setText(user.getName());
        textViewNumber.setText(user.getContactNumber());

        String DOBBuilder = "Date of Birth: " + user.getDateOfBirth();
        textViewBirthday.setText(DOBBuilder);
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
        String contactNumber = textViewNumber.getText().toString();

        if (userName.equals("") || contactNumber.equals("")) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        String birthDateString = textViewBirthday.getText().toString();
        int indexOfColon = birthDateString.indexOf(":");
        String birthDate = birthDateString.substring(indexOfColon + 1);

        User userUpdated = new User(userName, contactNumber, null, birthDate);
        fragmentViewModel.updateUser(userUpdated, this);

        onBackPressed();
    }
}