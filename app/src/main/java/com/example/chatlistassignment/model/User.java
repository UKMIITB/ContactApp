package com.example.chatlistassignment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "UserDB")
public class User implements Serializable {

    private String name;
    @PrimaryKey
    @NonNull
    private String contactNumber;
    private String profilePic;
    private String dateOfBirth;

    public User(String name, String contactNumber, String profilePic, String dateOfBirth) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.profilePic = profilePic;
        this.dateOfBirth = dateOfBirth;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
