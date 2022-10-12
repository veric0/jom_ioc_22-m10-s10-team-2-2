package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.time.LocalDateTime;

@RunWith(JUnitPlatform.class)
public class TaskServiceTest {
    private static UserService userService;

    private static TaskService taskService;


    private static User user;
    private static ToDo todo;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        taskService = annotationConfigContext.getBean(TaskService.class);

        user = new User("Naomi","Kembel",
                "Naomi1987@gmail.com","GhfjUJHK");

        todo = new ToDo(user.getFirstName()+"ToDo", LocalDateTime.now(), user);

        annotationConfigContext.close();
    }

    @Test
    public void checkAddTask() {
        Task actual = taskService.addTask(new Task("Cheese cl", Priority.HIGH),
                todo);
        Task expected = new Task("Cheese cl", Priority.HIGH);
        Assertions.assertEquals(actual,expected,"addTask can not work");
    }

    @Test
    public void checkAddExistTask() {
        Task task = new Task("Cheese cl", Priority.HIGH);
        boolean thrown = false;
        try {
            taskService.addTask(task,todo);
        }catch (RuntimeException tae){
            thrown=true;
        }
        Assertions.assertTrue(thrown,"task - already exist");
    }

}