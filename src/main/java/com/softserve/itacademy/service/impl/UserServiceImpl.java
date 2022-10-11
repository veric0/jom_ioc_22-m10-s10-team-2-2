package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("incorrect data in user");
        }
        if (users.contains(user)) {
            throw new IllegalArgumentException("user is already in list");
        }
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("incorrect data in user");
        }
        if (!users.contains(user)) {
            throw new IllegalArgumentException("user is not in list");
        }
        users.set(users.indexOf(user), user);
        return user;
    }

    @Override
    public void deleteUser(User user) throws IllegalArgumentException {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("incorrect data in user");
        }
        if (!users.remove(user)) {
            throw new IllegalArgumentException("user is not in list");
        }
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    private boolean isValidUser(User user) {
        return  user != null
                && user.getFirstName() != null  && !user.getFirstName().isEmpty()
                && user.getLastName() != null   && !user.getLastName().isEmpty()
                && user.getEmail() != null      && user.getEmail().matches("^[\\w-.]+@(\\w+\\.)+\\w{2,}$")
                && user.getPassword() != null   && user.getPassword().trim().length() >= 6
                && user.getMyTodos() != null;
    }
}
