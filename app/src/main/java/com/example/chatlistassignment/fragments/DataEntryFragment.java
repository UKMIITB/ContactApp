package com.example.chatlistassignment.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.utils.SaveBitmap;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_CANCELED;

public class DataEntryFragment extends Fragment implements View.OnClickListener {

    EditText editTextUserName, editTextContactNumber, editTextContactNumber2, editTextContactNumber3;
    Button buttonSave, buttonSelectProfilePic;
    TextView textViewBirthday, textViewDatePicker;
    String profilePicPath;
    ImageView imageViewProfilePic;
    FragmentViewModel fragmentViewModel;

    User user;

    boolean isEditInfoActivity;

    private final int REQUEST_CODE_CAMERA = 100;
    private final int REQUEST_CODE_GALLERY = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_data_entry, container, false);

        if (getArguments() != null)
            isEditInfoActivity = getArguments().getBoolean("IsEditInfoActivity");
        else
            isEditInfoActivity = false;

        init(view);
        fragmentViewModel.init();

        if (isEditInfoActivity)
            displayEditInfo((User) getArguments().getSerializable("User"));

        return view;
    }


    private void init(View view) {
        editTextUserName = view.findViewById(R.id.edit_text_username);

        editTextContactNumber = view.findViewById(R.id.edit_text_contact_number);
        editTextContactNumber2 = view.findViewById(R.id.edit_text_contact_number2);
        editTextContactNumber3 = view.findViewById(R.id.edit_text_contact_number3);

        textViewDatePicker = view.findViewById(R.id.text_view_date_picker);
        buttonSave = view.findViewById(R.id.button_save);
        buttonSelectProfilePic = view.findViewById(R.id.button_select_profile_pic);
        textViewBirthday = view.findViewById(R.id.text_view_birthday);
        imageViewProfilePic = view.findViewById(R.id.image_view_profile_pic);

        textViewDatePicker.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonSelectProfilePic.setOnClickListener(this);

        if (!isEditInfoActivity) {
            editTextContactNumber.setOnClickListener(this);
            editTextContactNumber2.setOnClickListener(this);
        }


        if (isEditInfoActivity)
            imageViewProfilePic.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                saveButtonClicked();
                break;
            case R.id.button_select_profile_pic:
                buttonSelectProfilePicClicked();
                break;
            case R.id.text_view_date_picker:
                buttonDatePickerClicked();
                break;
            case R.id.image_view_profile_pic:
                showProfilePic();
                break;
            case R.id.edit_text_contact_number:
                editContactClicked();
                break;
            case R.id.edit_text_contact_number2:
                editContact2Clicked();
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

    private void buttonSelectProfilePicClicked() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                if (options[item].equals("Take Photo")) {

                    checkPermissionAndStartCamera();

                } else if (options[item].equals("Choose from Gallery")) {
                    checkPermissionAndOpenGallery();

                } else
                    dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void checkPermissionAndOpenGallery() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED)

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY);
        else
            startOpenGalleryIntent();
    }

    private void startOpenGalleryIntent() {
        Intent intentPickPhoto = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentPickPhoto, REQUEST_CODE_GALLERY);
    }

    private void startTakePictureIntent() {
        Intent intentTakePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentTakePicture, REQUEST_CODE_CAMERA);
    }

    private void checkPermissionAndStartCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_CAMERA);
        else
            startTakePictureIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED)
                startTakePictureIntent();
            else
                Toast.makeText(getContext(), "Failed. Please grant camera & storage permission", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                startOpenGalleryIntent();
            else
                Toast.makeText(getContext(), "Failed. Please grant gallery permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    Bitmap bitmapCameraImage = (Bitmap) data.getExtras().get("data");
                    Uri cameraImageUri = null;

                    try {
                        cameraImageUri = SaveBitmap.saveBitmapReturnUri(bitmapCameraImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    updateProfilePic(cameraImageUri);
                    profilePicPath = cameraImageUri.toString();
                    break;

                case REQUEST_CODE_GALLERY:
                    Uri selectedImageUri = data.getData();
                    profilePicPath = selectedImageUri.toString();

                    updateProfilePic(selectedImageUri);
                    break;
            }
        }
    }

    private void updateProfilePic(Uri picUri) {
        Glide.with(getContext())
                .load(picUri)
                .error(R.drawable.ic_baseline_person_24)
                .into(imageViewProfilePic);
    }

    private void updateProfilePic(String profilePic) {
        if (profilePic != null)
            updateProfilePic(Uri.parse(profilePic));
        else
            Glide.with(getContext())
                    .load(R.drawable.ic_baseline_person_24)
                    .into(imageViewProfilePic);
    }


    private void saveButtonClicked() {
        String userName = editTextUserName.getText().toString();

        String contactNumber = editTextContactNumber.getText().toString();
        String contactNumber2 = editTextContactNumber2.getText().toString();
        String contactNumber3 = editTextContactNumber3.getText().toString();

        contactNumber = formattedContactNumber(contactNumber);

        if (userName.equals("") || contactNumber.equals("")) {
            Toast.makeText(getContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        String birthDateString = textViewBirthday.getText().toString();
        int indexOfColon = birthDateString.indexOf(":");
        String birthDate = birthDateString.substring(indexOfColon + 1);

        if (!isEditInfoActivity) {
            user = new User(userName, contactNumber, contactNumber2, contactNumber3, profilePicPath, birthDate, new Date());
            fragmentViewModel.addUser(user);
        } else {
            user.setName(userName);

            user.setContactNumber(contactNumber);
            user.setContactNumber2(contactNumber2);
            user.setContactNumber3(contactNumber3);

            user.setDateOfBirth(birthDate);
            user.setProfilePic(profilePicPath);
            fragmentViewModel.updateUser(user);
        }

        clearInputFields();

        if (!isEditInfoActivity)
            changeTabChatList();
        else
            getActivity().finish();

    }

    private void changeTabChatList() {
        ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
        viewPager.setCurrentItem(0, true);
    }

    private void clearInputFields() {
        editTextUserName.setText("");
        editTextContactNumber.setText("");
        textViewBirthday.setText(R.string.birthday_selected);
        imageViewProfilePic.setImageDrawable(null);
        imageViewProfilePic.setImageResource(R.drawable.ic_baseline_person_24);
    }

    private void displayEditInfo(User user) {
        editTextUserName.setText(user.getName());
        editTextContactNumber.setText(user.getContactNumber());

        String birthDate = "Birthday selected is :" + user.getDateOfBirth();
        textViewBirthday.setText(birthDate);

        updateProfilePic(user.getProfilePic());

        editTextContactNumber2.setVisibility(View.VISIBLE);
        editTextContactNumber3.setVisibility(View.VISIBLE);
        editTextContactNumber2.setText(user.getContactNumber2());
        editTextContactNumber3.setText(user.getContactNumber3());

        this.user = user;
    }

    private void showProfilePic() {
        Bundle bundle = new Bundle();
        bundle.putString("ProfilePic", user.getProfilePic());

        ProfilePicFragment profilePicFragment = new ProfilePicFragment();
        profilePicFragment.setArguments(bundle);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_frame_container, profilePicFragment)
                .addToBackStack(null)
                .commit();
    }

    private String formattedContactNumber(String contactNumber) {
        if (contactNumber.length() < 8 || contactNumber.length() > 12 || !contactNumber.matches("[0-9]*"))
            return contactNumber;

        //US check
        if (contactNumber.length() == 11 && contactNumber.charAt(0) == '1')
            return "+" + contactNumber;

        //UK check
        if (contactNumber.length() == 12 && contactNumber.charAt(0) == '4' && contactNumber.charAt(1) == '4')
            return "+" + contactNumber;

        //India
        if (contactNumber.length() == 12 && contactNumber.charAt(0) == '9' && contactNumber.charAt(1) == '1')
            return "+" + contactNumber;

        //Japan
        if (contactNumber.length() == 12 && contactNumber.charAt(0) == '8' && contactNumber.charAt(1) == '1')
            return "+" + contactNumber;

        return contactNumber;
    }

    private void editContactClicked() {
        editTextContactNumber2.setVisibility(View.VISIBLE);
    }

    private void editContact2Clicked() {
        editTextContactNumber3.setVisibility(View.VISIBLE);
    }
}