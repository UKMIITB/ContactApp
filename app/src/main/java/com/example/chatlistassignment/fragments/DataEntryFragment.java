package com.example.chatlistassignment.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

import java.util.Calendar;

public class DataEntryFragment extends Fragment implements View.OnClickListener {

    EditText editTextUserName, editTextContactNumber;
    Button buttonDatePicker, buttonSave, buttonProfilePic;
    TextView textViewBirthday;

    FragmentViewModel fragmentViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data_entry, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        editTextUserName = view.findViewById(R.id.edit_text_username);
        editTextContactNumber = view.findViewById(R.id.edit_text_contact_number);
        buttonDatePicker = view.findViewById(R.id.button_date_picker);
        buttonSave = view.findViewById(R.id.button_save);
        buttonProfilePic = view.findViewById(R.id.button_profile_pic);
        textViewBirthday = view.findViewById(R.id.text_view_birthday);

        buttonDatePicker.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonProfilePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                saveButtonClicked();
                break;
            case R.id.button_profile_pic:
                buttonProfilePicClicked();
                break;
            case R.id.button_date_picker:
                buttonDatePickerClicked();
                break;
        }
    }

    private void buttonDatePickerClicked() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1; // As Jan starts from 0
                String date = "Birthday selected is :" + dayOfMonth + "/" + month + "/" + year;
                textViewBirthday.setText(date);
            }
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void buttonProfilePicClicked() {
        Toast.makeText(getContext(), "Feature unavailable at the moment. Default pic will be used", Toast.LENGTH_SHORT).show();
    }

    private void saveButtonClicked() {
        String userName = editTextUserName.getText().toString();
        String contactNumber = editTextContactNumber.getText().toString();

        if (userName.equals("") || contactNumber.equals("")) {
            Toast.makeText(getContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        String birthDateString = textViewBirthday.getText().toString();
        int indexOfColon = birthDateString.indexOf(":");
        String birthDate = birthDateString.substring(indexOfColon + 1);

        User user = new User(userName, contactNumber, R.drawable.ic_baseline_person_24, birthDate);

        fragmentViewModel.addUser(user, getContext());

        clearInputFields();

    }

    private void clearInputFields() {
        editTextUserName.setText("");
        editTextContactNumber.setText("");
        textViewBirthday.setText(R.string.birthday_selected);
    }
}