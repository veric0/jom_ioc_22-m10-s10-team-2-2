package com.softserve.itacademy.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String firstName;

    private String lastName;

    private String email;

    private String password;


    private List<ToDo> myTodos = new ArrayList<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ToDo> getMyTodos() {
        return myTodos;
    }

    public void setMyTodos(ToDo myTodos) {
        ToDo ifExistToDo = this.myTodos.stream().filter(
                        toDo -> toDo.getTitle().equals(myTodos.getTitle()))
                .findFirst().orElse(null);

        if (ifExistToDo!=null){ //if this todos exist
            this.myTodos.set(this.myTodos.indexOf(ifExistToDo),
                    myTodos);
        }else {
            this.myTodos.add(myTodos);
        }
    }

    public void setMyTodos(List<ToDo> myTodos) {
        this.myTodos = myTodos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", myTodos=" + myTodos +
                '}';
    }
}
