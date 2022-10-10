package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = annotationConfigContext.getBean(UserService.class);
        ToDoService toDoService = annotationConfigContext.getBean(ToDoService.class);
        TaskService taskService = annotationConfigContext.getBean(TaskService.class);

//add new User to Application
        User user1 = new User("Roman","Naftar",
                "naftar2000@gmail.com","Rgfijsn");
        User user2 = new User("Victor","Levis",
                "levis@gmail.com","HranFrye");
        User user3 = new User("Nastia","Balushak",
                "Balus2000@gmail.com","1223Hdk");


    }

}
