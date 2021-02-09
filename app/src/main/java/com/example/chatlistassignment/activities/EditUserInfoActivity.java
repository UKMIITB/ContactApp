package com.example.chatlistassignment.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.chatlistassignment.R;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.utils.SaveBitmap;
import com.example.chatlistassignment.viewmodel.FragmentViewModel;

import java.io.IOException;
import java.util.Calendar;

public class EditUserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName, editTextNumber;
    Button buttonDatePicker, buttonSave;
    TextView textViewBirthday;
    ImageView imageViewProfilePic;
    String ProfilePicPath;

    User user;

    FragmentViewModel fragmentViewModel;

    private final int REQUEST_CODE_CAMERA = 0;
    private final int REQUEST_CODE_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        user = (User) getIntent().getSerializableExtra("User");

        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
        fragmentViewModel.init();

        init();
    }

    private void init() {
        editTextName = findViewById(R.id.edit_text_name);
        editTextNumber = findViewById(R.id.edit_text_contact_number);
        buttonDatePicker = findViewById(R.id.button_date_picker);
        buttonSave = findViewById(R.id.button_save);
        textViewBirthday = findViewById(R.id.text_view_birthday);
        imageViewProfilePic = findViewById(R.id.image_view_profile_pic);

        buttonDatePicker.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        imageViewProfilePic.setOnClickListener(this);

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


        ProfilePicPath = user.getProfilePic();
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
                updateProfilePic();
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
        user.setProfilePic(ProfilePicPath);

        fragmentViewModel.updateUser(user);

        onBackPressed();
    }

    private void updateProfilePic() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED)

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY);
        else
            startOpenGalleryIntent();
    }

    private void startOpenGalleryIntent() {
        Intent intentPickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentPickPhoto, REQUEST_CODE_GALLERY);
    }

    private void startTakePictureIntent() {
        Intent intentTakePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentTakePicture, REQUEST_CODE_CAMERA);
    }

    private void checkPermissionAndStartCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
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
                Toast.makeText(this, "Failed. Please grant camera & storage permission", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                startOpenGalleryIntent();
            else
                Toast.makeText(this, "Failed. Please grant gallery permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    Bitmap bitmapCameraImage = (Bitmap) data.getExtras().get("data");
                    Uri cameraImageUri = null;
                    try {
                        Log.d("TAG", "Inside try of onActivity result of DataEntryFragment");
                        cameraImageUri = SaveBitmap.saveBitmapReturnUri(bitmapCameraImage);
                        Log.d("TAG", "cameraUri: " + cameraImageUri.toString());
                        Log.d("TAG", "cameraUri: " + cameraImageUri.getPath());

                    } catch (IOException e) {
                        Log.d("TAG", "Inside catch: " + e.getMessage());
                        e.printStackTrace();
                    }

                    updateProfilePic(cameraImageUri);
                    ProfilePicPath = cameraImageUri.toString();
                    break;

                case REQUEST_CODE_GALLERY:
                    Uri selectedImageUri = data.getData();
                    Log.d("TAG", "URi: " + selectedImageUri.getPath());
                    ProfilePicPath = selectedImageUri.toString();

                    updateProfilePic(selectedImageUri);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void updateProfilePic(Uri picUri) {
        Glide.with(this)
                .load(picUri)
                .error(R.drawable.ic_baseline_person_24)
                .into(imageViewProfilePic);
    }
}