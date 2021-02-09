package com.example.chatlistassignment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.chatlistassignment.repository.room.ListConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "ContactDB")
public class Contact implements Serializable {

    @PrimaryKey
    @NonNull
    private String _id;
    private String name;

    @TypeConverters(ListConverter.class)
    private List<String> number;

    public Contact(@NonNull String _id, String name, List<String> number) {
        this._id = _id;
        this.name = name;
        this.number = number;
    }

    public Contact() {
    }

    @NonNull
    public String get_id() {
        return _id;
    }

    public void set_id(@NonNull String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNumber() {
        return number;
    }

    public void setNumber(List<String> number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return _id.equals(contact._id) &&
                Objects.equals(name, contact.name) &&
                Objects.equals(number, contact.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, number);
    }
}
