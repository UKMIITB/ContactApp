package com.example.chatlistassignment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.chatlistassignment.repository.room.DateConverter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "UserDB")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer _id;
    private String name;
    private String contactNumber;
    private String profilePic;
    private String dateOfBirth;
    private long creationTime;

    @TypeConverters(DateConverter.class)
    private Date date;

    public void set_id(@NonNull Integer _id) {
        this._id = _id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public User(String name, String contactNumber, String profilePic, String dateOfBirth, Date date, long creationTime) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.profilePic = profilePic;
        this.dateOfBirth = dateOfBirth;
        this.date = date;
        this.creationTime = creationTime;
    }

    public User() {
    }

    @NonNull
    public Integer get_id() {
        return _id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return _id.equals(user._id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(contactNumber, user.contactNumber) &&
                Objects.equals(profilePic, user.profilePic) &&
                Objects.equals(dateOfBirth, user.dateOfBirth) &&
                Objects.equals(creationTime, user.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, contactNumber, profilePic, dateOfBirth, creationTime);
    }
}
