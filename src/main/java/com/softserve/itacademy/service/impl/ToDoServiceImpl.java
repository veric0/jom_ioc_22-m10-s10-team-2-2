package com.softserve.itacademy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public ToDo addTodo(ToDo todo, User user) {
        List<ToDo> ToDoByUser = getByUser(user);
        if (ToDoByUser.contains(todo)) {
            throw new RuntimeException("Already exists");
        }
        todo.setOwner(user);
        ToDoByUser.add(todo);
        return todo;
    }

    public ToDo updateTodo(ToDo todo) {
        List<ToDo> allToDo = getAll();
        if (allToDo.contains(todo)) {
            allToDo.remove(todo);
            allToDo.add(todo);
        } else {
            throw new RuntimeException("Can not found");
        }
        return todo;
    }

    public void deleteTodo(ToDo todo) {
        List<ToDo> allToDo = getAll();
        if (allToDo.contains(todo)) {
            allToDo.remove(todo);
        } else {
            throw new RuntimeException("Not found");
        }
    }

    public List<ToDo> getAll() {
        return userService.getAll().stream()
                .flatMap(user -> user.getMyTodos().stream())
                .collect(Collectors.toList());
    }

    public List<ToDo> getByUser(User user) {
        return user.getMyTodos();
    }

    public ToDo getByUserTitle(User user, String title) {
        List<ToDo> toDoList = getByUser(user).stream()
                .filter(toDo -> toDo.getTitle().equals(title))
                .collect(Collectors.toList());
        return toDoList.get(0);
    }

}
