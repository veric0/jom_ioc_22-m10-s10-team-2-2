package com.softserve.itacademy.service;

import java.util.List;

import com.softserve.itacademy.model.User;

public interface UserService {
    
    User addUser(User user) throws IllegalArgumentException ;

    User updateUser(User user) throws IllegalArgumentException ;

    void deleteUser(User user) throws IllegalArgumentException ;

    List<User> getAll();

}
