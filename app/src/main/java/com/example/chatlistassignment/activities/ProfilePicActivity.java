package com.example.chatlistassignment.activities;

import android.Manifest;
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
import android.widget.ImageView;
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

public class ProfilePicActivity extends AppCompatActivity {

    ImageView imageViewProfilePic;
    Button buttonEditProfilePic;

    User user;

    FragmentViewModel fragmentViewModel;

    private final int REQUEST_CODE_CAMERA = 0;
    private final int REQUEST_CODE_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);

        user = (User) getIntent().getSerializableExtra("User");

        fragmentViewModel = new ViewModelProvider(this).get(FragmentViewModel.class);
        init();
    }

    private void init() {
        imageViewProfilePic = findViewById(R.id.image_view_profile_pic);
        buttonEditProfilePic = findViewById(R.id.button_edit_profile_pic);

        displayProfilePic();
        buttonClickListener();
    }

    private void displayProfilePic() {

        if (user.getProfilePic() != null)
            Glide.with(this)
                    .load(Uri.parse(user.getProfilePic()))
                    .error(R.drawable.ic_baseline_person_24)
                    .into(imageViewProfilePic);
        else
            Glide.with(this)
                    .load(R.drawable.ic_baseline_person_24)
                    .into(imageViewProfilePic);
    }

    private void displayProfilePic(Uri uri) {

        Glide.with(this)
                .load(uri)
                .error(R.drawable.ic_baseline_person_24)
                .into(imageViewProfilePic);
    }

    private void buttonClickListener() {
        buttonEditProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfilePic();
            }
        });
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

                    displayProfilePic(cameraImageUri);
                    user.setProfilePic(cameraImageUri.toString());
                    break;

                case REQUEST_CODE_GALLERY:
                    Uri selectedImageUri = data.getData();
                    Log.d("TAG", "URi: " + selectedImageUri.getPath());
                    user.setProfilePic(selectedImageUri.toString());

                    displayProfilePic(selectedImageUri);
                    break;
            }
            updateUserData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUserData() {
        Log.d("TAG", "update user data in profile pic activity: " + user.getProfilePic());
        fragmentViewModel.updateUser(user);
    }
}