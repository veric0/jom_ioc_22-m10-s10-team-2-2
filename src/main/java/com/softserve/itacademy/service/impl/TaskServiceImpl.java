package com.softserve.itacademy.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;

@Service
public class TaskServiceImpl implements TaskService {

    private ToDoService toDoService;

    @Autowired
    public TaskServiceImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    public Task addTask(Task task, ToDo todo) {
        List<Task> tasks = todo.getTasks();
        if (tasks.contains(task)){
            throw new RuntimeException("Task exist already");
        } else {
            tasks.add(task);
        }
        return task;
    }

    public Task updateTask(Task task) {
        List<Task> allTasks = getAll();
        Task updTask;
        if (allTasks.contains(task)) {
            Optional<Task> optionalTask = allTasks.stream()
                    .filter(task1 -> task1.equals(task)).findFirst();
            updTask = optionalTask.get();
            updTask.setPriority(task.getPriority());
        } else {
            throw new RuntimeException("Tasks not found");
        }
        return updTask;
    }

    public void deleteTask(Task task) {
        List<Task> allTasks = getAll();
        if (allTasks.contains(task)) {
            allTasks.remove(task);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public List<Task> getAll() {
        return toDoService.getAll().stream()
                .flatMap(toDo -> toDo.getTasks().stream())
                .collect(Collectors.toList());
    }

    public List<Task> getByToDo(ToDo todo) {
        return toDoService.getAll().stream()
                .filter(toDo1 -> toDo1.equals(todo))
                .flatMap(toDo -> toDo.getTasks().stream())
                .collect(Collectors.toList());
    }

    public Task getByToDoName(ToDo todo, String name) {
        return getByToDo(todo).stream()
                .filter(task -> task.getName().equals(name))
                .findFirst()
                .get();
    }

    public Task getByUserName(User user, String name) {
        return toDoService.getByUser(user).stream()
                .flatMap(toDo -> toDo.getTasks().stream())
                .filter(task -> task.getName().equals(name))
                .findFirst()
                .get();
    }

}
