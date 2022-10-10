package com.softserve.itacademy.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToDo {

    private String title;

    private LocalDateTime createdAt;

    private User owner;

    private List<Task> tasks = new ArrayList<>();

    public ToDo() {
    }

    public ToDo(String title, LocalDateTime createdAt, User owner) {
        this.title = title;
        this.createdAt = createdAt;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDo toDo = (ToDo) o;

        return title.equals(toDo.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "Title='" + title + '\'' +
                ", createdAt=" + createdAt.toString()+
                ",\n tasks=" + tasks +
                '}';
    }
}
