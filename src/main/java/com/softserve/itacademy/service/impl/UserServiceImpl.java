package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {

        if (nullCheck(user)) throw new IllegalArgumentException(user + " has a null parameter");

        boolean exist = users.stream()
                .filter(user1 -> user1.getEmail().equals(user.getEmail()))
                .count() == 1 ? true : false;


        if(!isValidEmailAddress(user.getEmail()))
            throw new IllegalArgumentException(user.getEmail() + " - is invalid");

        if (exist){
            throw new IllegalArgumentException(user.getEmail()+" - user already exist");
        }else {
            users.add(user);
            return user;
        }
    }

    @Override
    public User updateUser(User user) {
        if (nullCheck(user)) throw new IllegalArgumentException(user + " has a null parameter");

        User userOriginal = users.stream()
                .filter(user1 -> user1.getEmail().equals(user.getEmail()))
                .findFirst().orElse(null);

        if (userOriginal == null){
            throw new IllegalArgumentException(user.getEmail()+" - user not found");
        }else {
            users.set(users.indexOf(userOriginal), user);
            return user;
        }


    }

    @Override
    public void deleteUser(User user) {
        if (nullCheck(user)) throw new IllegalArgumentException(user + " has a null parameter");

        if (users.indexOf(user)==-1)
            throw new IllegalArgumentException(user.getEmail()+" - user not found");
        else
            users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }



    private boolean isValidEmailAddress(String email) {
        Pattern pattern =
                Pattern.compile("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean nullCheck(User user){
        if (user == null
                || user.getEmail()==null || user.getEmail().isEmpty()
                || user.getPassword()==null || user.getPassword().isEmpty()
                || user.getFirstName()==null || user.getFirstName().isEmpty()
                || user.getLastName()==null || user.getLastName().isEmpty()
        ) return true;

        return false;
    }

}
