package com.example.chatlistassignment.repository;

import androidx.paging.DataSource;

import com.example.chatlistassignment.model.Contact;
import com.example.chatlistassignment.model.User;
import com.example.chatlistassignment.repository.room.ContactDao;
import com.example.chatlistassignment.repository.room.UserDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalRepository {

    private UserDao userDao;
    private ContactDao contactDao;

    @Inject
    public LocalRepository(UserDao userDao, ContactDao contactDao) {
        this.userDao = userDao;
        this.contactDao = contactDao;
    }


    public Completable addUser(User user) {
        return userDao.addUser(user);
    }

    public Completable deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    public Completable updateUser(User user) {
        return userDao.updateUser(user);
    }


    public DataSource.Factory<Integer, User> getAllUser() {
        return userDao.getAllUser();
    }

    public DataSource.Factory<Integer, User> queryAllUser(String query) {
        return userDao.queryAllUser(query);
    }

    public Completable deleteListOfUsers(List<User> userArrayList) {
        return userDao.deleteListOfUsers(userArrayList);
    }

    public Completable addContact(Contact contact) {
        return contactDao.addContact(contact);
    }

    public Completable addListOfContact(List<Contact> contactList) {
        return contactDao.addListOfContact(contactList);
    }

    public DataSource.Factory<Integer, Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public DataSource.Factory<Integer, Contact> getQueryContact(String query) {
        return contactDao.getQueryContact(query);
    }

    public Single<List<Contact>> getAllContactsList() {
        return contactDao.getAllContactsList();
    }

    public Completable deleteContact(Contact contact) {
        return contactDao.deleteContact(contact);
    }
}
